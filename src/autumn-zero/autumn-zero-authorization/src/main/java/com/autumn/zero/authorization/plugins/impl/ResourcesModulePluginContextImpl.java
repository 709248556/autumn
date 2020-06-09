package com.autumn.zero.authorization.plugins.impl;

import com.autumn.zero.authorization.plugins.ResourcesModulePlugin;
import com.autumn.zero.authorization.plugins.ResourcesModulePluginContext;
import org.springframework.beans.factory.DisposableBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 22:15
 **/
public class ResourcesModulePluginContextImpl implements ResourcesModulePluginContext, DisposableBean {

    private final List<ResourcesModulePlugin> plugins = new ArrayList<>(16);

    @Override
    public void registerPlugin(ResourcesModulePlugin plugin) {
        this.plugins.add(plugin);
    }

    @Override
    public Collection<ResourcesModulePlugin> getPlugins() {
        return this.plugins;
    }

    @Override
    public void destroy() throws Exception {
        this.plugins.clear();
    }
}
