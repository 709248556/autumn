package com.autumn.util.excel.annotation;

import com.autumn.util.HorizontalAlignment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Excel列注解配置
 */
@Retention(RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ExcelColumn {

    /**
     * 友好名称
     */
    String friendlyName() default "";

    /**
     * 格式
     */
    String format() default "";

    /**
     * 分组名称，即多个列分组
     */
    String groupName() default "";

    /**
     * 列宽度
     */
    int width() default 80;

    /**
     * 顺序
     */
    int order() default 0;

    /**
     * 获取或设置导入非空值
     */
    boolean importNotNullable() default false;

    /**
     * 获取或设置是否导入列
     */
    boolean isImportColumn() default false;

    /**
     * 是否合并内容相同的行
     */
    boolean isMergeContentRow() default false;

    /**
     * 是否合并空白内容相同的行
     */
    boolean isMergeBlankContentRow() default false;

    /**
     * 水平对齐方式
     */
    HorizontalAlignment alignment() default HorizontalAlignment.LEFT;

}
