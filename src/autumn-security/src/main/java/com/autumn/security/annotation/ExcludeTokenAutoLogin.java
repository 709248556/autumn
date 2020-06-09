package com.autumn.security.annotation;

import java.lang.annotation.*;

/**
 * 排除票据自动登录
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 05:29
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ExcludeTokenAutoLogin {

}
