package com.autumn.zero.file.storage.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文件输出绑定
 * <p>指定的字段类型必须为 {@link com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse} 类型</p>
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-03 21:52
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileOutputBinding {

    /**
     * 文件标识
     * <p>同一输出对象中，不能重复。</p>
     *
     * @return
     */
    int identification();
}
