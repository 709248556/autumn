package com.autumn.runtime.plugins;

/**
 * 运行时插件
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 18:56
 **/
public interface RuntimePlugin {

    /**
     * 获取插件名称
     *
     * @return
     */
    String getName();

    /**
     * 获取插件描述
     *
     * @return
     */
    String getDescribe();
}
