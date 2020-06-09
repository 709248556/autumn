package com.autumn.zero.file.storage.annotation;

import com.autumn.file.storage.annotation.EnableAutumnStorage;
import com.autumn.zero.file.storage.configure.AutumnZeroFileStorageConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 Zero 文件存储
 *
 * @author 老码农 2019-03-18 18:03:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnStorage
@AutumnZeroFileStorageMybatisScan
@Import({AutumnZeroFileStorageConfiguration.class})
public @interface EnableAutumnZeroFileStorage {

}
