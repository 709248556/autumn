package com.autumn.zero.authorization.plugins;

import java.util.Collection;

/**
 * 资源模块插件上下文
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 22:14
 **/
public interface ResourcesModulePluginContext {

    /**
     * 注册插件
     *
     * @param plugin 插件
     */
    void registerPlugin(ResourcesModulePlugin plugin);

    /**
     * 获取插曲集合
     *
     * @return
     */
    Collection<ResourcesModulePlugin> getPlugins();
}
