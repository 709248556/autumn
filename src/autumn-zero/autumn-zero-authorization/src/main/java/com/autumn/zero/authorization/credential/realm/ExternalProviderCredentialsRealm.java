package com.autumn.zero.authorization.credential.realm;

import com.autumn.runtime.session.DefaultAutumnUser;
import com.autumn.security.token.ExternalProviderAuthenticationToken;
import com.autumn.security.token.ToeknAuditedLog;
import com.autumn.util.tuple.TupleTwo;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;

/**
 * 第三方登录认证
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 18:28
 **/
public class ExternalProviderCredentialsRealm
        extends AbstractUserCredentialsRealm<ExternalProviderAuthenticationToken> {

    /**
     * 实例化
     *
     * @param authorizationService 授权服务
     */
    public ExternalProviderCredentialsRealm(AuthorizationServiceBase authorizationService) {
        super(authorizationService);
    }

    @Override
    protected TupleTwo<AbstractUser, DefaultAutumnUser> doGetUser(ExternalProviderAuthenticationToken token) {
        AbstractUser user = this.authorizationService.findUserByExternalProvider(token.getProvider(),
                token.getProviderKey(),
                false);
        if (user == null) {
            ToeknAuditedLog auditedLog = token.createAuditedLog();
            String msg = "无效的第三方提供程序或键";
            this.loginAuditedLog.addFailLogByProvider(auditedLog.getProvider(), auditedLog.getProviderKey(), msg);
            throw this.createCredentialsException("无效的第三方提供程序[" + auditedLog.getProvider() + " " + auditedLog.getProviderKey() + "]", msg);
        }
        return this.createUser(user);
    }
}
