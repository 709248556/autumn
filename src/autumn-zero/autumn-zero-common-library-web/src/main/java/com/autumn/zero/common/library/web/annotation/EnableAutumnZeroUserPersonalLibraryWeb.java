package com.autumn.zero.common.library.web.annotation;

import com.autumn.swagger.annotation.AutumnSwaggerScan;
import com.autumn.web.annotation.ApiResponseBodyScan;
import com.autumn.zero.common.library.annotation.EnableAutumnZeroPersonalLibrary;
import com.autumn.zero.common.library.web.configure.AutumnZeroUserPersonalLibraryWebConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用用户个人
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 13:31
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroPersonalLibrary
@Import({AutumnZeroUserPersonalLibraryWebConfiguration.class})
@ApiResponseBodyScan({EnableAutumnZeroUserPersonalLibraryWeb.PACKAGE_USER_PERSONAL_PATH})
@AutumnSwaggerScan(groupName = "个人中心", order = 2000, packages = {EnableAutumnZeroUserPersonalLibraryWeb.PACKAGE_USER_PERSONAL_PATH})
public @interface EnableAutumnZeroUserPersonalLibraryWeb {

    /**
     * 用户个人包路径
     */
    public static final String PACKAGE_USER_PERSONAL_PATH = "com.autumn.zero.common.library.web.controllers.user.personal";
}
