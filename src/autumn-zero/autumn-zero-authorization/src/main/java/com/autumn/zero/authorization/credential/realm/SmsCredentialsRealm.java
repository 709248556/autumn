package com.autumn.zero.authorization.credential.realm;

import com.autumn.runtime.session.DefaultAutumnUser;
import com.autumn.security.token.SmsAutumnAuthenticationToken;
import com.autumn.security.token.ToeknAuditedLog;
import com.autumn.util.tuple.TupleTwo;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;

/**
 * 短信认证
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 19:26
 **/
public class SmsCredentialsRealm extends AbstractUserCredentialsRealm<SmsAutumnAuthenticationToken> {

    /**
     * 实例化
     *
     * @param authorizationService 授权服务
     */
    public SmsCredentialsRealm(AuthorizationServiceBase authorizationService) {
        super(authorizationService);
    }

    @Override
    protected TupleTwo<AbstractUser, DefaultAutumnUser> doGetUser(SmsAutumnAuthenticationToken token) {
        AbstractUser user = this.authorizationService.findUserByPhoneNumber(token.getMobilePhone(), false);
        if (user == null) {
            ToeknAuditedLog auditedLog = token.createAuditedLog();
            this.loginAuditedLog.addFailLogByProvider(auditedLog.getProvider(), auditedLog.getProviderKey(), auditedLog.getFailStatusMessage());
            throw this.createCredentialsException("无效的短信登录[" + auditedLog.getProvider() + " " + auditedLog.getProviderKey() + "]", auditedLog.getFailStatusMessage());
        }
        if (!user.getIsActivatePhone()) {
            this.authorizationService.activePhone(user);
        }
        return this.createUser(user);
    }

}
