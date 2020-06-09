package com.autumn.security.realm;

import com.autumn.runtime.session.AutumnUser;
import com.autumn.security.AutumnAccountCredentialsException;
import com.autumn.security.AutumnAccountStatusException;
import com.autumn.security.AutumnAuthenticationException;
import com.autumn.security.constants.UserStatusConstants;
import com.autumn.security.context.AutumnSecurityContextHolder;
import com.autumn.security.credential.AutumnUserCredentialsService;
import com.autumn.security.crypto.AutumnPasswordEncode;
import com.autumn.security.token.AutumnAuthenticationToken;
import com.autumn.security.token.UserNamePasswordAuthenticationToken;
import com.autumn.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * 授权
 *
 * @author 老码农 2018-12-06 02:04:39
 */
public class AutumnAuthorizingRealm extends AuthorizingRealm {

    /**
     * 日志
     */
    private static final Log logger = LogFactory.getLog(AutumnAuthorizingRealm.class);

    private final AutumnPasswordEncode passwordProvider;
    private final AutumnUserCredentialsService credentialsService;

    /**
     * @param passwordProvider
     * @param credentialsService
     */
    public AutumnAuthorizingRealm(AutumnPasswordEncode passwordProvider,
                                  AutumnUserCredentialsService credentialsService) {
        this.passwordProvider = passwordProvider;
        this.credentialsService = credentialsService;

    }

    public AutumnPasswordEncode getPasswordProvider() {
        return passwordProvider;
    }

    public AutumnUserCredentialsService getCredentialsService() {
        return credentialsService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        AutumnUser user;
        if (principals.getPrimaryPrincipal() instanceof AutumnUser) {
            user = (AutumnUser) principals.getPrimaryPrincipal();
        } else {
            user = AutumnSecurityContextHolder.getAutumnUser();
        }
        if (user == null) {
            return authorizationInfo;
        }
        Set<String> roles = user.getRoles();
        if (roles != null) {
            for (String role : roles) {
                if (!StringUtils.isNullOrBlank(role)) {
                    authorizationInfo.addRole(role);
                }
            }
        }
        Set<String> permissions = user.getPermissions();
        if (permissions != null) {
            for (String permission : permissions) {
                if (!StringUtils.isNullOrBlank(permission)) {
                    authorizationInfo.addStringPermission(permission);
                }
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token instanceof AutumnAuthenticationToken) {
            AutumnAuthenticationToken authToken = (AutumnAuthenticationToken) token;
            AutumnUser user = this.getCredentialsService().doGetUserByToken(authToken);
            if (user == null) {
                throw new AutumnAccountCredentialsException("无效的用户。");
            }
            this.checkUser(authToken, user);
            return new SimpleAuthenticationInfo(user, authToken.getCredentials(), getName());
        } else if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken upt = (UsernamePasswordToken) token;
            UserNamePasswordAuthenticationToken convertUpt = new UserNamePasswordAuthenticationToken(upt.getUsername(), upt.getPassword());
            AutumnUser user = this.getCredentialsService().doGetUserByToken(convertUpt);
            if (user == null) {
                throw new AutumnAccountCredentialsException("无效的用户。");
            }
            this.checkUser(convertUpt, user);
            return new SimpleAuthenticationInfo(user, convertUpt.getCredentials(), getName());
        }
        logger.error("不支持的认证类型:" + token.getClass().getName());
        throw new AutumnAuthenticationException("不支持登录方式。");
    }

    /**
     * @param user
     */
    private void checkUser(AutumnAuthenticationToken token, AutumnUser user) {
        if (user.getStatus() == null) {
            logger.error("用户名:" + user.getUserName() + " 的状态无效,无法登录。");
            throw new AutumnAccountStatusException("账户状态无效，不能登录。");
        }
        switch (user.getStatus()) {
            case UserStatusConstants.LOCKING:
                throw new AutumnAccountStatusException("账户已锁定，不能登录。");
            case UserStatusConstants.NOT_ACTIVATE:
                throw new AutumnAccountStatusException("账户未激活，不能登录。");
            case UserStatusConstants.EXPIRED:
                throw new AutumnAccountStatusException("账户已过期，不能登录。");
            default:
                break;
        }
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AutumnAuthenticationToken;
    }

}
