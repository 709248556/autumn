package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 表文档
 * <p>
 * 用于生成表文档
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-01-04 11:57
 **/
@Target(TYPE)
@Retention(RUNTIME)
public @interface TableDocument {

    /**
     * 表友好名称
     *
     * @return
     */
    String value();

    /**
     * 所有的组
     *
     * @return
     */
    String group() default "";

    /**
     * 组内顺序
     * <p>
     * 根据表集合生成时有效
     * </p>
     *
     * @return
     */
    int groupOrder() default -1;

    /**
     * 生成顺序,组内的顺序
     * <p>
     * 根据表集合生成时有效
     * </p>
     *
     * @return
     */
    int tableOrder() default -1;

    /**
     * 表说明
     *
     * @return
     */
    String explain() default "";
}
