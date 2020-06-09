package com.autumn.zero.authorization.annotation;

import com.autumn.zero.authorization.configure.AutumnZeroBaseAuthorizationConfiguration;
import com.autumn.zero.authorization.configure.AutumnZeroWeChatAuthConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用微信授权
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 20:57
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroCaptchaAuthorization
@Import({AutumnZeroWeChatAuthConfiguration.class, AutumnZeroBaseAuthorizationConfiguration.class})
public @interface EnableAutumnWebChatAuth {

}
