package com.autumn.security.filter.impl;

import com.autumn.security.filter.AutumnFilterContext;
import org.springframework.beans.factory.DisposableBean;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 05:01
 **/
public class AutumnFilterContextImpl
        implements AutumnFilterContext, DisposableBean {

    /**
     * 排除路径
     */
    private final Set<String> excludeTokenLoginPaths = new LinkedHashSet<>(16);

    @Override
    public void addExcludeTokenLoginPath(String path) {
        this.excludeTokenLoginPaths.add(path);
    }


    @Override
    public Collection<String> getExcludeTokenLoginPaths() {
        return this.excludeTokenLoginPaths;
    }

    @Override
    public void destroy() throws Exception {
        this.excludeTokenLoginPaths.clear();
    }
}
