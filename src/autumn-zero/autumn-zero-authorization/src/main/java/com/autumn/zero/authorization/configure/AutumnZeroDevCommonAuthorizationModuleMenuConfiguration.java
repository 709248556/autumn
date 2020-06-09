package com.autumn.zero.authorization.configure;

import com.autumn.zero.authorization.plugins.AuthorizationResourcesDataSourceListener;
import com.autumn.zero.authorization.plugins.ResourcesModulePlugin;
import com.autumn.zero.authorization.plugins.impl.AuthorizationCommonModuleMenuPluginImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 授权模块菜单配置定义
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 20:28
 **/
@Configuration
public class AutumnZeroDevCommonAuthorizationModuleMenuConfiguration {

    /**
     * 授权公共模块菜单插件
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthorizationCommonModuleMenuPluginImpl.class)
    @ConditionalOnBean(AuthorizationResourcesDataSourceListener.class)
    public ResourcesModulePlugin autumnZeroAuthorizationCommonResourcesModuleMenuPlugin() {
        return new AuthorizationCommonModuleMenuPluginImpl();
    }
}
