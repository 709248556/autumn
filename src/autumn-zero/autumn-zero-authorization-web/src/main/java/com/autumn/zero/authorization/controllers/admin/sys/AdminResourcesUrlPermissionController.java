package com.autumn.zero.authorization.controllers.admin.sys;

import com.autumn.zero.authorization.filter.AutumnZeroUrlPermissionInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后端资源url管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 23:28
 **/
@RestController
@RequestMapping("/sys/res/url")
@Api(tags = "资源url权限管理")
@RequiresUser
public class AdminResourcesUrlPermissionController {

    private final AutumnZeroUrlPermissionInterceptor permissionInterceptor;

    public AdminResourcesUrlPermissionController(AutumnZeroUrlPermissionInterceptor permissionInterceptor) {
        this.permissionInterceptor = permissionInterceptor;
    }

    /**
     * 重置拦截权限
     *
     * @return
     */
    @PostMapping("/reset/interceptor/permission")
    @ApiOperation(value = "重置拦截权限")
    public void resetInterceptorPermission() {
        permissionInterceptor.resetPermission();
    }
}
