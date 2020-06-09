package com.autumn.zero.authorization.plugins;

import com.autumn.runtime.plugins.RuntimePlugin;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModuleType;
import com.autumn.zero.authorization.plugins.data.ResourcesModuleData;

import java.util.List;

/**
 * 资源模块插件
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 18:58
 **/
public interface ResourcesModulePlugin extends RuntimePlugin {

    /**
     * 创建模块类型集合
     *
     * @return
     */
    List<ResourcesModuleType> createModuleTypes();

    /**
     * 创建模块集合
     *
     * @return
     */
    List<ResourcesModuleData> createModules();
}
