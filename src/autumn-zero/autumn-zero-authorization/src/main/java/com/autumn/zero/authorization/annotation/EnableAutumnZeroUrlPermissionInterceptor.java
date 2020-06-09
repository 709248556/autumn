package com.autumn.zero.authorization.annotation;

import com.autumn.zero.authorization.configure.AutumnZeroBaseAuthorizationConfiguration;
import com.autumn.zero.authorization.configure.AutumnZeroUrlPermissionInterceptorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用Url权限拦截
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 14:07
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroCommonAuthorization
@Import({AutumnZeroUrlPermissionInterceptorConfiguration.class, AutumnZeroBaseAuthorizationConfiguration.class})
public @interface EnableAutumnZeroUrlPermissionInterceptor {

}
