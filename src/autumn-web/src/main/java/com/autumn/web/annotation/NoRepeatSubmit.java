package com.autumn.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-14 20:34
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface NoRepeatSubmit {

    /**
     * 锁定时间(毫秒，默认1秒)
     * <p>
     * 指在锁定时间的范围内提交相同内容的数据视为重复提交
     * </p>
     *
     * @return
     */
    int lockMillisecond() default 1000;

    /**
     * 比较内容
     * <p>
     * 指将比较提交的内容，如果内容相同则表示重复提交。
     * </p>
     *
     * @return
     */
    boolean compareContent() default false;

    /**
     * 错误消息
     *
     * @return
     */
    String errorMessage() default "不能重复提交";
}
