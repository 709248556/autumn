package com.autumn.events;

import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * 事务事件
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-30 00:06
 **/
public class TransactionEvent extends ApplicationEvent {

    private static final long serialVersionUID = -7595859770930455505L;

    private final Class<?> entityClass;
    private final EntityEventType eventType;
    private final Serializable entityOrkey;


    /**
     * @param source      源
     * @param entityClass 实体类型
     * @param eventType   事件类型
     * @param entityOrkey 实体或主键
     */
    public TransactionEvent(Object source, Class<?> entityClass, EntityEventType eventType, Serializable entityOrkey) {
        super(source);
        this.entityClass = entityClass;
        this.eventType = eventType;
        this.entityOrkey = entityOrkey;
    }

    /**
     * 获取实体类型
     *
     * @return
     */
    public final Class<?> getEntityClass() {
        return this.entityClass;
    }

    /**
     * 获取事件类型
     *
     * @return
     */
    public final EntityEventType getEventType() {
        return this.eventType;
    }

    /**
     * 获取(实体或主键值
     *
     * @return
     */
    public final Serializable getEntityOrkey() {
        return this.entityOrkey;
    }

}
