package com.autumn.events;

import com.autumn.domain.entities.Entity;

import java.io.Serializable;

/**
 * 实体更新事件监听
 * <p>
 * 仅支持单条更新
 * </p>
 *
 * @param <TEntity> 实体类型
 * @param <TKey>    主键类型
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 21:25
 **/
public interface EntityUpdateEventListener<TEntity extends Entity<TKey>, TKey extends Serializable>
        extends EntityEventListener<TEntity, TKey> {

    /**
     * 执行更新之前
     *
     * @param source 源
     * @param entity 实体
     */
    void updateBefore(Object source, TEntity entity);

    /**
     * 执行更新之后
     *
     * @param source 源
     * @param entity 实体
     */
    void updateAfter(Object source, TEntity entity);

    /**
     * 更新完成,并且事务提交完成
     * <pre>
     *     如果未启用事务，则不会引发此项
     * </pre>
     *
     * @param source 源
     * @param entity 实体
     */
    void updateComplete(Object source, TEntity entity);

    /**
     * 更新回滚
     * <pre>
     *     如果未启用事务，则不会引发此项
     * </pre>
     *
     * @param source 源
     * @param entity 实体
     */
    void updateRollback(Object source, TEntity entity);
}
