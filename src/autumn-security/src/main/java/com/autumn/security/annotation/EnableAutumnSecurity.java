package com.autumn.security.annotation;

import com.autumn.security.configure.*;
import com.autumn.security.filter.AutumnPermissionFilter;
import com.autumn.security.filter.DefaultAutumnPermissionFilter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 Autumn 安全
 *
 * @author 老码农 2018-12-06 14:00:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumnPermissionFilterRegister.class,  AutumnSecurityConfiguration.class})
public @interface EnableAutumnSecurity {

    /**
     * 权限类型属性名称
     */
    public static final String PERMISSION_FILTER_TYPE_ATTRIBUTE_NAME = "permissionFilterType";

    /**
     * 权限过滤类型
     *
     * @return
     */
    Class<? extends AutumnPermissionFilter> permissionFilterType() default DefaultAutumnPermissionFilter.class;

}
