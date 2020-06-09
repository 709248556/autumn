package com.autumn.zero.authorization.configure;

import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.services.auth.WeChatAuthAppService;
import com.autumn.zero.authorization.controllers.auth.WeChatAuthController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信授权配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 18:54
 **/
@Configuration
public class AutumnZeroWeChatAuthorizationWebConfiguration {

    /**
     * 微信授权控制器
     *
     * @param service          服务
     * @param <TUserLoginInfo> 用户登录输出类型
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(WeChatAuthController.class)
    @ConditionalOnBean(value = {WeChatAuthAppService.class})
    public <TUserLoginInfo extends UserLoginInfoOutput> WeChatAuthController<TUserLoginInfo>
    autumnZeroWeChatAuthController(WeChatAuthAppService<TUserLoginInfo> service) {
        return new WeChatAuthController<>(service);
    }
}
