package com.autumn.zero.authorization.annotation;

import com.autumn.mybatis.annotation.AutumnMybatisScan;

import java.lang.annotation.*;

/**
 * 启用权限默认实现 Mybatis 扫描
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-24 1:14
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 13:31
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@AutumnMybatisScan(value = {AutumnZeroDefaultAuthorizationMybatisScan.REPOSITORY_PACKAGE_DEFAULT_PATH}, typeAliasesPackages = {AutumnZeroDefaultAuthorizationMybatisScan.ENTITY_PACKAGE_DEFAULT_PATH})
public @interface AutumnZeroDefaultAuthorizationMybatisScan {

    /**
     * 实体默认包路径
     */
    public static final String ENTITY_PACKAGE_DEFAULT_PATH = AutumnZeroCommonAuthorizationMybatisScan.ENTITY_PACKAGE_BASE_PATH + ".defaulted";

    /**
     * 仓储默认包路径
     */
    public static final String REPOSITORY_PACKAGE_DEFAULT_PATH = AutumnZeroCommonAuthorizationMybatisScan.REPOSITORY_PACKAGE_BASE_PATH + ".defaulted";
}
