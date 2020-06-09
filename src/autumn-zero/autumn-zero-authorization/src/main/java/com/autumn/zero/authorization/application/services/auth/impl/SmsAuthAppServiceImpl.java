package com.autumn.zero.authorization.application.services.auth.impl;

import com.autumn.application.service.AbstractApplicationService;
import com.autumn.exception.ExceptionUtils;
import com.autumn.security.AutumnNotLoginException;
import com.autumn.security.constants.UserStatusConstants;
import com.autumn.security.token.SmsAutumnAuthenticationToken;
import com.autumn.util.StringUtils;
import com.autumn.zero.authorization.application.dto.auth.SmsCaptchaLoginInput;
import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.dto.auth.UserSmsRegisterInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaCheckInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaPasswordInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaSendStatus;
import com.autumn.zero.authorization.application.services.auth.AuthAppServiceBase;
import com.autumn.zero.authorization.application.services.auth.SmsAuthAppService;
import com.autumn.zero.authorization.application.services.callback.CaptchaCallback;
import com.autumn.zero.authorization.application.services.captcha.SmsCaptchaAppService;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.utils.AuthUtils;
import com.google.common.collect.Lists;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 短信授权
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 18:48
 **/
public class SmsAuthAppServiceImpl<TUser extends AbstractUser, TRole extends AbstractRole, TUserLoginInfo extends UserLoginInfoOutput>
        extends AbstractApplicationService implements SmsAuthAppService<TUserLoginInfo> {

    private final AuthorizationServiceBase<TUser, TRole> authService;
    private final AuthAppServiceBase<TUser, TUserLoginInfo> authAppService;
    private final SmsCaptchaAppService smsCaptchaAppService;
    private final CaptchaCallback captchaCallback;

    /**
     *
     */
    public SmsAuthAppServiceImpl(AuthorizationServiceBase<TUser, TRole> authService,
                                 AuthAppServiceBase<TUser, TUserLoginInfo> authAppService,
                                 SmsCaptchaAppService smsCaptchaAppService,
                                 CaptchaCallback captchaCallback) {
        this.authService = authService;
        this.authAppService = authAppService;
        this.smsCaptchaAppService = smsCaptchaAppService;
        this.captchaCallback = captchaCallback;
    }

    @Override
    public String getModuleName() {
        return "短信授权";
    }

    @Override
    public TUserLoginInfo loginBySmsToken(SmsCaptchaLoginInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        SmsCaptchaSendStatus status = smsCaptchaAppService.checkSendStatus(input, true);
        if (status == null) {
            throw AuthUtils.createAccountCredentialsException("无效的短信验证码。");
        }
        if (!captchaCallback.isUserLoginSmsSend(status.getSendType())) {
            throw AuthUtils.createAccountCredentialsException("指定的验证码不是登录验证码。");
        }
        SmsAutumnAuthenticationToken token = new SmsAutumnAuthenticationToken(status.getMobilePhone(), status.getToken(), status.getSmsCode());
        token.setCredentialsDeviceInfo(input.getCredentialsDeviceInfo());
        Subject subject = SecurityUtils.getSubject();
        //subject.logout();
        subject.login(token);
        return this.authAppService.loginInfo();
    }

    @Override
    public void updatePasswordBySmsToken(SmsCaptchaPasswordInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        SmsCaptchaSendStatus status = smsCaptchaAppService.checkSendStatus(input, true);
        if (status == null) {
            throw AuthUtils.createAccountCredentialsException("无效的短信验证码。");
        }
        TUser user = this.authService.findUserByPhoneNumber(status.getMobilePhone(), false);
        if (user == null) {
            throw AuthUtils.createAccountNotFoundException("用户未注册或不存在。");
        }
        this.authService.resetPassword(user.getId(), input.getPassword());
        this.getAuditedLogger().addLog(this, "修改密码", "通过短信修改个人密码");
    }


    @Override
    public void bindingMobilePhone(SmsCaptchaCheckInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        Long userId = getSession().getUserId();
        if (userId == null) {
            throw new AutumnNotLoginException();
        }
        SmsCaptchaSendStatus status = smsCaptchaAppService.checkSendStatus(input, true);
        if (status == null) {
            throw AuthUtils.createAccountCredentialsException("无效的短信验证码。");
        }
        this.authService.updatePhoneNumber(userId, status.getMobilePhone(), true);
        this.authService.activePhoneById(userId);
        this.getAuditedLogger().addLog(this, "绑定手机", "手机号[" + status.getMobilePhone() + "]");
    }

    @Override
    public TUserLoginInfo userRegisterBySms(UserSmsRegisterInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        SmsCaptchaSendStatus status = smsCaptchaAppService.checkSendStatus(input, true);
        if (status == null) {
            throw AuthUtils.createAccountCredentialsException("无效的短信验证码。");
        }
        if (!captchaCallback.isUserRegisterSmsSend(status.getSendType())) {
            throw AuthUtils.createAccountCredentialsException("指定的验证码不是注册证码。");
        }
        TUser user = this.authService.findUserByPhoneNumber(status.getMobilePhone(), false);
        if (user != null) {
            throw AuthUtils.createAccountCredentialsException("指定的手机号已注册，不能重复注册。");
        }
        user = this.authAppService.createRegisterUser();
        if (StringUtils.isNullOrBlank(user.getUserName())) {
            user.setUserName(this.authAppService.createAvailableRandomUserName());
        }
        user.setIsSysUser(false);
        user.setStatus(UserStatusConstants.NORMAL);
        user.setPhoneNumber(status.getMobilePhone());
        user.setPassword(input.getPassword());
        user.setRoles(Lists.newArrayList());
        user.forNullToDefault();
        this.authService.addUser(user, false);
        if (!user.getIsActivatePhone()) {
            this.authService.activePhone(user);
        }
        this.getAuditedLogger().addLog(this, "短信注册", user);
        if (input.isLogin()) {
            SmsAutumnAuthenticationToken token = new SmsAutumnAuthenticationToken(status.getMobilePhone(), status.getToken(), status.getSmsCode());
            token.setCredentialsDeviceInfo(input.getCredentialsDeviceInfo());
            Subject subject = SecurityUtils.getSubject();
            //subject.logout();
            subject.login(token);
            return this.authAppService.loginInfo();
        }
        return null;
    }


}
