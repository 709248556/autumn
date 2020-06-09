package com.autumn.zero.file.storage.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 上传绑定
 * <p>
 * 用于在输入对象字段绑定，实现文件保存时的绑定
 * </p>
 * <p>指定的字段类型必须为 {@link java.lang.Long} 类型</p>
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-03 21:49
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileUploadBinding {

    /**
     * 文件标识
     * <p>同一个输入对象不应该重复</p>
     *
     * @return
     */
    int identification();

    /**
     * 说明
     *
     * @return
     */
    String explain() default "";
}
