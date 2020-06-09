package com.autumn.application.service;

import java.io.Serializable;

/**
 * 编辑服务
 *
 * @param <TKey>           主键类型
 * @param <TAddInput>      添加输入类型
 * @param <TUpdateInput>   更新输入类型
 * @param <TOutputItem>    输出项目类型
 * @param <TOutputDetails> 输出详情类型
 * @author 老码农 2018-11-16 16:55:52
 */
public interface EditApplicationService<TKey extends Serializable, TAddInput, TUpdateInput, TOutputItem, TOutputDetails>
        extends QueryApplicationService<TKey, TOutputItem, TOutputDetails> {

    /**
     * 添加
     *
     * @param input 输入
     * @return
     */
    TOutputDetails add(TAddInput input);

    /**
     * 更新
     *
     * @param input 输入
     * @return
     */
    TOutputDetails update(TUpdateInput input);

    /**
     * 根据主键删除
     *
     * @param id 主键
     */
    void deleteById(TKey id);
}
