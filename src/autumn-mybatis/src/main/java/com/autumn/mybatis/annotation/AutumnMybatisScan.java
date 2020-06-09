package com.autumn.mybatis.annotation;

import java.lang.annotation.*;

/**
 * Mybatis 扫描
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-24 0:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutumnMybatisScan {

    /**
     * MyBatis mapper Interface 的包集合
     *
     * @return
     */
    String[] value();

    /**
     * 实体(POJO)类型(与数据映射的实体)包路径
     *
     * @return
     */
    String[] typeAliasesPackages();

    /**
     * 名称(对应数据源名称或标识,多数据源时指定)
     *
     * @return
     */
    String name() default "";
}
