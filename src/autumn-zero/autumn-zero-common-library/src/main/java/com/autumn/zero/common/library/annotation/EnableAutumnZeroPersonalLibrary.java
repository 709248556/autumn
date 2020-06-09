package com.autumn.zero.common.library.annotation;

import com.autumn.zero.common.library.configure.AutumnZeroBaseLibraryConfiguration;
import com.autumn.zero.common.library.configure.AutumnZeroPersonalLibraryConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用个人库
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 21:15
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@AutumnZeroPersonalLibraryMybatisScan
@Import({AutumnZeroPersonalLibraryConfiguration.class, AutumnZeroBaseLibraryConfiguration.class})
public @interface EnableAutumnZeroPersonalLibrary {

}
