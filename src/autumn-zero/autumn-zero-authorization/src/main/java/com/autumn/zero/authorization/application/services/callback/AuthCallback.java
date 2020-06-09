package com.autumn.zero.authorization.application.services.callback;

import com.autumn.runtime.session.AutumnSession;

/**
 * 授权回调
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 10:58
 **/
public interface AuthCallback {

    /**
     * 模块资源类型
     *
     * @param session 会话
     * @return
     */
    int moduleResourcesType(AutumnSession session);

    /**
     * 创建随机用户名
     *
     * @return
     */
    String createRandomUserName();

}
