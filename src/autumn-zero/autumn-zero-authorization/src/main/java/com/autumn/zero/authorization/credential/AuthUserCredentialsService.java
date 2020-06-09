package com.autumn.zero.authorization.credential;

import com.autumn.security.credential.AutumnUserCredentialsService;
import com.autumn.zero.authorization.credential.realm.UserCredentialsRealm;

/**
 * 授权认证服务
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 23:16
 **/
public interface AuthUserCredentialsService extends AutumnUserCredentialsService {

    /**
     * 注册 realm
     *
     * @param realm
     */
    void registerRealm(UserCredentialsRealm realm);

}
