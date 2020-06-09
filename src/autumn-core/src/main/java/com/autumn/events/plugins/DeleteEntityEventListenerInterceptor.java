package com.autumn.events.plugins;

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

import java.io.Serializable;

/**
 * 删除主键事件拦截
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 23:49
 **/
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class DeleteEntityEventListenerInterceptor extends AbstractEntityEventListenerInterceptor {

    /**
     * @param eventBus
     */
    public DeleteEntityEventListenerInterceptor(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected boolean isInterceptorMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return this.eventBus.existDeleteUpdateEventListener(mapperInfo.getEntityClass())
                && mapperMethodName.equals(EntityMapper.DELETE_BY_ID);
    }

    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        Serializable keyValue = null;
        if (mapperInfo.getMethod().getName().equals(EntityMapper.DELETE_BY_ID)) {
            keyValue = (Serializable) invocation.getArgs()[1];
        }
        if (keyValue == null) {
            return invocation.proceed();
        } else {
            final Serializable finalKeyValue = keyValue;
            this.eventBus.publishTransactionEvent(new TransactionEvent(invocation.getTarget(),
                    mapperInfo.getEntityClass(), EntityEventType.DELETE, finalKeyValue));
            this.eventBus.callEntityDeleteEventListener(mapperInfo.getEntityClass(), c -> {
                c.deleteBefore(invocation.getTarget(), finalKeyValue);
            });
            Object result = invocation.proceed();
            this.eventBus.callEntityDeleteEventListener(mapperInfo.getEntityClass(), c -> {
                c.deleteAfter(invocation.getTarget(), finalKeyValue);
            });
            return result;
        }
    }
}
