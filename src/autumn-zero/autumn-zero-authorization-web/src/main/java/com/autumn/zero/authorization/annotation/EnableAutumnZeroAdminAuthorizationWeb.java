package com.autumn.zero.authorization.annotation;

import com.autumn.swagger.annotation.AutumnSwaggerScan;
import com.autumn.web.annotation.ApiResponseBodyScan;
import com.autumn.zero.authorization.configure.AutumnZeroAdminAuthorizationWebConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用管理端
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 22:51
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroCommonAuthorization
@Import({AutumnZeroAdminAuthorizationWebConfiguration.class})
@ApiResponseBodyScan({EnableAutumnZeroAdminAuthorizationWeb.PACKAGE_ADMIN_SYS_PATH})
@AutumnSwaggerScan(groupName = "系统", order = 1000, packages = {EnableAutumnZeroAdminAuthorizationWeb.PACKAGE_ADMIN_SYS_PATH})
public @interface EnableAutumnZeroAdminAuthorizationWeb {

    /**
     * 管理端系统包路径
     */
    public static final String PACKAGE_ADMIN_SYS_PATH = "com.autumn.zero.authorization.controllers.admin.sys";

}
