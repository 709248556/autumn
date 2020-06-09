package com.autumn.evaluator;

import java.util.Collection;

/**
 * 获取函数上下文
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-16 23:44
 */
public interface FunctionParamContext {

    /**
     * 获取函数
     *
     * @return
     */
    Function getFunction();

    /**
     * 获取参数数量
     *
     * @return
     */
    int getParamSize();

    /**
     * 获取参数
     *
     * @param order 顺序
     * @return
     */
    FunctionParam getParam(int order);

    /**
     * 获取参数
     *
     * @param name 名称
     * @return
     */
    FunctionParam getParam(String name);

    /**
     * 获取参数集合
     *
     * @return
     */
    Collection<FunctionParam> getParams();

}
