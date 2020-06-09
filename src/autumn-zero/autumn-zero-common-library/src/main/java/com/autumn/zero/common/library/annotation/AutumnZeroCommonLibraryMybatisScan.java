package com.autumn.zero.common.library.annotation;

import com.autumn.mybatis.annotation.AutumnMybatisScan;

import java.lang.annotation.*;

/**
 * 公共运行库扫描
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 3:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@AutumnMybatisScan(
        value = {AutumnZeroCommonLibraryMybatisScan.REPOSITORY_PACKAGE_COMMON_PATH,
                AutumnZeroCommonLibraryMybatisScan.REPOSITORY_PACKAGE_SYS_PATH},
        typeAliasesPackages = {
                AutumnZeroCommonLibraryMybatisScan.ENTITY_PACKAGE_COMMON_PATH,
                AutumnZeroCommonLibraryMybatisScan.ENTITY_PACKAGE_SYS_PATH})
@Documented
public @interface AutumnZeroCommonLibraryMybatisScan {

    /**
     * 实体基础包路径
     */
    public static final String ENTITY_PACKAGE_BASE_PATH = "com.autumn.zero.common.library.entities";

    /**
     * 实体公共包路径
     */
    public static final String ENTITY_PACKAGE_COMMON_PATH = ENTITY_PACKAGE_BASE_PATH + ".common";

    /**
     * 实体系统包路径
     */
    public static final String ENTITY_PACKAGE_SYS_PATH = ENTITY_PACKAGE_BASE_PATH + ".sys";

    /**
     * 仓储基础包路径
     */
    public static final String REPOSITORY_PACKAGE_BASE_PATH = "com.autumn.zero.common.library.repositories";

    /**
     * 仓储共包路径
     */
    public static final String REPOSITORY_PACKAGE_COMMON_PATH = REPOSITORY_PACKAGE_BASE_PATH + ".common";

    /**
     * 仓储系统包路径
     */
    public static final String REPOSITORY_PACKAGE_SYS_PATH = REPOSITORY_PACKAGE_BASE_PATH + ".sys";
}
