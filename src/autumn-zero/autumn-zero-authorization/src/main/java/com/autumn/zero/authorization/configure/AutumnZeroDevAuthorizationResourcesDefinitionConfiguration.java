package com.autumn.zero.authorization.configure;

import com.autumn.mybatis.event.TableAutoDefinitionListener;
import com.autumn.zero.authorization.plugins.AuthorizationResourcesDataSourceListener;
import com.autumn.zero.authorization.plugins.ResourcesModulePlugin;
import com.autumn.zero.authorization.plugins.ResourcesModulePluginContext;
import com.autumn.zero.authorization.plugins.impl.AuthorizationResourcesDefinitionDataSourceListenerImpl;
import com.autumn.zero.authorization.plugins.impl.ResourcesModulePluginContextImpl;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.services.impl.ResourcesServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 授权资源数所源定义配置
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 20:10
 **/
@Configuration
public class AutumnZeroDevAuthorizationResourcesDefinitionConfiguration {

    /**
     * 插件上下文
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ResourcesModulePluginContext.class)
    public ResourcesModulePluginContext autumnZeroResourcesModulePluginContext() {
        return new ResourcesModulePluginContextImpl();
    }

    /**
     * 授权资源数所源监听
     *
     * @param resourcesModulePluginContext 插件上下文
     * @return
     */
    @Bean
    @Order(TableAutoDefinitionListener.BEAN_BEGIN_ORDER + 20)
    @ConditionalOnMissingBean(AuthorizationResourcesDataSourceListener.class)
    public AuthorizationResourcesDataSourceListener authorizationResourcesDataSourceListener(ResourcesModulePluginContext resourcesModulePluginContext) {
        return new AuthorizationResourcesDefinitionDataSourceListenerImpl(resourcesModulePluginContext);
    }

    /**
     * 资源服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ResourcesService.class)
    public ResourcesService autumnZeroResourcesService() {
        return new ResourcesServiceImpl();
    }

    /**
     * 拦截
     *
     * @param dataSourceListener
     * @return
     */
    @Bean
    public BeanPostProcessor autumnAuthorizationResourcesDataSourceListenerBeanPostProcessor(ResourcesModulePluginContext resourcesModulePluginContext) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof ResourcesModulePlugin) {
                    ResourcesModulePlugin plugin = (ResourcesModulePlugin) bean;
                    resourcesModulePluginContext.registerPlugin(plugin);
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
