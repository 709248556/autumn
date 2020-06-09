package com.autumn.zero.authorization.configure;

import com.autumn.web.handlers.UrlRequestMappingInfoHandler;
import com.autumn.zero.authorization.application.dto.roles.RoleDto;
import com.autumn.zero.authorization.application.dto.roles.RoleInput;
import com.autumn.zero.authorization.application.dto.roles.RoleOutput;
import com.autumn.zero.authorization.application.dto.users.UserAddInput;
import com.autumn.zero.authorization.application.dto.users.UserDetailsOutput;
import com.autumn.zero.authorization.application.dto.users.UserInput;
import com.autumn.zero.authorization.application.dto.users.UserOutput;
import com.autumn.zero.authorization.application.services.*;
import com.autumn.zero.authorization.application.services.callback.AuthCallback;
import com.autumn.zero.authorization.controllers.admin.sys.*;
import com.autumn.zero.authorization.filter.AutumnZeroUrlPermissionInterceptor;
import com.autumn.zero.authorization.services.ResourcesService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 授权管理端Web配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 22:55
 **/
@Configuration
public class AutumnZeroAdminAuthorizationWebConfiguration {

    /**
     * 资源模块管理端
     *
     * @param service
     * @param resourcesService
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminResourcesModuleController.class)
    @ConditionalOnBean(value = {ResourcesModuleAppService.class, ResourcesService.class})
    public AdminResourcesModuleController autumnZeroAdminResourcesModuleController(ResourcesModuleAppService service, ResourcesService resourcesService) {
        return new AdminResourcesModuleController(service, resourcesService);
    }

    /**
     * 资源模块权限管理端
     *
     * @param service
     * @param resourcesService
     */
    @Bean
    @ConditionalOnMissingBean(AdminResourcesModulePermissionController.class)
    @ConditionalOnBean(value = {ResourcesModulePermissionAppService.class, ResourcesService.class})
    public AdminResourcesModulePermissionController autumnZeroAdminResourcesModulePermissionController(ResourcesModulePermissionAppService service, ResourcesService resourcesService) {
        return new AdminResourcesModulePermissionController(service, resourcesService);
    }

    /**
     * 资源Url管理
     *
     * @param resourcesService          资源服务
     * @param requestMappingInfoHandler 请求处理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminResourcesUrlController.class)
    @ConditionalOnBean(value = {ResourcesService.class})
    public AdminResourcesUrlController autumnZeroAdminResourcesUrlController(ResourcesService resourcesService, UrlRequestMappingInfoHandler requestMappingInfoHandler) {
        return new AdminResourcesUrlController(resourcesService, requestMappingInfoHandler);
    }

    /**
     * 资源Url权限管理
     *
     * @param permissionInterceptor 权限拦截器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminResourcesUrlPermissionController.class)
    @ConditionalOnBean(value = {AutumnZeroUrlPermissionInterceptor.class})
    public AdminResourcesUrlPermissionController autumnZeroAdminResourcesUrlPermissionController(AutumnZeroUrlPermissionInterceptor permissionInterceptor) {
        return new AdminResourcesUrlPermissionController(permissionInterceptor);
    }

    /**
     * 登录日志管理端
     *
     * @param service
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminUserLoginLogController.class)
    @ConditionalOnBean(value = {UserLoginLogAppService.class})
    public AdminUserLoginLogController autumnZeroAdminUserLoginLogAuthController(UserLoginLogAppService service) {
        return new AdminUserLoginLogController(service);
    }

    /**
     * 操作日志管理端
     *
     * @param service
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminUserOperationLogController.class)
    @ConditionalOnBean(value = {UserOperationLogAppService.class})
    public AdminUserOperationLogController autumnZeroAdminUserOperationLogController(UserOperationLogAppService service) {
        return new AdminUserOperationLogController(service);
    }

    /**
     * 角色管理端
     *
     * @param service
     * @param authCallback
     * @param <TAddInput>
     * @param <TUpdateInput>
     * @param <TOutputItem>
     * @param <TOutputDetails>
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminRoleManagerController.class)
    @ConditionalOnBean(value = {RoleAppServiceBase.class, AuthCallback.class})
    public <TAddInput extends RoleInput, TUpdateInput extends RoleInput, TOutputItem extends RoleDto, TOutputDetails extends RoleOutput>
    AdminRoleManagerController<TAddInput, TUpdateInput, TOutputItem, TOutputDetails>
    autumnZeroAdminRoleManagerController(RoleAppServiceBase<TAddInput, TUpdateInput, TOutputItem, TOutputDetails> service,
                                         AuthCallback authCallback) {
        return new AdminRoleManagerController(service, authCallback);
    }

    /**
     * 用户管理端
     *
     * @param service
     * @param authCallback
     * @param <TAddInput>
     * @param <TUpdateInput>
     * @param <TOutputItem>
     * @param <TOutputDetails>
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminUserManagerController.class)
    @ConditionalOnBean(value = {UserAppServiceBase.class, AuthCallback.class})
    public <TAddInput extends UserAddInput, TUpdateInput extends UserInput, TOutputItem extends UserOutput, TOutputDetails extends UserDetailsOutput>
    AdminUserManagerController<TAddInput, TUpdateInput, TOutputItem, TOutputDetails>
    autumnZeroAdminUserManagerController(UserAppServiceBase<TAddInput, TUpdateInput, TOutputItem, TOutputDetails> service,
                                         AuthCallback authCallback) {
        return new AdminUserManagerController(service, authCallback);
    }

}
