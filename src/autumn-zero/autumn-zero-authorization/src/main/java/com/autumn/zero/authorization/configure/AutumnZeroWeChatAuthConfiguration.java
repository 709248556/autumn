package com.autumn.zero.authorization.configure;

import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.services.auth.AuthAppServiceBase;
import com.autumn.zero.authorization.application.services.auth.WeChatAuthAppService;
import com.autumn.zero.authorization.application.services.auth.impl.WeChatAuthAppServiceImpl;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.properties.WeChatAuthProperties;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信授权配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 20:53
 **/
@Configuration
@EnableConfigurationProperties({WeChatAuthProperties.class})
public class AutumnZeroWeChatAuthConfiguration {

    /**
     * 微信授权应用服务
     *
     * @param <TUserLoginInfo>
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(WeChatAuthAppService.class)
    // @ConditionalOnBean({AuthorizationServiceBase.class, AuthAppServiceBase.class, WeChatAuthProperties.class})
    public <TUser extends AbstractUser, TRole extends AbstractRole, TUserLoginInfo extends UserLoginInfoOutput> WeChatAuthAppService<TUserLoginInfo>
    autumnZoreWeChatAuthAppService(AuthorizationServiceBase<TUser, TRole> authService, AuthAppServiceBase<TUser, TUserLoginInfo> authAppService, WeChatAuthProperties weChatAuthProperties) {
        return new WeChatAuthAppServiceImpl(authService, authAppService, weChatAuthProperties);
    }

}
