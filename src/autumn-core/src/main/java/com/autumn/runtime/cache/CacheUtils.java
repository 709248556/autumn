package com.autumn.runtime.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

import java.util.concurrent.Callable;

/**
 * 缓存帮助工具
 *
 * @author 老码农 2019-05-22 22:49:41
 */
public class CacheUtils {

    private static final Log logger = LogFactory.getLog(CacheUtils.class);

    /**
     * 获取或添加
     *
     * @param cacheName   缓存名称
     * @param key         键
     * @param valueLoader 生成
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getOrAdd(String cacheName, Object key, Callable<T> valueLoader) {
        Cache cache = CacheContext.getCache(cacheName);
        if (cache != null) {
            ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                try {
                    Object cacheValue = valueWrapper.get();
                    if (cacheValue == null) {
                        return null;
                    }
                    return (T) cacheValue;
                } catch (Exception e) {
                    logger.error("读取缓存出错key(" + key.toString() + "):" + e.getMessage(), e);
                    cache.evict(key);
                }
            }
        }
        T value = null;
        if (valueLoader != null) {
            try {
                value = valueLoader.call();
            } catch (Exception e) {
                logger.error("生成缓存出错key(" + key.toString() + "):" + e.getMessage(), e);
            }
        }
        if (cache != null) {
            if (value != null) {
                cache.put(key, value);
            } else {
                cache.evict(key);
            }
        }
        return value;
    }

    /**
     * 获取
     *
     * @param cacheName 缓存名称
     * @param key       键
     * @return
     */
    public static <T> T get(String cacheName, Object key, Class<T> type) {
        Cache cache = CacheContext.getCache(cacheName);
        if (cache != null) {
            try {
                return cache.get(key, type);
            } catch (Exception e) {
                logger.error("读取缓存出错key(" + key.toString() + "):" + e.getMessage(), e);
                cache.evict(key);
            }
        }
        return null;
    }

    /**
     * 清除
     *
     * @param cacheName 缓存名称
     */
    public static void clear(String cacheName) {
        Cache cache = CacheContext.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    /**
     * 移除键
     *
     * @param cacheName 缓存名称
     * @param key       键
     */
    public static void removeKey(String cacheName, Object key) {
        if (key != null) {
            Cache cache = CacheContext.getCache(cacheName);
            if (cache != null) {
                cache.evict(key);
            }
        }
    }

    /**
     * 移除键集合
     *
     * @param cacheName 缓存名称
     * @param keys      键集合
     */
    public static void removeKeys(String cacheName, Object... keys) {
        if (keys == null || keys.length == 0) {
            return;
        }
        Cache cache = CacheContext.getCache(cacheName);
        if (cache != null) {
            for (Object key : keys) {
                if (key != null) {
                    cache.evict(key);
                }
            }
        }
    }

    /**
     * 放入
     *
     * @param cacheName 缓存名称
     * @param key       键
     * @param value     值
     */
    public static void put(String cacheName, Object key, Object value) {
        Cache cache = CacheContext.getCache(cacheName);
        if (cache != null) {
            if (value != null) {
                cache.put(key, value);
            } else {
                cache.evict(key);
            }
        }
    }
}
