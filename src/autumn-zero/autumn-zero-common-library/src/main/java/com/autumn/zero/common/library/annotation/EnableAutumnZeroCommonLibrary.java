package com.autumn.zero.common.library.annotation;


import com.autumn.zero.common.library.configure.AutumnZeroBaseLibraryConfiguration;
import com.autumn.zero.common.library.configure.AutumnZeroCommonLibraryConfiguration;
import com.autumn.zero.file.storage.annotation.EnableAutumnZeroFileStorage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用公共运行库
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 16:47
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroFileStorage
@AutumnZeroCommonLibraryMybatisScan
@Import({AutumnZeroCommonLibraryConfiguration.class, AutumnZeroBaseLibraryConfiguration.class})
public @interface EnableAutumnZeroCommonLibrary {

}
