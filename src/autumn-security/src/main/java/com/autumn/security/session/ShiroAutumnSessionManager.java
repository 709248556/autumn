package com.autumn.security.session;

import com.autumn.runtime.session.AutumnSessionManager;

/**
 * 会话管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-01 22:48
 **/
public interface ShiroAutumnSessionManager extends AutumnSessionManager {

    /**
     * 会话管理前缀
     */
    public static final String SESSION_MANAGER_PREFIX_KEY = "autumn_session_manager:";

}
