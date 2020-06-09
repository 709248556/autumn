package com.autumn.zero.common.library.application.callback;

import com.autumn.domain.values.IntegerConstantItemValue;

import java.util.Collection;

/**
 * 整数常量回调
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 19:45
 */
public interface IntegerConstantCallback {

    /**
     * 是否存在
     *
     * @param value 值
     * @return
     */
    boolean exist(Integer value);

    /**
     * 获取项目
     *
     * @param value 值
     * @return
     */
    IntegerConstantItemValue getItem(Integer value);

    /**
     * 获取名称
     *
     * @param value 值
     * @return
     */
    String getName(Integer value);

    /**
     * 获取项目集合
     *
     * @return
     */
    Collection<IntegerConstantItemValue> items();
}
