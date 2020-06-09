package com.autumn.application.service;

import com.autumn.application.dto.input.StatusInput;

import java.io.Serializable;

/***
 * 具有编辑和状态更新的应用服务抽象
 *
 * @author 老码农 2018-11-25 16:01:30
 * @param <TKey> 主键类型
 * @param <TAddInput> 添加输入类型
 * @param <TUpdateInput> 更新输入类型
 * @param <TOutputItem> 输出项目类型
 * @param <TOutputDetails> 输出详情类型
 */
public interface EditByStatusApplicationService<TKey extends Serializable, TAddInput, TUpdateInput, TOutputItem, TOutputDetails>
        extends EditApplicationService<TKey, TAddInput, TUpdateInput, TOutputItem, TOutputDetails> {

    /**
     * 更新状态
     *
     * @param input 输入
     */
    void updateStatus(StatusInput<TKey> input);
}
