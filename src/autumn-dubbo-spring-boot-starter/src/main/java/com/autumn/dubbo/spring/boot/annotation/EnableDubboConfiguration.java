package com.autumn.dubbo.spring.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.alibaba.boot.dubbo.autoconfigure.DubboAutoConfiguration;
import com.autumn.spring.boot.annotation.EnableAutoAutumnConfiguration;

/**
 * 启用 Dubbo 配置
 * 
 * @author 老码农
 *
 *         2018-07-03 14:07:28
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableAutoAutumnConfiguration
@Documented
@Import({ DubboAutoConfiguration.class })
public @interface EnableDubboConfiguration {

}
