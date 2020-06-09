package com.autumn.zero.ueditor.action.impl;

import com.autumn.runtime.session.AutumnSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 动作抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-24 19:14
 **/
public abstract class AbstractAction {

    /**
     * 会话
     */
    @Autowired
    protected AutumnSession session;

    private boolean checkLogin;

    public AbstractAction() {
        this.checkLogin = true;
    }

    /**
     * 是否检查登录(未登录禁止非配置之前的请求)
     *
     * @return
     */
    public boolean isCheckLogin() {
        return this.checkLogin;
    }

    /**
     * 设置是否检查登录
     *
     * @param checkLogin 检查登录
     */
    public void setCheckLogin(boolean checkLogin) {
        this.checkLogin = checkLogin;
    }

    /**
     * 是否已登录
     */
    public boolean isLogin() {
        return this.session != null && this.session.isAuthenticated();
    }
}
