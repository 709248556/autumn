package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表排序
 *
 * @author 老码农
 * <p>
 * 2017-10-18 21:49:55
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableOrderBy {

    /*
     * 排序值
     */
    String value() default "";
}
