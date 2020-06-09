package com.autumn.domain.repositories;

import java.io.Serializable;

import com.autumn.domain.entities.Entity;
import com.autumn.mybatis.mapper.EntityMapper;

/**
 * 实体仓储
 * @author 老码农
 * 2019-05-23 11:02:06
 * @param <TEntity>
 * @param <TKey>
 */
public interface EntityRepository<TEntity extends Entity<TKey>, TKey extends Serializable>
		extends EntityMapper<TEntity> {

}
