package com.autumn.zero.common.library.web.annotation;

import com.autumn.swagger.annotation.AutumnSwaggerScan;
import com.autumn.web.annotation.ApiResponseBodyScan;
import com.autumn.zero.common.library.annotation.EnableAutumnZeroCommonLibrary;
import com.autumn.zero.common.library.web.configure.AutumnZeroUserCommonLibraryWebConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用用户端公共
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 14:48
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroCommonLibrary
@Import({AutumnZeroUserCommonLibraryWebConfiguration.class})
@ApiResponseBodyScan({EnableAutumnZeroUserCommonLibraryWeb.PACKAGE_USER_COMMON_PATH})
@AutumnSwaggerScan(groupName = "公共", order = 0, packages = {EnableAutumnZeroUserCommonLibraryWeb.PACKAGE_USER_COMMON_PATH})
public @interface EnableAutumnZeroUserCommonLibraryWeb {

    /**
     * 用户端公共包路径
     */
    public static final String PACKAGE_USER_COMMON_PATH = "com.autumn.zero.common.library.web.controllers.user.common";
}
