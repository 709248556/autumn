package com.autumn.events;

import com.autumn.domain.entities.Entity;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * 事件总线
 *
 * @author 老码农
 * <p>
 * 2017-09-22 19:09:29
 */
public interface EventBus {

    /**
     * 注册实体
     *
     * @param listener  监听器
     * @param <TEntity> 实体类型
     * @param <TKey>    主键类型
     */
    <TEntity extends Entity<TKey>, TKey extends Serializable> void registerEntityEventListener(EntityEventListener<TEntity, TKey> listener);

    /**
     * 存在实体插入事件监听
     *
     * @param entityClass 实体
     * @return
     */
    boolean existEntityInsertEventListener(Class<?> entityClass);

    /**
     * 存在实体更新事件监听
     *
     * @param entityClass 实体
     * @return
     */
    boolean existEntityUpdateEventListener(Class<?> entityClass);

    /**
     * 存在实体删除事件监听
     *
     * @param entityClass 实体
     * @return
     */
    boolean existDeleteUpdateEventListener(Class<?> entityClass);

    /**
     * 调用实体插入事件监听
     *
     * @param entityClass 实体类型
     * @param consumer    消费
     */
    void callEntityInsertEventListener(Class<?> entityClass, Consumer<EntityInsertEventListener> consumer);

    /**
     * 调用实体更新事件监听
     *
     * @param entityClass 实体类型
     * @param consumer    消费
     */
    void callEntityUpdateEventListener(Class<?> entityClass, Consumer<EntityUpdateEventListener> consumer);

    /**
     * 调用实体删除事件监听
     *
     * @param entityClass 实体类型
     * @param consumer    消费
     */
    void callEntityDeleteEventListener(Class<?> entityClass, Consumer<EntityDeleteEventListener> consumer);

    /**
     * 发布事务事件
     *
     * @param event 事件
     */
    void publishTransactionEvent(TransactionEvent event);
}
