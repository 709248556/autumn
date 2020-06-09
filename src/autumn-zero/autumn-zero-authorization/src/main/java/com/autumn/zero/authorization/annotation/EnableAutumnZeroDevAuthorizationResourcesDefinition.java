package com.autumn.zero.authorization.annotation;

import com.autumn.mybatis.annotation.AutumnMybatisScan;
import com.autumn.zero.authorization.configure.AutumnZeroDevAuthorizationResourcesDefinitionConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用基本授权自动定义
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 20:12
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@AutumnMybatisScan(value = {AutumnZeroCommonAuthorizationMybatisScan.REPOSITORY_PACKAGE_MODULES_PATH},
        typeAliasesPackages = {AutumnZeroCommonAuthorizationMybatisScan.ENTITY_PACKAGE_MODULES_PATH})
@Import({AutumnZeroDevAuthorizationResourcesDefinitionConfiguration.class})
public @interface EnableAutumnZeroDevAuthorizationResourcesDefinition {

}
