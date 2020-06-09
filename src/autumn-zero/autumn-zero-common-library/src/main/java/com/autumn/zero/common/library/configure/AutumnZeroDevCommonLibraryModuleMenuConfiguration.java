package com.autumn.zero.common.library.configure;

import com.autumn.mybatis.event.TableAutoDefinitionListener;
import com.autumn.zero.authorization.plugins.AuthorizationResourcesDataSourceListener;
import com.autumn.zero.authorization.plugins.ResourcesModulePlugin;
import com.autumn.zero.common.library.plugins.CommonLibrayModuleMenuPluginImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 公共库模块菜单定义
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 02:25
 **/
@Configuration
public class AutumnZeroDevCommonLibraryModuleMenuConfiguration {

    /**
     * 公共库模块菜单插件
     *
     * @return
     */
    @Bean
    @Order(TableAutoDefinitionListener.BEAN_BEGIN_ORDER + 30)
    @ConditionalOnMissingBean(CommonLibrayModuleMenuPluginImpl.class)
    @ConditionalOnBean(AuthorizationResourcesDataSourceListener.class)
    public ResourcesModulePlugin autumnZeroCommonLibrayResourcesModuleMenuPlugin() {
        return new CommonLibrayModuleMenuPluginImpl();
    }
}
