package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 列文档名称
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-01-04 12:03
 **/
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RUNTIME)
public @interface ColumnDocument {

    /**
     * 列友好名称
     *
     * @return
     */
    String value();

    /**
     * 列说明
     *
     * @return
     */
    String explain() default "";
}
