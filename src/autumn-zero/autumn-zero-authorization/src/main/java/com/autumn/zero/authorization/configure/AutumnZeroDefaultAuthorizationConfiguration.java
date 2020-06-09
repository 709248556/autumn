package com.autumn.zero.authorization.configure;

import com.autumn.security.credential.AutumnUserCredentialsService;
import com.autumn.zero.authorization.application.services.DefaultRoleAppService;
import com.autumn.zero.authorization.application.services.DefaultUserAppService;
import com.autumn.zero.authorization.application.services.RoleAppServiceBase;
import com.autumn.zero.authorization.application.services.UserAppServiceBase;
import com.autumn.zero.authorization.application.services.auth.AuthAppServiceBase;
import com.autumn.zero.authorization.application.services.auth.DefaultAuthAppService;
import com.autumn.zero.authorization.application.services.auth.impl.DefaultAuthAppServiceImpl;
import com.autumn.zero.authorization.application.services.callback.AuthCallback;
import com.autumn.zero.authorization.application.services.captcha.ImageCaptchaAppService;
import com.autumn.zero.authorization.application.services.impl.DefaultRoleAppServiceImpl;
import com.autumn.zero.authorization.application.services.impl.DefaultUserAppServiceImpl;
import com.autumn.zero.authorization.credential.AuthUserCredentialsService;
import com.autumn.zero.authorization.credential.UserCredentialsService;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.services.DefaultAuthorizationService;
import com.autumn.zero.authorization.services.impl.DefaultAuthorizationServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 授权默认模板配置
 *
 * @author 老码农 2018-12-07 19:36:47
 */
@Configuration
public class AutumnZeroDefaultAuthorizationConfiguration {

    /**
     * 授权服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthorizationServiceBase.class)
    public DefaultAuthorizationService autumnZeroAuthorizationService() {
        return new DefaultAuthorizationServiceImpl();
    }

    /**
     * 默认身份认证
     *
     * @param authorizationService
     * @param authCallback
     * @param captchaAppService
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthAppServiceBase.class)
    @ConditionalOnBean({DefaultAuthorizationService.class, AuthCallback.class, ImageCaptchaAppService.class})
    public DefaultAuthAppService autumnZeroDefaultAuthAppService(DefaultAuthorizationService authorizationService, AuthCallback authCallback, ImageCaptchaAppService captchaAppService) {
        return new DefaultAuthAppServiceImpl(authorizationService, authCallback, captchaAppService);
    }

    /**
     * 用户默认应用服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserAppServiceBase.class)
    public DefaultUserAppService autumnZeroDefaultUserAppService() {
        return new DefaultUserAppServiceImpl();
    }

    /**
     * 角色默认应用服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RoleAppServiceBase.class)
    public DefaultRoleAppService autumnZeroDefaultRoleAppService() {
        return new DefaultRoleAppServiceImpl();
    }

    /**
     * 用户认证
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = {AuthUserCredentialsService.class, AutumnUserCredentialsService.class})
    public AuthUserCredentialsService autumnZeroUserCredentialsService() {
        return new UserCredentialsService<>(this.autumnZeroAuthorizationService());
    }
}
