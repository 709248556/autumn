package com.autumn.util.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 工作表注解，表示一个工作表
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelWorkSheet {

    /**
     * 默认导出说明字体大小
     */
    public static final double DEFAULT_EXPORT_EXPLAIN_FONT_SIZE = 10.0;

    /**
     * 默认导出说明行高
     */
    public static final int DEFAULT_EXPORT_EXPLAIN_ROW_HEIGHT = 50;

    /**
     * 工作表名称
     */
    String sheetName() default "";

    /**
     * 导出标题
     */
    String exportTitle() default "";

    /**
     * 是否导出标题
     */
    boolean isExportTitle() default true;

    /**
     * 导出说明
     */
    String exportExplain() default "";

    /**
     * 导出说明字体大小
     */
    double exportExplainFontSize() default DEFAULT_EXPORT_EXPLAIN_FONT_SIZE;

    /**
     * 导出说明行高
     */
    int exportExplainRowHeight() default DEFAULT_EXPORT_EXPLAIN_ROW_HEIGHT;

}
