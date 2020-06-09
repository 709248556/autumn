package com.autumn.runtime.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

/**
 * 默认会话管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-01 23:19
 **/
public class DefaultAutumnSessionManager extends AbstractAutumnSessionManager {

    @Override
    public AutumnSessionUser readSessionUser(Serializable sessionId) {
        return null;
    }

    @Override
    public Collection<AutumnSessionUser> readSessionUsers(Long userId) {
        return Collections.unmodifiableList(new ArrayList<>());
    }

    @Override
    public void deleteSession(Serializable sessionId) {

    }

    @Override
    public int deleteSessionUser(Long userId, Predicate<AutumnSessionUser> condition) {
        return 0;
    }

    @Override
    public void deleteAllSessionUser() {

    }

    @Override
    public void synSession() {

    }

    @Override
    public long onLineSessions() {
        return 0L;
    }

    @Override
    public long onLineUsers() {
        return 0L;
    }
}
