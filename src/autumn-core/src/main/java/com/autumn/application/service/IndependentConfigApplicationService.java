package com.autumn.application.service;

/**
 * 独立配置应用服务抽象
 *
 * @param <TEntity> 查询实体类型
 * @param <TInput>  输入类型
 * @param <TOutputDetails> 输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 0:14
 */
public interface IndependentConfigApplicationService<TEntity, TInput, TOutput> extends ConfigApplicationService<TInput, TOutput> {

    /**
     * 查询实体
     *
     * @return
     */
    TEntity queryForEntity();
}
