package com.autumn.mybatis.mapper.annotation;

import com.autumn.mybatis.mapper.SpecificationDefinition;
import com.autumn.mybatis.mapper.impl.StandardSpecificationDefinitionImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表规范
 * <p>
 * 特定表上指定时，作用为该表，未指定时按 {@link GlobalSpecification} 配置
 * </p>
 *
 * @author 老码农
 * <p>
 * 2017-12-04 11:35:59
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableSpecification {

    /**
     * 规范类型
     *
     * @return
     */
    Class<? extends SpecificationDefinition> specificationType() default StandardSpecificationDefinitionImpl.class;
}
