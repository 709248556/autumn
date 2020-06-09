package com.autumn.zero.authorization.configure;

import com.autumn.audited.AuditedLogStorage;
import com.autumn.zero.authorization.application.services.ResourcesModuleAppService;
import com.autumn.zero.authorization.application.services.ResourcesModulePermissionAppService;
import com.autumn.zero.authorization.application.services.UserLoginLogAppService;
import com.autumn.zero.authorization.application.services.UserOperationLogAppService;
import com.autumn.zero.authorization.application.services.impl.*;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.services.impl.ResourcesServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 授权公共配置
 *
 * @author 老码农 2018-12-08 22:46:19
 */
@Configuration
public class AutumnZeroCommonAuthorizationConfiguration {

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
     * 资源模块应用服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ResourcesModuleAppService.class)
    public ResourcesModuleAppService autumnZeroResourcesModuleAppService() {
        return new ResourcesModuleAppServiceImpl();
    }

    /**
     * 资源权限应用服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ResourcesModulePermissionAppService.class)
    public ResourcesModulePermissionAppService autumnZeroResourcesPermissionModuleAppService() {
        return new ResourcesModulePermissionAppServiceImpl();
    }

    /**
     * 用户登录日志
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserLoginLogAppService.class)
    public UserLoginLogAppService autumnZeroUserLoginLogAppService() {
        return new UserLoginLogAppServiceImpl();
    }

    /**
     * 用户操作日志
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserOperationLogAppService.class)
    public UserOperationLogAppService autumnZeroUserOperationLogAppService() {
        return new UserOperationLogAppServiceImpl();
    }

    /**
     * 审计数据库存储
     *
     * @return
     */
    @Bean
    @Primary
    public AuditedLogStorage autumnDbAuditedLogStorage() {
        return new AuditedLogStorageImpl();
    }
}
