package com.autumn.runtime.cache;

import com.autumn.application.ApplicationDataCache;
import com.autumn.domain.services.DomainDataCache;

import java.util.Collection;

/**
 * 缓存数据管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 04:14
 **/
public interface DataCacheManager {

    /**
     * 注册缓存数
     *
     * @param dataCache
     */
    void registerDataCache(DataCache dataCache);

    /**
     * 获取缓存数据集合
     *
     * @return
     */
    Collection<DataCache> dataCaches();

    /**
     * 应用缓存数
     *
     * @return
     */
    Collection<ApplicationDataCache> applicationDataCaches();

    /**
     * 领域缓存数
     *
     * @return
     */
    Collection<DomainDataCache> domainDataCaches();

    /**
     * 数据缓存大小
     *
     * @return
     */
    int dataCacheSize();

    /**
     * 清除所有缓存
     */
    void clearAllCache();

    /**
     * 清除应用缓存
     */
    void clearApplicationCache();

    /**
     * 清除领域应用缓存
     */
    void clearDomainCache();
}
