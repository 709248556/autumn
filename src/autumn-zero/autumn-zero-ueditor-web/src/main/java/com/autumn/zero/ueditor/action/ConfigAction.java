package com.autumn.zero.ueditor.action;

import com.autumn.zero.ueditor.model.UeditorConfig;

/**
 * 配置执行器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-24 17:55
 **/
public interface ConfigAction {

    /**
     * 刷新配置
     */
    void refreshConfig();

    /**
     * 读取配置
     *
     * @return
     */
    UeditorConfig readConfig();
}
