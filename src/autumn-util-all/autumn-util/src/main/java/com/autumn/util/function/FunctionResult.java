package com.autumn.util.function;

import java.io.Serializable;

/**
 * 无参数和具有返回值函数
 *
 * @author 老码农
 * <p>
 * 2017-12-05 16:30:25
 */
@FunctionalInterface
public interface FunctionResult<TResult> extends Serializable {

    /**
     * 应用
     *
     * @param t 参数
     * @return 2017-12-05 16:31:16
     */
    TResult apply();

}
