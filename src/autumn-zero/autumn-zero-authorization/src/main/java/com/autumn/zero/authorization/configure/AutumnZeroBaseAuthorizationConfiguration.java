package com.autumn.zero.authorization.configure;

import com.autumn.util.json.JsonObjectDeserializerGenerator;
import com.autumn.zero.authorization.application.services.callback.AuthCallback;
import com.autumn.zero.authorization.application.services.callback.CaptchaCallback;
import com.autumn.zero.authorization.application.services.callback.impl.DefaultAuthCallback;
import com.autumn.zero.authorization.application.services.callback.impl.DefaultCaptchaCallback;
import com.autumn.zero.authorization.credential.AuthUserCredentialsService;
import com.autumn.zero.authorization.credential.realm.*;
import com.autumn.zero.authorization.deserializer.json.AuthorizationInputObjectDeserializerGenerator;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基本配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 19:03
 **/
@Configuration
public class AutumnZeroBaseAuthorizationConfiguration {

    /**
     * 默认授权回调
     *
     * @return
     */
    @Bean("autumnZoreDefaultAuthCallback")
    @ConditionalOnMissingBean(AuthCallback.class)
    public AuthCallback autumnZoreDefaultAuthCallback() {
        return new DefaultAuthCallback();
    }

    /**
     * 验证码默认回调
     *
     * @return
     */
    @Bean("autumnZeroDefaultCaptchaCallback")
    @ConditionalOnMissingBean(CaptchaCallback.class)
    public CaptchaCallback autumnZeroDefaultCaptchaCallback() {
        return new DefaultCaptchaCallback();
    }

    /**
     * 公共接口默认序列化
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthorizationInputObjectDeserializerGenerator.class)
    public JsonObjectDeserializerGenerator zeroAuthorizationInputObjectDeserializerGenerator() {
        return new AuthorizationInputObjectDeserializerGenerator();
    }

    /**
     * 用户名密码认证
     *
     * @param authorizationService 授权服务
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserNamePasswordCredentialsRealm.class)
    public UserCredentialsRealm autumnUserNamePasswordCredentialsRealm(AuthorizationServiceBase authorizationService) {
        return new UserNamePasswordCredentialsRealm(authorizationService);
    }

    /**
     * 短信认证
     *
     * @param authorizationService 授权服务
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCredentialsRealm.class)
    public UserCredentialsRealm autumnSmsCredentialsRealm(AuthorizationServiceBase authorizationService) {
        return new SmsCredentialsRealm(authorizationService);
    }

    /**
     * 第三方登录认证
     *
     * @param authorizationService 授权服务
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ExternalProviderCredentialsRealm.class)
    public UserCredentialsRealm autumnExternalProviderCredentialsRealm(AuthorizationServiceBase authorizationService) {
        return new ExternalProviderCredentialsRealm(authorizationService);
    }

    /**
     * 设备驱动认证
     *
     * @param authorizationService 授权服务
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(DeviceProviderCredentialsRealm.class)
    public UserCredentialsRealm autumnDeviceProviderCredentialsRealm(AuthorizationServiceBase authorizationService) {
        return new DeviceProviderCredentialsRealm(authorizationService);
    }

    /**
     * 拦截
     *
     * @param authCredentialsService 授权认证服务
     * @return
     */
    @Bean
    public BeanPostProcessor autumnAuthorizationCredentialsPostProcessor(AuthUserCredentialsService authCredentialsService) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof UserCredentialsRealm) {
                    UserCredentialsRealm realm = (UserCredentialsRealm) bean;
                    authCredentialsService.registerRealm(realm);
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }

}
