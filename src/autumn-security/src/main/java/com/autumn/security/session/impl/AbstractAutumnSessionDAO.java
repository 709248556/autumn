package com.autumn.security.session.impl;

import com.autumn.runtime.session.AutumnSessionUser;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.security.session.AutumnSessionDAO;
import com.autumn.security.session.ShiroAutumnSessionManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 21:52
 **/
public abstract class AbstractAutumnSessionDAO extends AbstractSessionDAO implements AutumnSessionDAO {

    /**
     * 会话键前缀
     */
    public final static String SESSION_MANAGER_KEY_PREFIX = ShiroAutumnSessionManager.SESSION_MANAGER_PREFIX_KEY;

    /**
     * 在线会话
     */
    public final static String LINE_SESSION_KEY = SESSION_MANAGER_KEY_PREFIX + "lineSessions";

    /**
     * 在线用户
     */
    public final static String LINE_USER_KEY = SESSION_MANAGER_KEY_PREFIX + "lineUsers";

    /**
     * 会话id键前缀
     */
    public final static String SESSION_ID_KEY_PREFIX = SESSION_MANAGER_KEY_PREFIX + "sessionId:";

    /**
     * 用户id键前缀
     */
    public final static String USER_ID_KEY_PREFIX = SESSION_MANAGER_KEY_PREFIX + "userId:";

    /**
     * 读取会话用户
     *
     * @param session
     * @return
     */
    protected AutumnSessionUser readSessionUser(Session session) {
        if (session != null) {
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (obj instanceof PrincipalCollection) {
                PrincipalCollection principalCollection = (PrincipalCollection) obj;
                Object principal = principalCollection.getPrimaryPrincipal();
                if (principal instanceof AutumnUser) {
                    return new AutumnSessionUser(
                            session.getId(),
                            session.getStartTimestamp(),
                            session.getHost(),
                            (AutumnUser) principal);
                }
            }
        }
        return null;
    }

    /**
     * 获取基础键前缀
     *
     * @return
     */
    protected String getManagerBaseKeyPrefix() {
        return SESSION_MANAGER_KEY_PREFIX;
    }

    /**
     * 获取在线会话键
     *
     * @return
     */
    protected String getLineSessionKey() {
        return LINE_SESSION_KEY;
    }

    /**
     * 获取在线用户键
     *
     * @return
     */
    protected String getLineUserKey() {
        return LINE_USER_KEY;
    }

    /**
     * 获取会话键
     *
     * @return
     */
    protected String getSessionKey(Serializable sessionId) {
        return SESSION_ID_KEY_PREFIX + sessionId;
    }

    /**
     * 获取用户键
     *
     * @return
     */
    protected String getUserKey(long userId) {
        return USER_ID_KEY_PREFIX + userId;
    }

    /**
     * 获取所有用户键
     *
     * @return
     */
    protected String getAllUserKey() {
        return USER_ID_KEY_PREFIX + "*";
    }

    /**
     * 读取会话用户 Map
     *
     * @param userId
     * @return
     */
    protected abstract Map<Serializable, AutumnSessionUser> getSessionUserMap(Long userId);

    @Override
    public AutumnSessionUser readSessionUser(Serializable sessionId) {
        Session session = this.readSession(sessionId);
        return this.readSessionUser(session);
    }

    @Override
    public Collection<AutumnSessionUser> readSessionUsers(Long userId) {
        Map<Serializable, AutumnSessionUser> userMap = this.getSessionUserMap(userId);
        if (userMap == null) {
            return Collections.unmodifiableList(new ArrayList<>());
        }
        return userMap.values();
    }

    @Override
    public int deleteSessionUser(Long userId) {
        return this.deleteSessionUser(userId, null);
    }

    @Override
    public int deleteSessionUser(Long userId, Predicate<AutumnSessionUser> condition) {
        Collection<AutumnSessionUser> sessionUsers = this.readSessionUsers(userId);
        int count = 0;
        for (AutumnSessionUser sessionUser : sessionUsers) {
            if (condition == null || condition.test(sessionUser)) {
                SimpleSession simpleSession = new SimpleSession();
                simpleSession.setId(sessionUser.getSessionId());
                this.delete(simpleSession);
                count++;
            }
        }
        return count;
    }
}
