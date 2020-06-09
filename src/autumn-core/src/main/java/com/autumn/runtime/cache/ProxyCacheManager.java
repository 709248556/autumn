package com.autumn.runtime.cache;

import java.util.Collection;

/**
 * 代理缓存管理
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-06 16:19
 */
public interface ProxyCacheManager {

    /**
     * 获取代理缓存
     *
     * @param name 缓存名称
     * @return
     */
    ProxyCache getCache(String name);

    /**
     * 清除缓存
     *
     * @param name 缓存名称
     */
    void clearCache(String name);

    /**
     * 清除缓存键
     * Key
     *
     * @param name 缓存名称
     * @param key
     */
    void clearCacheKey(String name, Object key);

    /**
     * 清除缓存键集合
     * Key
     *
     * @param name 缓存名称
     * @param keys 键集合
     */
    void clearCacheKeys(String name, Object... keys);

    /**
     * 获取缓存名称集合
     *
     * @return
     */
    Collection<String> getCacheNames();

}
