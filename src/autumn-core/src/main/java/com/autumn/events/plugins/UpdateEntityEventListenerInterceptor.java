package com.autumn.events.plugins;

import com.autumn.domain.entities.Entity;
import com.autumn.events.EntityEventType;
import com.autumn.events.EventBus;
import com.autumn.events.TransactionEvent;
import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperInfo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

/**
 * 更新实体事件拦截
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 23:42
 **/
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class UpdateEntityEventListenerInterceptor extends AbstractEntityEventListenerInterceptor {

    /**
     * @param eventBus
     */
    public UpdateEntityEventListenerInterceptor(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected boolean isInterceptorMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return this.eventBus.existEntityUpdateEventListener(mapperInfo.getEntityClass())
                && (mapperMethodName.equals(EntityMapper.UPDATE)
                || mapperMethodName.equals(EntityMapper.UPDATE_AND_RESET_CHILDREN)
                || mapperMethodName.equals(EntityMapper.UPDATE_BY_NOT_NULL));
    }

    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        Entity listenerEntity = null;
        if (mapperInfo.getMethod().getName().equals(EntityMapper.UPDATE)
                || mapperInfo.getMethod().getName().equals(EntityMapper.UPDATE_AND_RESET_CHILDREN)
                || mapperInfo.getMethod().getName().equals(EntityMapper.UPDATE_BY_NOT_NULL)) {
            Object entity = invocation.getArgs()[1];
            listenerEntity = (Entity) entity;
        }
        if (listenerEntity == null) {
            return invocation.proceed();
        } else {
            final Entity finalListenerEntity = listenerEntity;
            this.eventBus.publishTransactionEvent(new TransactionEvent(invocation.getTarget(),
                    mapperInfo.getEntityClass(), EntityEventType.UPDATE, finalListenerEntity));
            this.eventBus.callEntityUpdateEventListener(mapperInfo.getEntityClass(), c -> {
                c.updateBefore(invocation.getTarget(), finalListenerEntity);
            });
            Object result = invocation.proceed();
            this.eventBus.callEntityUpdateEventListener(mapperInfo.getEntityClass(), c -> {
                c.updateAfter(invocation.getTarget(), finalListenerEntity);
            });
            return result;
        }
    }
}
