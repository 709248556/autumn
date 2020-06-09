package com.autumn.security.session;

import com.autumn.runtime.session.AutumnUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * 会话监听
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 13:35
 **/
public interface AutumnSessionListener extends SessionListener {

    /**
     * 成功登录
     *
     * @param session 会话
     * @param user    用户
     */
    void onStart(Session session, AutumnUser user);

    /**
     * 注销
     *
     * @param session
     * @param user
     */
    void onStop(Session session, AutumnUser user);

    /**
     * 过期
     *
     * @param session
     * @param user
     */
    void onExpiration(Session session, AutumnUser user);
}
