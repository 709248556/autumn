package com.autumn.evaluator.annotation;

import com.autumn.evaluator.VariantType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 函数注册
 *
 * @author 老码农
 * <p>
 * 2018-04-25 12:25:06
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface FunctionRegister {
    /**
     * 函数名称
     *
     * @return
     */
    String name();

    /**
     * 分类
     *
     * @return
     */
    String category();

    /**
     * 说明
     *
     * @return
     */
    String caption();

    /**
     * 是否为动态参数
     *
     * @return
     */
    boolean isDynamicParam() default false;

    /**
     * 动态参数类型
     * <p>
     * {@link com.autumn.evaluator.VariantType}
     *
     * @return
     */
    int dynamicParamType() default VariantType.NULL;

    /**
     * 最小参数类型
     *
     * @return
     */
    int minParamCount() default 0;
}
