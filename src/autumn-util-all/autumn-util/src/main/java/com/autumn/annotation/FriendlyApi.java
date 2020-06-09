package com.autumn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 友好的Api
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2019-12-31 17:21
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FriendlyApi {

    /**
     * 说明
     *
     * @return
     */
    String value();

    /**
     * 说明
     *
     * @return
     */
    String explain() default "";
}
