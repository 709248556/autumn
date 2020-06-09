package com.autumn.domain.repositories.plugins;

import com.autumn.domain.entities.auditing.Auditing;
import com.autumn.domain.entities.auditing.AuditingTransient;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.mybatis.plugins.AbstractEntityMapperExecutorInterceptor;
import com.autumn.runtime.session.AutumnSession;

/**
 * 审计拦截抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-27 15:18
 */
public abstract class AbstractAuditingInterceptor extends AbstractEntityMapperExecutorInterceptor {

    private AutumnSession session = null;

    /**
     * 获取会话
     *
     * @return
     */
    protected AutumnSession getSession() {
        if (this.session != null) {
            return this.session;
        }
        synchronized (this) {
            if (this.session == null) {
                this.session = this.getBean(AutumnSession.class);
            }
            return this.session;
        }
    }

    @Override
    protected final boolean isInterceptorMethod(MapperInfo mapperInfo, String mapperMethodName) {
        if (AuditingTransient.class.isAssignableFrom(mapperInfo.getEntityClass())) {
            return false;
        }
        return Auditing.class.isAssignableFrom(mapperInfo.getEntityClass())
                && this.isInterceptorAuditingMethod(mapperInfo, mapperMethodName);
    }

    /**
     * 是否拦截审计方法
     *
     * @param mapperInfo
     * @param mapperMethodName
     * @return
     */
    protected abstract boolean isInterceptorAuditingMethod(MapperInfo mapperInfo, String mapperMethodName);

}
