package com.autumn.file.storage.annotation;

import com.autumn.file.storage.configure.AutumnStorageAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用存储
 * 
 * @author 老码农 2019-03-11 00:45:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ AutumnStorageAutoConfiguration.class })
public @interface EnableAutumnStorage {

}
