package com.autumn.zero.authorization.annotation;

import com.autumn.swagger.annotation.AutumnSwaggerScan;
import com.autumn.web.annotation.ApiResponseBodyScan;
import com.autumn.zero.authorization.configure.AutumnZeroWeChatAuthorizationWebConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用微信授权配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 19:03
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnWebChatAuth
@Import({AutumnZeroWeChatAuthorizationWebConfiguration.class})
@ApiResponseBodyScan({EnableAutumnZeroAuthAuthorizationWeb.PACKAGE_AUTH_PATH})
@AutumnSwaggerScan(groupName = "身份认证", order = 1, packages = {EnableAutumnZeroAuthAuthorizationWeb.PACKAGE_AUTH_PATH})
public @interface EnableAutumnZeroWeChatAuthorizationWeb {
}
