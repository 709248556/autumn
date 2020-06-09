package com.autumn.security.session.impl;

import com.autumn.exception.ExceptionUtils;
import com.autumn.runtime.session.AutumnSessionUser;
import com.autumn.security.redis.AutumnShiroRedisManager;
import com.autumn.util.StringUtils;
import com.autumn.util.tuple.TupleTwo;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.crazycake.shiro.exception.SerializationException;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.RedisSerializer;
import org.crazycake.shiro.serializer.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 20:26
 **/
public class AutumnRedisSessionDAO extends AbstractAutumnSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(AutumnRedisSessionDAO.class);
    private static final long DAY_SECOND = 24 * 60 * 60;

    private static final String DEFAULT_SESSION_KEY_PREFIX = "autumn:session:";
    private String keyPrefix = DEFAULT_SESSION_KEY_PREFIX;

    /**
     * 10秒
     */
    private static final long DEFAULT_SESSION_IN_MEMORY_TIMEOUT = 10000L;

    /**
     * doReadSession be called about 10 times when login.
     * Save Session in ThreadLocal to resolve this problem. sessionInMemoryTimeout is expiration of Session in ThreadLocal.
     * The default value is 10000 milliseconds (10s).
     * Most of time, you don't need to change it.
     */
    private long sessionInMemoryTimeout = DEFAULT_SESSION_IN_MEMORY_TIMEOUT;

    private static final boolean DEFAULT_SESSION_IN_MEMORY_ENABLED = true;

    private boolean sessionInMemoryEnabled = DEFAULT_SESSION_IN_MEMORY_ENABLED;

    // expire time in seconds
    private static final int DEFAULT_EXPIRE = -2;
    private static final int NO_EXPIRE = -1;

    /**
     * Please make sure expire is longer than sesion.getTimeout()
     */
    private int expire = DEFAULT_EXPIRE;

    private static final int MILLISECONDS_IN_A_SECOND = 1000;

    private AutumnShiroRedisManager redisManager;
    private RedisSerializer<String> keySerializer = new StringSerializer();
    private RedisSerializer valueSerializer = new ObjectSerializer();
    private static ThreadLocal<Map<Serializable, AutumnSessionInMemory>> sessionsInThread = new ThreadLocal<>();

    /**
     * 在线会话递增
     */
    private long lineSessionIncr() {
        return this.redisManager.incr(getLineSessionKey());
    }

    /**
     * 在线会话递减
     */
    private long lineSessionDecr() {
        TupleTwo<Boolean, Long> result = this.redisManager.decrByIf(getLineSessionKey(), 1L, 0L);
        if (result.getItem1() && result.getItem2() != null) {
            return result.getItem2();
        }
        return 0L;
    }

    /**
     * 在线用户递增
     */
    private long lineUserIncr() {
        return this.redisManager.incr(getLineUserKey());
    }

    /**
     * 在线用户递减
     */
    private long lineUserDecr() {
        TupleTwo<Boolean, Long> result = this.redisManager.decrByIf(getLineUserKey(), 1L, 0L);
        if (result.getItem1() && result.getItem2() != null) {
            return result.getItem2();
        }
        return 0L;
    }


    @Override
    protected Serializable doCreate(Session session) {
        if (session == null) {
            logger.error("session is null");
            throw new UnknownSessionException("session is null");
        }
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        this.addSession(session);
        return sessionId;
    }

    /**
     * 添加会话
     *
     * @param session 会话
     */
    private void addSession(Session session) {
        try {
            String key = getSessionKey(session.getId());
            Long userId = this.getLongValue(key);
            if (userId == null) {
                this.lineSessionIncr();
                this.setLongValue(key, Long.MIN_VALUE);
            } else {
                AutumnSessionUser sessionUser = this.readSessionUser(session);
                if (sessionUser != null) {
                    lineUserIncr();
                    saveSessionUser(sessionUser);
                }
            }
        } catch (Exception err) {
            logger.error("添加会话[" + session.getId() + "]出错:" + err.getMessage());
        }
    }

    /**
     * 删除会话
     *
     * @param session
     */
    private void delSession(Session session) {
        try {
            String key = this.getSessionKey(session.getId());
            Long userId = this.getLongValue(key);
            if (userId != null) {
                this.deleteKey(key);
                lineSessionDecr();
                if (userId != Long.MIN_VALUE) {
                    lineUserDecr();
                    this.deleteSessionUser(userId, session.getId());
                }
            }
        } catch (Exception err) {
            logger.error("删除会话[" + session.getId() + "]出错:" + err.getMessage());
        }
    }

    /**
     * 更新会话
     *
     * @param session
     */
    private void updateSession(Session session) {
        try {
            AutumnSessionUser sessionUser = this.readSessionUser(session);
            String key = this.getSessionKey(session.getId());
            Long userId = this.getLongValue(key);
            if (userId == null || userId == Long.MIN_VALUE) {
                if (userId == null) {
                    this.lineSessionIncr();
                }
                if (sessionUser != null) {
                    this.setLongValue(key, sessionUser.getUser().getId());
                    lineUserIncr();
                } else {
                    this.setLongValue(key, Long.MIN_VALUE);
                }
            }
            if (sessionUser != null) {
                saveSessionUser(sessionUser);
            } else {
                if (userId != null && userId != Long.MIN_VALUE) {
                    this.deleteSessionUser(userId, session.getId());
                }
            }
        } catch (Exception err) {
            logger.error("更新会话[" + session.getId() + "]出错:" + err.getMessage());
        }
    }

    /**
     * 保存会话用户
     *
     * @param sessionUser
     */
    private void saveSessionUser(AutumnSessionUser sessionUser) throws SerializationException {
        if (sessionUser != null) {
            byte[] keyBytes = this.serializeString(this.getUserKey(sessionUser.getUser().getId()));
            Map<Serializable, AutumnSessionUser> userMap = this.getSessionUserMap(sessionUser.getUser().getId());
            if (userMap == null) {
                userMap = new HashMap<>(5);
            }
            userMap.put(sessionUser.getSessionId(), sessionUser);
            redisManager.set(keyBytes, this.valueSerializer.serialize(userMap));
        }
    }

    /**
     * 获取会话用户Map
     *
     * @param userId
     * @return
     */
    @Override
    protected Map<Serializable, AutumnSessionUser> getSessionUserMap(Long userId) {
        byte[] keyBytes = this.serializeString(this.getUserKey(userId));
        try {
            Object value = this.valueSerializer.deserialize(redisManager.get(keyBytes));
            if (value == null) {
                return null;
            }
            return (Map<Serializable, AutumnSessionUser>) value;
        } catch (SerializationException e) {
            return null;
        }
    }

    private void deleteSessionUser(Long userId, Serializable sessionId) throws SerializationException {
        byte[] keyBytes = this.serializeString(this.getUserKey(userId));
        Map<Serializable, AutumnSessionUser> userMap = this.getSessionUserMap(userId);
        if (userMap != null) {
            userMap.remove(sessionId);
        }
        if (userMap == null || userMap.size() == 0) {
            redisManager.del(keyBytes);
        } else {
            redisManager.set(keyBytes, this.valueSerializer.serialize(userMap));
        }
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
        if (this.sessionInMemoryEnabled) {
            this.setSessionToThreadLocal(session.getId(), session);
        }
        this.updateSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        delSession(session);
        if (this.sessionInMemoryEnabled) {
            this.deleteSessionToThreadLocal(session.getId());
        }
        try {
            redisManager.del(serializeString(getRedisSessionKey(session.getId())));
        } catch (Exception e) {
            logger.error("delete session error. session id=" + session.getId());
        }
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.warn("session id is null");
            return null;
        }
        if (this.sessionInMemoryEnabled) {
            Session session = getSessionFromThreadLocal(sessionId);
            if (session != null) {
                return session;
            }
        }
        Session session = null;
        logger.debug("read session from redis");
        try {
            session = (Session) valueSerializer.deserialize(redisManager.get(this.serializeString(getRedisSessionKey(sessionId))));
            if (this.sessionInMemoryEnabled) {
                setSessionToThreadLocal(sessionId, session);
            }
        } catch (Exception e) {
            logger.error("read session error. settionId=" + sessionId);
        }
        return session;
    }

    /**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        byte[] key = this.serializeString(getRedisSessionKey(session.getId()));
        byte[] value = this.serializeValue(session);
        if (expire == DEFAULT_EXPIRE) {
            this.redisManager.set(key, value, (int) (session.getTimeout() / MILLISECONDS_IN_A_SECOND));
            return;
        }
        if (expire != NO_EXPIRE && expire * MILLISECONDS_IN_A_SECOND < session.getTimeout()) {
            logger.warn("Redis session expire time: "
                    + (expire * MILLISECONDS_IN_A_SECOND)
                    + " is less than Session timeout: "
                    + session.getTimeout()
                    + " . It may cause some problems.");
        }
        this.redisManager.set(key, value, expire);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        try {
            Set<byte[]> keys = redisManager.keys(this.serializeString(this.keyPrefix + "*"));
            if (keys != null && keys.size() > 0) {
                for (byte[] key : keys) {
                    Session s = (Session) valueSerializer.deserialize(redisManager.get(key));
                    sessions.add(s);
                }
            }
        } catch (Exception e) {
            logger.error("get active sessions error.");
        }
        return sessions;
    }

    private void setSessionToThreadLocal(Serializable sessionId, Session s) {
        Map<Serializable, AutumnSessionInMemory> sessionMap = sessionsInThread.get();
        if (sessionMap == null) {
            sessionMap = new HashMap<>(5);
            sessionsInThread.set(sessionMap);
        } else {
            removeExpiredSessionInMemory(sessionMap);
        }
        AutumnSessionInMemory sessionInMemory = new AutumnSessionInMemory(s, this.getSessionInMemoryTimeout());
        sessionMap.put(sessionId, sessionInMemory);
    }

    /**
     * 删除内存会话
     *
     * @param sessionId
     */
    private void deleteSessionToThreadLocal(Serializable sessionId) {
        Map<Serializable, AutumnSessionInMemory> sessionMap = sessionsInThread.get();
        if (sessionMap == null) {
            return;
        }
        sessionMap.remove(sessionId);
    }

    private void removeExpiredSessionInMemory(Map<Serializable, AutumnSessionInMemory> sessionMap) {
        Iterator<Serializable> it = sessionMap.keySet().iterator();
        while (it.hasNext()) {
            Serializable sessionId = it.next();
            AutumnSessionInMemory sessionInMemory = sessionMap.get(sessionId);
            if (sessionInMemory == null) {
                it.remove();
                continue;
            }
            if (sessionInMemory.isExpire()) {
                it.remove();
            }
        }
    }

    private Session getSessionFromThreadLocal(Serializable sessionId) {
        Map<Serializable, AutumnSessionInMemory> sessionMap = sessionsInThread.get();
        if (sessionMap == null) {
            return null;
        }
        AutumnSessionInMemory sessionInMemory = sessionMap.get(sessionId);
        if (sessionInMemory == null) {
            return null;
        }
        if (sessionInMemory.isExpire()) {
            sessionMap.remove(sessionId);
            return null;
        }
        return sessionInMemory.getSession();
    }


    private String getRedisSessionKey(Serializable sessionId) {
        return this.keyPrefix + sessionId;
    }

    public AutumnShiroRedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(AutumnShiroRedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public RedisSerializer getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(RedisSerializer keySerializer) {
        this.keySerializer = keySerializer;
    }

    public RedisSerializer getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(RedisSerializer valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public long getSessionInMemoryTimeout() {
        return sessionInMemoryTimeout;
    }

    public void setSessionInMemoryTimeout(long sessionInMemoryTimeout) {
        this.sessionInMemoryTimeout = sessionInMemoryTimeout;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public boolean getSessionInMemoryEnabled() {
        return sessionInMemoryEnabled;
    }

    public void setSessionInMemoryEnabled(boolean sessionInMemoryEnabled) {
        this.sessionInMemoryEnabled = sessionInMemoryEnabled;
    }

    public static ThreadLocal getSessionsInThread() {
        return sessionsInThread;
    }

    /**
     * 读取会话键数量
     *
     * @return
     */
    private long redisSessionKeys() {
        try {
            Long result = this.redisManager.dbSize(this.keySerializer.serialize(this.keyPrefix + "*"));
            if (result != null) {
                result.longValue();
            }
        } catch (SerializationException e) {

        }
        return 0L;
    }

    /**
     * 序列化字符
     *
     * @param str
     * @return
     */
    private byte[] serializeString(String str) {
        if (str == null) {
            str = "";
        }
        try {
            return this.keySerializer.serialize(str);
        } catch (SerializationException e) {
            throw ExceptionUtils.throwSystemException("序列化会话 String [" + str + "]出错:" + e.getMessage());
        }
    }

    /**
     * 序列化值
     *
     * @param key
     * @return
     */
    private byte[] serializeValue(Object value) {
        try {
            return this.valueSerializer.serialize(value);
        } catch (SerializationException e) {
            throw ExceptionUtils.throwSystemException("序列化会话 Value [" + value + "]出错:" + e.getMessage());
        }
    }

    /**
     * 删除键
     *
     * @param key
     */
    private void deleteKey(String key) {
        this.redisManager.del(serializeString(key));
    }

    private Long getLongValue(String key) {
        byte[] result = this.redisManager.get(serializeString(key));
        if (result == null || result.length == 0) {
            return null;
        }
        try {
            String value = keySerializer.deserialize(result);
            if (StringUtils.isNullOrBlank(value)) {
                return null;
            }
            return Long.parseLong(value);
        } catch (SerializationException e) {
            return null;
        }
    }

    /**
     * 设置整数值
     *
     * @param key
     * @param value
     */
    private void setLongValue(String key, long value) {
        byte[] bytes = this.serializeString(Long.toString(value));
        this.redisManager.set(serializeString(key), bytes);
    }

    @Override
    public long onLineSessions() {
        Long value = this.getLongValue(getLineSessionKey());
        return value != null ? value : 0L;
    }

    @Override
    public long onLineUsers() {
        Long value = this.getLongValue(getLineUserKey());
        return value != null ? value : 0L;
    }

    @Override
    public void deleteAllSessionUser() {
        this.redisManager.deleteByMatches(this.getManagerBaseKeyPrefix() + "*");
        this.redisManager.deleteByMatches(this.keyPrefix + "*");
        logger.info("删队所有用户");
    }

    @Override
    public void synSession() {
        try {
            long lineSessionCount = 0L;
            long lineUserCount = 0L;
            this.setLongValue(getLineSessionKey(), lineSessionCount);
            this.setLongValue(getLineUserKey(), lineUserCount);
            this.redisManager.deleteByMatches(this.getAllUserKey());
            this.redisManager.deleteByMatches(this.getSessionKey("*"));
            Set<byte[]> keys = this.redisManager.keys(this.serializeString(this.keyPrefix + "*"));
            if (keys != null && keys.size() > 0) {
                for (byte[] key : keys) {
                    lineSessionCount++;
                    Session session = (Session) valueSerializer.deserialize(redisManager.get(key));
                    AutumnSessionUser sessionUser = this.readSessionUser(session);
                    if (sessionUser != null) {
                        this.setLongValue(this.getSessionKey(sessionUser.getSessionId()), sessionUser.getUser().getId());
                        this.saveSessionUser(sessionUser);
                        lineUserCount++;
                    }
                }
            }
            this.setLongValue(getLineSessionKey(), lineSessionCount);
            this.setLongValue(getLineUserKey(), lineUserCount);
            logger.info("同步所有会话成功");
        } catch (Exception err) {
            throw ExceptionUtils.throwSystemException("同步会话出错：" + err.getMessage(), err);
        }
    }

}
