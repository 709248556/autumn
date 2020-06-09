package com.autumn.spring.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.autumn.spring.boot.AutumnAutoConfiguration;

/**
 * 启用 Autumn 配置
 * 
 * @author 老码农
 *
 *         2018-06-26 17:33:14
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ AutumnAutoConfiguration.class })
public @interface EnableAutoAutumnConfiguration {

}
