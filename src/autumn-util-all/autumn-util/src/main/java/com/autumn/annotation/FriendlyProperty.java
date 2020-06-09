package com.autumn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 友好属性
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 3:18
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FriendlyProperty {

    /**
     * 属性名
     *
     * @return
     */
    String value();

    /**
     * 说明
     * @return
     */
    String explain() default "";
}
