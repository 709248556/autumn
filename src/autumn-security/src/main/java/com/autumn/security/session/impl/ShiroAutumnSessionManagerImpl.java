package com.autumn.security.session.impl;

import com.autumn.runtime.session.AbstractAutumnSessionManager;
import com.autumn.runtime.session.AutumnSessionUser;
import com.autumn.security.session.AutumnSessionDAO;
import com.autumn.security.session.ShiroAutumnSessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * 会话管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 01:53
 **/
public class ShiroAutumnSessionManagerImpl extends AbstractAutumnSessionManager implements ShiroAutumnSessionManager {

    /**
     * 日志
     */
    protected final Log logger = LogFactory.getLog(this.getClass());

    protected final AutumnSessionDAO sessionDAO;

    /**
     * 实例化
     *
     * @param sessionDAO
     */
    public ShiroAutumnSessionManagerImpl(AutumnSessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @Override
    public void synSession() {
        this.sessionDAO.synSession();
    }

    @Override
    public long onLineSessions() {
        return this.sessionDAO.onLineSessions();
    }

    @Override
    public long onLineUsers() {
        return this.sessionDAO.onLineUsers();
    }

    @Override
    public AutumnSessionUser readSessionUser(Serializable sessionId) {
        return this.sessionDAO.readSessionUser(sessionId);
    }

    @Override
    public void deleteSession(Serializable sessionId) {
        SimpleSession simpleSession = new SimpleSession();
        simpleSession.setId(sessionId);
        this.sessionDAO.delete(simpleSession);
    }

    @Override
    public Collection<AutumnSessionUser> readSessionUsers(Long userId) {
        return this.sessionDAO.readSessionUsers(userId);
    }

    @Override
    public int deleteSessionUser(Long userId, Predicate<AutumnSessionUser> condition) {
        return this.sessionDAO.deleteSessionUser(userId, condition);
    }

    @Override
    public void deleteAllSessionUser() {
        this.sessionDAO.deleteAllSessionUser();
    }

}
