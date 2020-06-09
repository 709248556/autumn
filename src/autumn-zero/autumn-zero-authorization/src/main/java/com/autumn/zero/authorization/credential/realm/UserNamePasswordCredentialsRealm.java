package com.autumn.zero.authorization.credential.realm;

import com.autumn.runtime.session.DefaultAutumnUser;
import com.autumn.security.token.UserNamePasswordAuthenticationToken;
import com.autumn.util.tuple.TupleTwo;
import com.autumn.validation.MatchesUtils;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;

/**
 * 用户名密码认证
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 17:14
 **/
public class UserNamePasswordCredentialsRealm
        extends AbstractUserCredentialsRealm<UserNamePasswordAuthenticationToken> {

    /**
     * 实例化
     *
     * @param authorizationService 授权服务
     */
    public UserNamePasswordCredentialsRealm(AuthorizationServiceBase authorizationService) {
        super(authorizationService);
    }

    @Override
    protected TupleTwo<AbstractUser, DefaultAutumnUser> doGetUser(UserNamePasswordAuthenticationToken token) {
        String username = token.getUsername().trim();
        AbstractUser user = this.authorizationService.findUserByAccount(username, false);
        if (user == null) {
            this.loginAuditedLog.addFailLogByAccount(username, "账户或密码出错");
            throw this.createCredentialsException("账户:" + username + "无效", "账户或密码出错");
        }
        if (!user.getIsActivatePhone() && MatchesUtils.isMobilePhone(username)) {
            this.loginAuditedLog.addFailLogByAccount(username, "手机号未激活");
            throw this.createCredentialsException("手机号:" + username + " 未激活", "手机号未激活");
        }
        if (!user.getIsActivateEmail() && MatchesUtils.isEmail(username)) {
            this.loginAuditedLog.addFailLogByAccount(username, "邮箱未激活");
            throw this.createCredentialsException("邮箱:" + username + " 未激活", "邮箱未激活");
        }
        return this.createUser(user);
    }

}
