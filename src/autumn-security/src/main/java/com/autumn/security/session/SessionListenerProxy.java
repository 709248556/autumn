package com.autumn.security.session;

import org.apache.shiro.session.SessionListener;

/**
 * 会话监听代理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-01 20:14
 **/
public interface SessionListenerProxy extends AutumnSessionListener {

    /**
     * 注册监听
     *
     * @param listener 监听
     */
    void registerListener(SessionListener listener);
}
