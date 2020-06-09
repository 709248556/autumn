package com.autumn.zero.authorization.annotation;

import com.autumn.redis.annotation.EnableAutumnRedis;
import com.autumn.sms.annotation.EnableAutumnSmsChannel;
import com.autumn.zero.authorization.configure.AutumnZeroBaseAuthorizationConfiguration;
import com.autumn.zero.authorization.configure.AutumnZeroCaptchaAuthorizationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用图形验证码与短信验证码授权
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 00:56
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnRedis
@EnableAutumnSmsChannel
@Import({AutumnZeroCaptchaAuthorizationConfiguration.class, AutumnZeroBaseAuthorizationConfiguration.class})
public @interface EnableAutumnZeroCaptchaAuthorization {

}
