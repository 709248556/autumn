package com.autumn.zero.authorization.configure;

import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.services.auth.AuthAppServiceBase;
import com.autumn.zero.authorization.application.services.auth.SmsAuthAppService;
import com.autumn.zero.authorization.application.services.auth.impl.SmsAuthAppServiceImpl;
import com.autumn.zero.authorization.application.services.callback.CaptchaCallback;
import com.autumn.zero.authorization.application.services.captcha.SmsCaptchaAppService;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信授权配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 20:52
 **/
@Configuration
public class AutumnZeroSmsAuthConfiguration {

    /**
     * 短信授权应用服务
     *
     * @param <TUserLoginInfo>
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsAuthAppService.class)
    // @ConditionalOnBean({AuthorizationServiceBase.class, AuthAppServiceBase.class, SmsCaptchaAppService.class, CaptchaCallback.class})
    public <TUser extends AbstractUser, TRole extends AbstractRole, TUserLoginInfo extends UserLoginInfoOutput> SmsAuthAppService<TUserLoginInfo>
    autumnZoreSmsAuthAppService(AuthorizationServiceBase<TUser, TRole> authService, AuthAppServiceBase<TUser, TUserLoginInfo> authAppService,
                                SmsCaptchaAppService smsCaptchaAppService, CaptchaCallback captchaCallback) {
        return new SmsAuthAppServiceImpl(authService, authAppService, smsCaptchaAppService, captchaCallback);
    }

}
