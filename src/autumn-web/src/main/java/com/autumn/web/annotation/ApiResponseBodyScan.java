package com.autumn.web.annotation;

import java.lang.annotation.*;

/**
 * Api响应体扫描
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 16:15
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApiResponseBodyScan {

    /**
     * 控制器包集合
     *
     * @return
     */
    String[] value();
}
