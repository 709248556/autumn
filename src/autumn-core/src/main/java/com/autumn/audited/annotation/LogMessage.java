package com.autumn.audited.annotation;

import com.autumn.annotation.FriendlyProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志消息
 * <p>
 * 通过在字段是或get方法上配置，自动生成相应的日志信息,与 {@link com.autumn.audited.OperationLog } 接口等同，但接口优先
 * </p>
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-29 22:27
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMessage {

    /**
     * 名称
     * <p>
     * 如果名称为空白值，则取 {@link FriendlyProperty} ,再空则到字段名
     * </p>
     *
     * @return
     */
    String name() default "";

    /**
     * 顺序
     *
     * @return
     */
    int order() default 0;
}
