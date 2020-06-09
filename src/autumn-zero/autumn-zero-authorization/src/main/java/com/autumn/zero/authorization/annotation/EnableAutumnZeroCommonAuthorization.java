package com.autumn.zero.authorization.annotation;

import com.autumn.zero.authorization.configure.AutumnZeroBaseAuthorizationConfiguration;
import com.autumn.zero.authorization.configure.AutumnZeroCommonAuthorizationConfiguration;
import com.autumn.zero.file.storage.annotation.EnableAutumnZeroFileStorage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用基本授权
 *
 * @author 老码农
 * 2018-12-08 22:45:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroFileStorage
@AutumnZeroCommonAuthorizationMybatisScan
@Import({AutumnZeroCommonAuthorizationConfiguration.class, AutumnZeroBaseAuthorizationConfiguration.class})
public @interface EnableAutumnZeroCommonAuthorization {

}
