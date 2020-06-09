package com.autumn.runtime.cache.impl;

import com.autumn.application.ApplicationDataCache;
import com.autumn.domain.services.DomainDataCache;
import com.autumn.exception.ExceptionUtils;
import com.autumn.runtime.cache.DataCache;
import com.autumn.runtime.cache.DataCacheManager;
import org.springframework.beans.factory.DisposableBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 缓存数据管理实现
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 04:15
 **/
public class DataCacheManagerImpl implements DataCacheManager, DisposableBean {

    private final List<DataCache> dataCacheList = new ArrayList<>(16);

    @Override
    public void registerDataCache(DataCache dataCache) {
        ExceptionUtils.checkNotNull(dataCache, "dataCache");
        this.dataCacheList.add(dataCache);
    }

    @Override
    public Collection<DataCache> dataCaches() {
        return Collections.unmodifiableList(this.dataCacheList);
    }

    @Override
    public Collection<ApplicationDataCache> applicationDataCaches() {
        return this.dataCacheList.stream()
                .filter(s -> s instanceof ApplicationDataCache)
                .map(s -> (ApplicationDataCache) s).collect(Collectors.toList());
    }

    @Override
    public Collection<DomainDataCache> domainDataCaches() {
        return this.dataCacheList.stream()
                .filter(s -> s instanceof DomainDataCache)
                .map(s -> (DomainDataCache) s).collect(Collectors.toList());
    }


    @Override
    public int dataCacheSize() {
        return this.dataCacheList.size();
    }

    @Override
    public void clearAllCache() {
        for (DataCache dataCache : dataCacheList) {
            dataCache.clearCache();
        }
    }

    @Override
    public void clearApplicationCache() {
        Collection<ApplicationDataCache> caches = this.applicationDataCaches();
        for (ApplicationDataCache cache : caches) {
            cache.clearCache();
        }
    }

    @Override
    public void clearDomainCache() {
        Collection<DomainDataCache> caches = this.domainDataCaches();
        for (DomainDataCache cache : caches) {
            cache.clearCache();
        }
    }

    @Override
    public void destroy() throws Exception {
        this.dataCacheList.clear();
    }
}
