package com.autumn.util;

/**
 * 具有值的枚举
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 18:12
 */
public interface ValuedEnum<T> {

    /**
     * 枚举值
     *
     * @return
     */
    T value();
}
