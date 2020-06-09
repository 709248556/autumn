package com.autumn.zero.authorization.plugins;

import com.autumn.zero.authorization.application.services.callback.impl.DefaultAuthCallback;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModuleType;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 02:09
 **/
public abstract class AbstractResourcesModulePlugin implements ResourcesModulePlugin {

    @Override
    public List<ResourcesModuleType> createModuleTypes() {
        List<ResourcesModuleType> moduleTypes = new ArrayList<>(2);
        ResourcesModuleType moduleType = new ResourcesModuleType();
        moduleType.setId((long) DefaultAuthCallback.DEFAULT_MODULE_RESOURCES_TYPE);
        moduleType.setName("后台模块");
        moduleTypes.add(moduleType);
        return moduleTypes;
    }
}
