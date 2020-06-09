package com.autumn.security.filter;

import java.util.Collection;

/**
 * 过滤器上下文
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 04:28
 **/
public interface AutumnFilterContext {

    /**
     * 添加排除Token登录路径
     *
     * @param path 路径
     */
    void addExcludeTokenLoginPath(String path);

    /**
     * 获取排除登录路径集合
     *
     * @return
     */
    Collection<String> getExcludeTokenLoginPaths();

}
