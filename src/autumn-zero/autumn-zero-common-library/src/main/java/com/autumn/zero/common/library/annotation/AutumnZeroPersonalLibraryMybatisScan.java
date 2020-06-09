package com.autumn.zero.common.library.annotation;

import com.autumn.mybatis.annotation.AutumnMybatisScan;

import java.lang.annotation.*;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 21:05
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@AutumnMybatisScan(value = {AutumnZeroPersonalLibraryMybatisScan.REPOSITORY_PACKAGE_PATH},
        typeAliasesPackages = {AutumnZeroPersonalLibraryMybatisScan.ENTITY_PACKAGE_PATH})
@Documented
public @interface AutumnZeroPersonalLibraryMybatisScan {

    /**
     * 实体包路径
     */
    public static final String ENTITY_PACKAGE_PATH = AutumnZeroCommonLibraryMybatisScan.ENTITY_PACKAGE_BASE_PATH + ".personal";

    /**
     * 仓储包路径
     */
    public static final String REPOSITORY_PACKAGE_PATH = AutumnZeroCommonLibraryMybatisScan.REPOSITORY_PACKAGE_BASE_PATH + ".personal";
}
