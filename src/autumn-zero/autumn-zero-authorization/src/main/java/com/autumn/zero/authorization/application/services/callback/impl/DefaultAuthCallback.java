package com.autumn.zero.authorization.application.services.callback.impl;

import com.autumn.runtime.session.AutumnSession;
import com.autumn.zero.authorization.application.services.callback.AuthCallback;
import com.autumn.zero.authorization.utils.AuthUtils;

/**
 * 默认授权回调
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 11:36
 **/
public class DefaultAuthCallback implements AuthCallback {

    /**
     * 默认资源模块类型
     */
    public static final int DEFAULT_MODULE_RESOURCES_TYPE = 1;

    public DefaultAuthCallback() {

    }

    @Override
    public int moduleResourcesType(AutumnSession session) {
        return DEFAULT_MODULE_RESOURCES_TYPE;
    }

    @Override
    public String createRandomUserName() {
        return AuthUtils.randomUserName();
    }

}
