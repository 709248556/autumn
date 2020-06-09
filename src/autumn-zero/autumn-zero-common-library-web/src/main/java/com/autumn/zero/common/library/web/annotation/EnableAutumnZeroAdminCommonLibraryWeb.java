package com.autumn.zero.common.library.web.annotation;

import com.autumn.swagger.annotation.AutumnSwaggerScan;
import com.autumn.web.annotation.ApiResponseBodyScan;
import com.autumn.zero.common.library.annotation.EnableAutumnZeroCommonLibrary;
import com.autumn.zero.common.library.web.configure.AutumnZeroAdminCommonLibraryWebConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用公共管理端
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 16:58
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroCommonLibrary
@Import({AutumnZeroAdminCommonLibraryWebConfiguration.class})
@ApiResponseBodyScan({EnableAutumnZeroAdminCommonLibraryWeb.PACKAGE_ADMIN_COMMON_PATH, EnableAutumnZeroAdminCommonLibraryWeb.PACKAGE_ADMIN_SYS_PATH})
@AutumnSwaggerScan(groupName = "公共", order = 0, packages = {EnableAutumnZeroAdminCommonLibraryWeb.PACKAGE_ADMIN_COMMON_PATH})
@AutumnSwaggerScan(groupName = "系统", order = 1000, packages = {EnableAutumnZeroAdminCommonLibraryWeb.PACKAGE_ADMIN_SYS_PATH})
public @interface EnableAutumnZeroAdminCommonLibraryWeb {

    /**
     * 管理端公共包路径
     */
    public static final String PACKAGE_ADMIN_COMMON_PATH = "com.autumn.zero.common.library.web.controllers.admin.common";

    /**
     * 管理端系统包路径
     */
    public static final String PACKAGE_ADMIN_SYS_PATH = "com.autumn.zero.common.library.web.controllers.admin.sys";

}
