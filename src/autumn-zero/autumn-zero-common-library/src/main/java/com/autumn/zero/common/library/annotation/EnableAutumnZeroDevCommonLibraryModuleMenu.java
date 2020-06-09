package com.autumn.zero.common.library.annotation;

import com.autumn.zero.common.library.configure.AutumnZeroDevCommonLibraryModuleMenuConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用公共库模块菜单定义
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 02:25
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumnZeroDevCommonLibraryModuleMenuConfiguration.class})
public @interface EnableAutumnZeroDevCommonLibraryModuleMenu {

}
