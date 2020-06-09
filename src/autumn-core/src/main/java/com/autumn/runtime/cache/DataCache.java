package com.autumn.runtime.cache;

/**
 * 数据缓存
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 03:23
 **/
public interface DataCache {

    /**
     * 是否启用缓存
     *
     * @return
     */
    boolean isEnableCache();

    /**
     * 清除所有缓存
     */
    void clearCache();

}
