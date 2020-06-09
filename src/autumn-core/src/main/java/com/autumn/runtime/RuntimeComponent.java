package com.autumn.runtime;

import com.autumn.audited.OperationAuditedLog;
import com.autumn.runtime.cache.ProxyCacheManager;
import org.apache.commons.logging.Log;

/**
 * 运行时组件
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-06 17:18
 */
public interface RuntimeComponent {

    /**
     * 获取日志
     *
     * @return
     */
    Log getLogger();

    /**
     * 获取审计日志
     *
     * @return
     */
    OperationAuditedLog getAuditedLogger();

    /**
     * 获取代理缓存管理
     *
     * @return
     */
    ProxyCacheManager getCacheManager();
}
