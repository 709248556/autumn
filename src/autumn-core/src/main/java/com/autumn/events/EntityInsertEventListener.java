package com.autumn.events;

import com.autumn.domain.entities.Entity;

import java.io.Serializable;

/**
 * 实体插入事件监听
 * <p>
 * 不支持单条插入
 * </p>
 *
 * @param <TEntity> 实体类型
 * @param <TKey>    主键类型
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 21:04
 **/
public interface EntityInsertEventListener<TEntity extends Entity<TKey>, TKey extends Serializable>
        extends EntityEventListener<TEntity, TKey> {

    /**
     * 执行插入之前
     *
     * @param source 源
     * @param entity 实体
     */
    void insertBefore(Object source, TEntity entity);

    /**
     * 执行插入之后
     *
     * @param source 源
     * @param entity 实体
     */
    void insertAfter(Object source, TEntity entity);

    /**
     * 插入完成,并且事务提交完成
     * <pre>
     *     如果未启用事务，则不会引发此项
     * </pre>
     *
     * @param source 源
     * @param entity 实体
     */
    void insertComplete(Object source, TEntity entity);

    /**
     * 插入回滚
     * <pre>
     *     如果未启用事务，则不会引发此项
     * </pre>
     *
     * @param source 源
     * @param entity 实体
     */
    void insertRollback(Object source, TEntity entity);

}
