package com.autumn.domain.values;

import java.lang.annotation.*;

/**
 * 常量字段
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-25 19:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ConstantField {

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

    /**
     * 子级字段
     *
     * @return
     */
    ConstantChildrenField[] children() default {};
}
