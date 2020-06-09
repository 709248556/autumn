package com.autumn.word.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.autumn.word.configure.AutumnWordConfiguration;

/**
 * 启用 AutumnWord 配置
 * 
 * @author 老码农 2019-04-24 01:11:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ AutumnWordConfiguration.class })
public @interface EnableAutumnWord {

}
