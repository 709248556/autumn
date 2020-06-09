package com.autumn.zero.common.library.application.services.personal;

import com.autumn.application.service.EditApplicationService;

/**
 * 用户个人服务
 * <p>
 * </p>
 *
 * @param <TAddInput>      添加时输入类型
 * @param <TUpdateInput>   更新时输入类型
 * @param <TOutputItem>    输出项目时的类型(列表)
 * @param <TOutputDetails> 详情输出类型
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 18:22
 **/
public interface UserPresonalDefaultService<TAddInput, TUpdateInput, TOutputItem, TOutputDetails>
        extends EditApplicationService<Long, TAddInput, TUpdateInput, TOutputItem, TOutputDetails> {

    /**
     * 默认更新
     *
     * @param id 主键
     */
    void defaultUpdateById(Long id);

    /**
     * 查询默认
     *
     * @return
     */
    TOutputDetails queryDefault();

    /**
     * 获取限制个人最大记录数
     *
     * @return 小于或等于 0 表示不限制，默认为 -1。
     */
    int getLimitPresonalMaxRecord();

    /**
     * 设置限制个人最大记录数
     *
     * @param limitPresonalMaxRecord 限制个人最大记录数
     */
    void setLimitPresonalMaxRecord(int limitPresonalMaxRecord);

}
