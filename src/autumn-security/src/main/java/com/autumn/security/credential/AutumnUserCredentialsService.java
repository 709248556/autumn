package com.autumn.security.credential;

import com.autumn.audited.ClientInfoProvider;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.security.AutumnAccountCredentialsException;
import com.autumn.security.token.AutumnAuthenticationToken;

/**
 * 用户认证服务
 *
 * @author 老码农
 * <p>
 * 2017-11-04 15:14:28
 */
public interface AutumnUserCredentialsService {

    /**
     * 是否匹配
     *
     * @param user
     * @param token
     * @return
     */
    boolean matches(AutumnUser user, AutumnAuthenticationToken token);

    /**
     * 根据用户票据加载用户
     *
     * @param token 登录票据
     * @throws AutumnAccountCredentialsException 认证无效而引发的异常
     */
    AutumnUser doGetUserByToken(AutumnAuthenticationToken token) throws AutumnAccountCredentialsException;

    /**
     * 生成身份类型
     *
     * @param token              登录票据
     * @param clientInfoProvider 客户端信息提供者
     * @return
     */
    String generateIdentityType(AutumnAuthenticationToken token, ClientInfoProvider clientInfoProvider);

}
