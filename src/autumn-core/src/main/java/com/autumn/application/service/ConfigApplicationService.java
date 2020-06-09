package com.autumn.application.service;

import com.autumn.application.ApplicationDataCache;

/**
 * 应用配置服务
 *
 * @param <TInput>         输入类型
 * @param <TOutputDetails> 输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-30 19:06
 */
public interface ConfigApplicationService<TInput, TOutput> extends ApplicationService, ApplicationDataCache {

    /**
     * 保存
     *
     * @param input 输入
     * @return
     */
    TOutput save(TInput input);

    /**
     * 查询输出
     *
     * @return
     */
    TOutput queryForOutput();
}
