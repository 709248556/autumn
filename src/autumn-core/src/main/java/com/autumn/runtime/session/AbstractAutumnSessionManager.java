package com.autumn.runtime.session;

/**
 * 会话管理抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-01 23:20
 **/
public abstract class AbstractAutumnSessionManager implements AutumnSessionManager {

    @Override
    public int deleteSessionUser(Long userId) {
        return this.deleteSessionUser(userId, null);
    }
}
