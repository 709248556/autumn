package com.autumn.mybatis.provider.annotation;

import com.autumn.mybatis.provider.ProviderDriveType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提供程序驱动
 *
 * @author 老码农
 * <p>
 * 2017-10-19 08:51:46
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProviderDrive {

    /**
     * 驱动类型
     *
     * @return
     */
    ProviderDriveType value();
}
