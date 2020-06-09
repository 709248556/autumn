package com.autumn.zero.authorization.annotation;

import com.autumn.zero.authorization.configure.AutumnZeroBaseAuthorizationConfiguration;
import com.autumn.zero.authorization.configure.AutumnZeroSmsAuthConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用短信授权
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 20:55
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroCaptchaAuthorization
@Import({AutumnZeroSmsAuthConfiguration.class, AutumnZeroBaseAuthorizationConfiguration.class})
public @interface EnableAutumnSmsAuth {

}
