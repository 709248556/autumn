package com.autumn.zero.authorization.annotation;

import com.autumn.security.annotation.EnableAutumnSecurity;
import com.autumn.zero.authorization.configure.AutumnZeroBaseAuthorizationConfiguration;
import com.autumn.zero.authorization.configure.AutumnZeroDefaultAuthorizationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用权限默认身份认证管理模块
 *
 * @author 老码农 2018-11-24 21:25:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnSecurity
@EnableAutumnZeroCommonAuthorization
@AutumnZeroDefaultAuthorizationMybatisScan
@Import({AutumnZeroDefaultAuthorizationConfiguration.class, AutumnZeroBaseAuthorizationConfiguration.class})
public @interface EnableAutumnZeroDefaultAuthorization {

}
