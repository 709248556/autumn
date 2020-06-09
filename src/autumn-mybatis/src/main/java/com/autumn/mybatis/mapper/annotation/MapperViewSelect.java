package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Mapper 查询表达式
 * <p>
 * 自定义视图的代码
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 18:06
 **/
@Target(TYPE)
@Retention(RUNTIME)
public @interface MapperViewSelect {

    /**
     * 查询 Sql代码
     *
     * @return
     */
    String value();

    /**
     * 根据不同的提供者生成不同的查询 Sql
     *
     * @return
     */
    MapperProviderViewSelect[] providerViewSelect() default {};
}
