package com.autumn.mybatis.wrapper;

/**
 * 实体  lambda
 *
 * @param <TExecute> 执行类型
 * @param <TLambda>  lambda类型
 */
public interface EntityLambdaWrapper<TExecute, TLambda> {

    /**
     * 执行器
     *
     * @return
     */
    TExecute execute();

    /**
     * lambda 表达式类型
     *
     * @return
     */
    TLambda lambda();
}
