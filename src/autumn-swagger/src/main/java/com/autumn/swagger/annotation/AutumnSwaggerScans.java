package com.autumn.swagger.annotation;

import java.lang.annotation.*;

/**
 * Swagger 扫描集合
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 21:43
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutumnSwaggerScans {
    
    /**
     * 扫描集合
     *
     * @return
     */
    AutumnSwaggerScan[] value();
}
