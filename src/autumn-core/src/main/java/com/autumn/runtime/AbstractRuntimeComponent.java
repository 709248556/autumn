package com.autumn.runtime;

import com.autumn.audited.OperationAuditedLog;
import com.autumn.runtime.cache.ProxyCache;
import com.autumn.runtime.cache.ProxyCacheManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 运行服务组件
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-06 17:20
 */
public abstract class AbstractRuntimeComponent implements RuntimeComponent {

  //  Logger logger = LoggerFactory.getLogger(Object.class);

    /**
     * 日志
     */
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private OperationAuditedLog auditedLog;

    @Autowired
    private ProxyCacheManager cacheManager;

    @Override
    public Log getLogger() {
        return this.logger;
    }

    @Override
    public ProxyCacheManager getCacheManager() {
        return this.cacheManager;
    }

    @Override
    public OperationAuditedLog getAuditedLogger() {
        return this.auditedLog;
    }

    /**
     * 获取或添加缓存
     *
     * @param cacheName   缓存名称
     * @param key         键
     * @param valueLoader 生成
     * @return
     */
    protected final <T> T getOrAddCache(String cacheName, Object key, Callable<T> valueLoader) {
        ProxyCache cache = this.getCacheManager().getCache(cacheName);
        return cache.get(key, valueLoader);
    }

    /**
     * 清除缓存
     *
     * @param cacheName 缓存名称
     */
    protected final void clearCache(String cacheName) {
        ProxyCache cache = this.getCacheManager().getCache(cacheName);
        cache.clear();
    }

    /**
     * 清除缓存键
     *
     * @param cacheName 缓存名称
     * @param key       键
     */
    protected final void clearCacheKey(String cacheName, Object key) {
        ProxyCache cache = this.getCacheManager().getCache(cacheName);
        cache.evict(key);
    }

    /**
     * 清除缓存键集合
     *
     * @param cacheName 缓存名称
     * @param keys      键集合
     */
    protected final void clearCacheKeys(String cacheName, Object... keys) {
        if (keys == null) {
            return;
        }
        ProxyCache cache = this.getCacheManager().getCache(cacheName);
        cache.evict(keys);
    }

    /**
     * 清除缓存键集合
     *
     * @param cacheName 缓存名称
     * @param keys      键集合
     */
    protected final <E> void clearCacheKeys(String cacheName, List<E> keys) {
        if (keys == null) {
            return;
        }
        ProxyCache cache = this.getCacheManager().getCache(cacheName);
        Object[] ks = new Object[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            ks[i] = keys.get(i);
        }
        cache.evict(ks);
    }
}
