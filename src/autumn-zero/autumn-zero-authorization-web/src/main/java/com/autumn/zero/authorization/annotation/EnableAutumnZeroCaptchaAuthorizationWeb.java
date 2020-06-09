package com.autumn.zero.authorization.annotation;

import com.autumn.swagger.annotation.AutumnSwaggerScan;
import com.autumn.web.annotation.ApiResponseBodyScan;
import com.autumn.zero.authorization.configure.AutumnZeroCaptchaAuthorizationWebConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用验证码Web
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 01:46
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroCaptchaAuthorization
@Import({AutumnZeroCaptchaAuthorizationWebConfiguration.class})
@ApiResponseBodyScan({EnableAutumnZeroCaptchaAuthorizationWeb.PACKAGE_CAPTCHA_PATH})
@AutumnSwaggerScan(groupName = "验证码", order = 900, packages = {EnableAutumnZeroCaptchaAuthorizationWeb.PACKAGE_CAPTCHA_PATH})
public @interface EnableAutumnZeroCaptchaAuthorizationWeb {

    /**
     * 验证码包路径
     */
    public static final String PACKAGE_CAPTCHA_PATH = "com.autumn.zero.authorization.controllers.captcha";
}
