package com.autumn.zero.authorization.configure;

import com.autumn.runtime.session.AutumnSession;
import com.autumn.zero.authorization.filter.AutumnZeroUrlPermissionInterceptor;
import com.autumn.zero.authorization.services.ResourcesService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 14:08
 **/
@Configuration
@EnableConfigurationProperties({ServerProperties.class})
public class AutumnZeroUrlPermissionInterceptorConfiguration {

    /**
     * 权限拦截
     *
     * @param session          会话
     * @param resourcesService 资源
     * @param serverProperties 服务器属性
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnZeroUrlPermissionInterceptor.class)
    @ConditionalOnBean({AutumnSession.class, ResourcesService.class})
    public AutumnZeroUrlPermissionInterceptor zeroAutumnPermissionInterceptor(AutumnSession session,
                                                                              ResourcesService resourcesService,
                                                                              ServerProperties serverProperties) {
        return new AutumnZeroUrlPermissionInterceptor(session, resourcesService, serverProperties);
    }
}
