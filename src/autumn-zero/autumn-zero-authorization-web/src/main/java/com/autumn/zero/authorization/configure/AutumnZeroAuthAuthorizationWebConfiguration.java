package com.autumn.zero.authorization.configure;

import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.services.auth.AuthAppServiceBase;
import com.autumn.zero.authorization.controllers.auth.AuthController;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基本配授权配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 18:53
 **/
@Configuration
public class AutumnZeroAuthAuthorizationWebConfiguration {

    /**
     * 基本授权制器
     *
     * @param service          服务
     * @param <TUser>          用户类型
     * @param <TUserLoginInfo> 用户登录输出类型
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthController.class)
    @ConditionalOnBean(value = {AuthAppServiceBase.class})
    public <TUser extends AbstractUser, TUserLoginInfo extends UserLoginInfoOutput> AuthController<TUser, TUserLoginInfo>
    autumnZeroAuthController(AuthAppServiceBase<TUser, TUserLoginInfo> service) {
        return new AuthController<>(service);
    }

}
