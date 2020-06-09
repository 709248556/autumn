package com.autumn.domain.values;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 常量字段数组
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-20 17:08
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({})
@Documented
public @interface ConstantChildrenField {

    /**
     * 值
     *
     * @return
     */
    int value();

    /**
     * 名称
     *
     * @return
     */
    String name();

    /**
     * 说明
     *
     * @return
     */
    String explain() default "";

    /**
     * 顺序
     *
     * @return
     */
    int order() default 0;
}
