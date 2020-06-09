package com.autumn.zero.file.storage.annotation;

import com.autumn.mybatis.annotation.AutumnMybatisScan;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 3:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@AutumnMybatisScan(value = {AutumnZeroFileStorageMybatisScan.REPOSITORY_PACKAGE_PATH}, typeAliasesPackages = {AutumnZeroFileStorageMybatisScan.ENTITY_PACKAGE_PATH})
public @interface AutumnZeroFileStorageMybatisScan {

    /**
     * 实体包路径
     */
    public static final String ENTITY_PACKAGE_PATH = "com.autumn.zero.file.storage.entities";

    /**
     * 仓储包路径
     */
    public static final String REPOSITORY_PACKAGE_PATH = "com.autumn.zero.file.storage.repositories";
}
