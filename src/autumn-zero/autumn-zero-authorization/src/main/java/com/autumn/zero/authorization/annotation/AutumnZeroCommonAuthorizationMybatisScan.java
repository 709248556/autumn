package com.autumn.zero.authorization.annotation;

import com.autumn.mybatis.annotation.AutumnMybatisScan;

import java.lang.annotation.*;

/**
 * 启用权限公共 Mybatis 扫描
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-24 1:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@AutumnMybatisScan(value = {AutumnZeroCommonAuthorizationMybatisScan.REPOSITORY_PACKAGE_COMMON_PATH}, typeAliasesPackages = {AutumnZeroCommonAuthorizationMybatisScan.ENTITY_PACKAGE_COMMON_PATH})
public @interface AutumnZeroCommonAuthorizationMybatisScan {

    /**
     * 实体包基础路径
     */
    public static final String ENTITY_PACKAGE_BASE_PATH = "com.autumn.zero.authorization.entities";

    /**
     * 实体公共包路径
     */
    public static final String ENTITY_PACKAGE_COMMON_PATH = ENTITY_PACKAGE_BASE_PATH + ".common";

    /**
     * 仓储包基础路径
     */
    public static final String REPOSITORY_PACKAGE_BASE_PATH = "com.autumn.zero.authorization.repositories";

    /**
     * 仓储公共包路径
     */
    public static final String REPOSITORY_PACKAGE_COMMON_PATH = REPOSITORY_PACKAGE_BASE_PATH + ".common";

    /**
     * 实体模块包路径
     */
    public static final String ENTITY_PACKAGE_MODULES_PATH = ENTITY_PACKAGE_COMMON_PATH + ".modules";

    /**
     * 仓储模块包路径
     */
    public static final String REPOSITORY_PACKAGE_MODULES_PATH = REPOSITORY_PACKAGE_COMMON_PATH + ".modules";
}
