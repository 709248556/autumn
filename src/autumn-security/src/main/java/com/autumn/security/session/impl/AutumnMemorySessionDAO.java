package com.autumn.security.session.impl;

import com.autumn.runtime.session.AutumnSessionUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 内存会话DAO
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 20:19
 **/
public class AutumnMemorySessionDAO extends AbstractAutumnSessionDAO {

    protected static final Logger logger = LoggerFactory.getLogger(AutumnMemorySessionDAO.class);
    protected final ConcurrentMap<Serializable, Session> sessions;
    private final ConcurrentMap<Long, Map<Serializable, AutumnSessionUser>> userSessionMap;
    private final ConcurrentMap<Serializable, Long> sessionUserMap;

    public AutumnMemorySessionDAO() {
        this.sessions = new ConcurrentHashMap<>(100);
        this.userSessionMap = new ConcurrentHashMap(100);
        this.sessionUserMap = new ConcurrentHashMap(100);
    }

    /**
     * 添加会话
     *
     * @param session 会话
     */
    private void addSession(Session session) {
        try {
            synchronized (this) {
                AutumnSessionUser sessionUser = this.readSessionUser(session);
                if (sessionUser != null) {
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
            synchronized (this) {
                Long userId = this.sessionUserMap.remove(session.getId());
                if (userId != null) {
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
            synchronized (this) {
                AutumnSessionUser sessionUser = this.readSessionUser(session);
                Long userId = this.sessionUserMap.get(session.getId());
                if (sessionUser != null) {
                    saveSessionUser(sessionUser);
                } else {
                    if (userId != null) {
                        this.sessionUserMap.remove(session.getId());
                        this.deleteSessionUser(userId, session.getId());
                    }
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
    private void saveSessionUser(AutumnSessionUser sessionUser) {
        if (sessionUser != null) {
            this.sessionUserMap.put(sessionUser.getSessionId(), sessionUser.getUser().getId());
            Map<Serializable, AutumnSessionUser> userMap = this.userSessionMap.computeIfAbsent(sessionUser.getUser().getId(), (k) -> new ConcurrentHashMap<>(5));
            userMap.put(sessionUser.getSessionId(), sessionUser);
        }
    }

    private void deleteSessionUser(Long userId, Serializable sessionId) {
        Map<Serializable, AutumnSessionUser> userMap = this.userSessionMap.get(userId);
        if (userMap != null) {
            userMap.remove(sessionId);
        }
        if (userMap == null || userMap.size() == 0) {
            this.userSessionMap.remove(userId);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        storeSession(sessionId, session);
        this.addSession(session);
        return sessionId;
    }

    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        }
        return sessions.putIfAbsent(id, session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        storeSession(session.getId(), session);
        this.updateSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null) {
            throw new NullPointerException("session argument cannot be null.");
        }
        Serializable id = session.getId();
        if (id != null) {
            sessions.remove(id);
        }
        delSession(session);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Collection<Session> values = sessions.values();
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableCollection(values);
        }
    }

    @Override
    public long onLineSessions() {
        return this.sessions.size();
    }

    @Override
    public long onLineUsers() {
        return this.sessionUserMap.size();
    }

    @Override
    public void deleteAllSessionUser() {
        synchronized (this) {
            sessions.clear();
            userSessionMap.clear();
            sessionUserMap.clear();
            logger.info("删队所有用户");
        }
    }

    @Override
    public void synSession() {

    }

    @Override
    protected Map<Serializable, AutumnSessionUser> getSessionUserMap(Long userId) {
        return this.userSessionMap.get(this.getUserKey(userId));
    }
}
