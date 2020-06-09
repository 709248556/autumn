package com.autumn.zero.authorization.configure;

import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.services.auth.SmsAuthAppService;
import com.autumn.zero.authorization.controllers.auth.SmsAuthController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
 * @create: 2020-04-02 18:53
 **/
@Configuration
public class AutumnZeroSmsAuthAuthorizationWebConfiguration {

    /**
     * 短信授权控制器
     *
     * @param service          服务
     * @param <TUserLoginInfo> 用户登录输出类型
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsAuthController.class)
    @ConditionalOnBean(value = {SmsAuthAppService.class})
    public <TUserLoginInfo extends UserLoginInfoOutput> SmsAuthController<TUserLoginInfo>
    autumnZeroSmsAuthController(SmsAuthAppService<TUserLoginInfo> service) {
        return new SmsAuthController<>(service);
    }
}
