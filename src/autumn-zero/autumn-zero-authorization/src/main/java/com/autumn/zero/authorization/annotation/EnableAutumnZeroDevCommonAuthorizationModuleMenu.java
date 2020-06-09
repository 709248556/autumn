package com.autumn.zero.authorization.annotation;

import com.autumn.zero.authorization.configure.AutumnZeroDevCommonAuthorizationModuleMenuConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用基本授权模块菜单定义
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 20:32
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumnZeroDevCommonAuthorizationModuleMenuConfiguration.class})
public @interface EnableAutumnZeroDevCommonAuthorizationModuleMenu {

}
