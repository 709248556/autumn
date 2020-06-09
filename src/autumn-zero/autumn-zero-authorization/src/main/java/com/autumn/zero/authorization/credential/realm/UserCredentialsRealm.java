package com.autumn.zero.authorization.credential.realm;

import com.autumn.runtime.session.AutumnUser;
import com.autumn.security.credential.AutumnUserCredentialsService;
import com.autumn.security.token.AutumnAuthenticationToken;
import com.autumn.util.tuple.TupleTwo;
import com.autumn.zero.authorization.entities.common.AbstractUser;

/**
 * 用户 Token 认证
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 13:18
 **/
public interface UserCredentialsRealm {

    /**
     * 获取默认Token类型
     *
     * @return
     */
    Class<? extends AutumnAuthenticationToken> getDefaultTokenClass();

    /**
     * 是否支持
     *
     * @param token 票据
     * @return
     */
    boolean supports(AutumnAuthenticationToken token);

    /**
     * 调用会话用户
     *
     * @param service 服务
     * @param token   票据
     * @return
     */
    TupleTwo<AbstractUser, AutumnUser> doGetUser(AutumnUserCredentialsService service, AutumnAuthenticationToken token);


}
