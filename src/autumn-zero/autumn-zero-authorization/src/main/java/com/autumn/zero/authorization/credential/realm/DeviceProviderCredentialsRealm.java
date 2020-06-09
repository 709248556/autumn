package com.autumn.zero.authorization.credential.realm;

import com.autumn.exception.ExceptionUtils;
import com.autumn.runtime.session.DefaultAutumnUser;
import com.autumn.security.token.DeviceAuthenticationToken;
import com.autumn.security.token.ToeknAuditedLog;
import com.autumn.timing.Clock;
import com.autumn.util.StringUtils;
import com.autumn.util.tuple.TupleTwo;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.entities.common.UserDeviceAuthLogin;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;

import java.util.Date;

/**
 * 设备认证
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 19:35
 **/
public class DeviceProviderCredentialsRealm extends AbstractUserCredentialsRealm<DeviceAuthenticationToken> {

    /**
     * 实例化
     *
     * @param authorizationService 授权服务
     */
    public DeviceProviderCredentialsRealm(AuthorizationServiceBase authorizationService) {
        super(authorizationService);
    }

    @Override
    protected TupleTwo<AbstractUser, DefaultAutumnUser> doGetUser(DeviceAuthenticationToken token) {
        String msg;
        if (token.getCredentialsDeviceInfo() == null || StringUtils.isNullOrBlank(token.getCredentialsDeviceInfo().getDeviceIdentification())) {
            msg = "Token 或 DeviceId 为空";
            throw this.createCredentialsException(msg, msg);
        }
        ToeknAuditedLog auditedLog = token.createAuditedLog();
        UserDeviceAuthLogin authLogin = authorizationService.findUserDeviceAuth(token.getToken());
        if (authLogin == null) {
            msg = "无效的 token 或 设备信息";
            this.loginAuditedLog.addFailLogByProvider(auditedLog.getProvider(), auditedLog.getProviderKey(), msg);
            throw this.createCredentialsException("无效的设备信息[" + auditedLog.getProvider() + " " + auditedLog.getProviderKey() + "]", msg);
        }
        if (authLogin.getExpires() > 0 && authLogin.getFirstLoginTime() != null) {
            Date expires = new Date(authLogin.getFirstLoginTime().getTime() + (authLogin.getExpires() * 1000));
            if (expires.getTime() < Clock.gmtNow().getTime()) {
                msg = "登录凭证已过期";
                throw this.createCredentialsException("登录凭证已过期[" + auditedLog.getProvider() + " " + auditedLog.getProviderKey() + "]", msg);
            }
        }
        AbstractUser user = this.authorizationService.getUser(authLogin.getUserId(), false);
        if (authLogin == null) {
            throw ExceptionUtils.throwSystemException("系统出错:存在用户设备信息，但不存在用户。");
        }
        if (authLogin.getFirstLoginTime() == null) {
            authLogin.setFirstLoginTime(Clock.gmtNow());
        }
        authLogin.setLastLoginTime(Clock.gmtNow());
        authLogin = this.authorizationService.addOrUpdateUserDeviceInfo(authLogin);
        TupleTwo<AbstractUser, DefaultAutumnUser> userTupleTwo = this.createUser(user);
        this.setSessionUserDeviceInfo(userTupleTwo.getItem2(), authLogin);
        return userTupleTwo;
    }
}
