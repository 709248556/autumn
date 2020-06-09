package com.autumn.cloud.annotation;

import com.autumn.cloud.configure.AutumnLockConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 Autumn 分布式锁
 * 
 * @author 老码农
 *
 *         2017-11-23 16:45:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ AutumnLockConfiguration.class })
public @interface EnableAutumnCloudLock {

}
