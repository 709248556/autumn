package com.autumn.events;

import com.autumn.domain.entities.Entity;

import java.io.Serializable;

/**
 * 实体删除事件监听
 * <p>
 * 仅支持主键删除
 * </p>
 *
 * @param <TEntity> 实体类型
 * @param <TKey>    主键类型
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 22:02
 **/
public interface EntityDeleteEventListener<TEntity extends Entity<TKey>, TKey extends Serializable>
        extends EntityEventListener<TEntity, TKey> {

    /**
     * 执行删除之前
     *
     * @param source 源
     * @param key    主键
     */
    void deleteBefore(Object source, Serializable key);

    /**
     * 执行删除之后
     *
     * @param source 源
     * @param key    主键
     */
    void deleteAfter(Object source, Serializable key);

    /**
     * 删除完成,并且事务提交完成
     * <pre>
     *     如果未启用事务，则不会引发此项
     * </pre>
     *
     * @param source 源
     * @param key    主键
     */
    void deleteComplete(Object source, Serializable key);

    /**
     * 删除回滚
     * <pre>
     *     如果未启用事务，则不会引发此项
     * </pre>
     *
     * @param source 源
     * @param key    主键
     */
    void deleteRollback(Object source, Serializable key);
}
