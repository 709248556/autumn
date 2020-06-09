package com.autumn.events;

import com.autumn.domain.entities.Entity;

import java.io.Serializable;
import java.util.EventListener;

/**
 * 实体事件监听
 * <p>
 *
 * </p>
 *
 * @param <TEntity> 实体类型
 * @param <TKey>    主键类型
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 21:37
 **/
public interface EntityEventListener<TEntity extends Entity<TKey>, TKey extends Serializable>
        extends EventListener {

}
