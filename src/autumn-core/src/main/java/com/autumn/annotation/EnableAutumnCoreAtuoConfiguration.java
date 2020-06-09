package com.autumn.annotation;

import com.autumn.configure.AutumnCoreAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用核心自动配置
 * 
 * @author 老码农 2018-12-07 01:41:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ AutumnCoreAutoConfiguration.class })
public @interface EnableAutumnCoreAtuoConfiguration {

}
