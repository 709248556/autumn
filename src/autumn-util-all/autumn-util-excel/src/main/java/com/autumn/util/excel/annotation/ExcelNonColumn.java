package com.autumn.util.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Excel忽略列，如果配置此注解时，将忽略特定的字段或get方法
 */
@Retention(RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ExcelNonColumn {

}
