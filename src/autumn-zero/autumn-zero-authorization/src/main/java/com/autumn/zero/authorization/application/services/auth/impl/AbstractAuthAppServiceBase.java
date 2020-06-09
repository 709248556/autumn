package com.autumn.zero.authorization.application.services.auth.impl;

import com.autumn.application.service.AbstractApplicationService;
import com.autumn.exception.ExceptionUtils;
import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.runtime.session.claims.IdentityClaims;
import com.autumn.security.token.UserNamePasswordAuthenticationToken;
import com.autumn.spring.boot.properties.AutumnAuthProperties;
import com.autumn.util.StringUtils;
import com.autumn.zero.authorization.application.dto.auth.*;
import com.autumn.zero.authorization.application.services.auth.AuthAppServiceBase;
import com.autumn.zero.authorization.application.services.callback.AuthCallback;
import com.autumn.zero.authorization.application.services.captcha.ImageCaptchaAppService;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.utils.AuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 授权应用服务抽象
 *
 * @param <TUser>          用户类型
 * @param <TRole>          角色类型
 * @param <TUserLoginInfo> 用户登录信息
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 16:08
 */
public abstract class AbstractAuthAppServiceBase<TUser extends AbstractUser, TRole extends AbstractRole, TUserLoginInfo extends UserLoginInfoOutput>
        extends AbstractApplicationService implements AuthAppServiceBase<TUser, TUserLoginInfo> {

    /**
     * 用户名与密码登录错误次数
     */
    private static final String USER_LOGIN_ERROR_COUNT = "USER_LOGIN_ERROR_COUNT_SESSION:";

    /**
     * 模板
     */
    @Autowired
    protected AutumnRedisTemplate redisTemplate;

    /**
     * cookie 名称
     */
    @Autowired
    protected DefaultWebSessionManager webSessionManager;

    /**
     * 授权属性
     */
    @Autowired
    protected AutumnAuthProperties authProperties;

    /**
     * 授权服务
     */
    protected final AuthorizationServiceBase<TUser, TRole> authService;

    /**
     * 授权回调整
     */
    protected final AuthCallback authCallback;

    /**
     * 验证码应用服务
     */
    protected final ImageCaptchaAppService captchaAppService;

    /**
     * @param authService
     * @param authCallback
     * @param captchaAppService
     */
    public AbstractAuthAppServiceBase(AuthorizationServiceBase<TUser, TRole> authService,
                                      AuthCallback authCallback,
                                      ImageCaptchaAppService captchaAppService) {
        this.authService = authService;
        this.authCallback = authCallback;
        this.captchaAppService = captchaAppService;
    }

    /**
     * 获取登录次错误次数键
     *
     * @return
     */
    protected String getLoginErrorCountKey() {
        return USER_LOGIN_ERROR_COUNT + AuthUtils.checkSessionId().toLowerCase(Locale.ENGLISH);
    }

    /**
     * 登录请求
     *
     * @return
     */
    @Override
    public UserLoginRequestOutput loginRequest() {
        UserLoginRequestOutput requestOutput = new UserLoginRequestOutput();
        if (this.authProperties.isEnableImageCaptcha()) {
            String key = this.getLoginErrorCountKey();
            Integer count = redisTemplate.opsForCustomValue().get(key);
            int errorCount = this.authProperties.getImageCaptchaLoginErrorCount();
            requestOutput.setMustImageCode(errorCount <= 0 || (count != null && count >= errorCount));
            requestOutput.setErrorCount(count != null ? count : 0);
        } else {
            requestOutput.setMustImageCode(false);
            requestOutput.setErrorCount(0);
        }
        this.setAuthSessionInfo(requestOutput);
        return requestOutput;
    }

    /**
     * 用户名称与密码登录
     *
     * @param input
     */
    private void userNamePasswordLogin(UserNamePasswordLoingInput input) {
        UserNamePasswordAuthenticationToken token = new UserNamePasswordAuthenticationToken(input.getUserName(), input.getPassword().toCharArray());
        token.setRememberMe(input.isRememberMe());
        token.setCredentialsDeviceInfo(input.getCredentialsDeviceInfo());
        Subject subject = SecurityUtils.getSubject();
        //subject.logout();
        subject.login(token);
    }

    @Override
    public TUserLoginInfo login(UserNamePasswordLoingInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        if (!this.authProperties.isEnableImageCaptcha()) {
            this.userNamePasswordLogin(input);
        } else {
            int errorCount = this.authProperties.getImageCaptchaLoginErrorCount();
            String key = this.getLoginErrorCountKey();
            Integer count = redisTemplate.opsForCustomValue().get(key);
            if (count == null) {
                count = 0;
            }
            if (errorCount <= 0 || count >= errorCount) {
                count++;
                if (StringUtils.isNullOrBlank(input.getImageCode())) {
                    redisTemplate.opsForValue().set(key, count, this.authProperties.getImageCaptchaExpire(), TimeUnit.SECONDS);
                    throw AuthUtils.createCaptchaException("图形验证码不能为空");
                }
                if (captchaAppService.checkCaptcha(input.getImageCode(), true)) {
                    redisTemplate.opsForValue().set(key, count, this.authProperties.getImageCaptchaExpire(), TimeUnit.SECONDS);
                    throw AuthUtils.createCaptchaException("图形验证码不正确");
                }
            }
            try {
                this.userNamePasswordLogin(input);
                redisTemplate.delete(key);
            } catch (Exception err) {
                count++;
                redisTemplate.opsForValue().set(key, count, this.authProperties.getImageCaptchaExpire(), TimeUnit.SECONDS);
                if (count >= errorCount) {
                    throw AuthUtils.createCaptchaException("图形验证码不能为空。");
                }
                throw err;
            }
        }
        return this.loginInfo();
    }

    /**
     * 创建随机用户名称
     *
     * @return
     */
    @Override
    public String createAvailableRandomUserName() {
        String userName = authCallback.createRandomUserName();
        while (authService.existUserByUserName(userName)) {
            userName = AuthUtils.randomUserName();
        }
        return userName;
    }

    @Override
    public boolean existUserByAccount(String account) {
        ExceptionUtils.checkNotNullOrBlank(account, "account");
        return authService.existUserByAccount(account);
    }

    @Override
    public void logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        } catch (Exception err) {

        }
    }

    @Override
    public TUserLoginInfo loginInfo() {
        TUserLoginInfo loginInfo = this.createUserLoginInfo();
        //  loginInfo.setUserId(this.getSession().getUserId());
        loginInfo.setUserName(this.getSession().getUserName());
        IdentityClaims identityClaims = this.getSession().getIdentityClaims();
        if (identityClaims != null) {
            loginInfo.setNickName(identityClaims.getStringClaims(AbstractUser.USER_SESSION_CLAIM_NICK_NAME));
            // loginInfo.setRealName(identityClaims.getStringClaims(AbstractUser.USER_SESSION_CLAIM_REAL_NAME));
            // loginInfo.setSex(identityClaims.getStringClaims(AbstractUser.USER_SESSION_CLAIM_SEX));
            //  loginInfo.setBirthday(identityClaims.getDateClaims(AbstractUser.USER_SESSION_CLAIM_BIRTHDAY));
            // loginInfo.setPhoneNumber(identityClaims.getStringClaims(AbstractUser.USER_SESSION_CLAIM_PHONE_NUMBER));
            // loginInfo.setEmailAddress(identityClaims.getStringClaims(AbstractUser.USER_SESSION_CLAIM_EMAIL_ADDRESS));
            loginInfo.setHeadPortraitPath(identityClaims.getStringClaims(AbstractUser.USER_SESSION_CLAIM_HEAD_PORTRAIT_PATH));
        }
        AutumnUser user = this.getSession().getUserInfo();
        if (user != null) {
            if (user.getDeviceInfo() != null) {
                loginInfo.setDeviceToken(user.getDeviceInfo().getDeviceToken());
            }
        }
        this.setAuthSessionInfo(loginInfo);
        return loginInfo;
    }

    private void setAuthSessionInfo(AuthSessionInfo sessionInfo) {
        Session session = AuthUtils.getSession();
        Serializable sessionId = session.getId();
        if (sessionId != null) {
            sessionInfo.setSessionId(sessionId.toString());
        }
        if (webSessionManager.getSessionIdCookie() != null && !StringUtils.isNullOrBlank(webSessionManager.getSessionIdCookie().getName())) {
            sessionInfo.setSessionKey(webSessionManager.getSessionIdCookie().getName());
        } else {
            sessionInfo.setSessionKey(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        }
        sessionInfo.setSessionTimeout(session.getTimeout());
    }

    /**
     * 创建用户登录信息
     *
     * @return
     */
    protected abstract TUserLoginInfo createUserLoginInfo();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(UpdatePasswordInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        authService.updatePassword(this.getSession().getUserId(), input.getOldPassword(), input.getNewPassword());
        this.getAuditedLogger().addLog(this, "修改密码", "修改个人密码");
    }

}
