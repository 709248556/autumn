package com.autumn.evaluator.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 参数集合
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface ParamRegisters {
    ParamRegister[] value();
}
