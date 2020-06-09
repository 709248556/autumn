package com.autumn.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.autumn.redis.configurre.AutumnRedisConfiguration;

/**
 * 启用 AutumnRedis
 * 
 * @author 老码农
 *
 *         2018-01-14 15:08:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ AutumnRedisConfiguration.class })
public @interface EnableAutumnRedis {

}
