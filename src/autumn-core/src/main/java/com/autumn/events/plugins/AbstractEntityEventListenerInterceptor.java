package com.autumn.events.plugins;

import com.autumn.events.EventBus;
import com.autumn.mybatis.plugins.AbstractEntityMapperExecutorInterceptor;

/**
 * 实体事件抽象
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 23:23
 **/
public abstract class AbstractEntityEventListenerInterceptor extends AbstractEntityMapperExecutorInterceptor {

    /**
     * 获取事件
     */
    protected final EventBus eventBus;

    /**
     * @param eventBus
     */
    public AbstractEntityEventListenerInterceptor(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
