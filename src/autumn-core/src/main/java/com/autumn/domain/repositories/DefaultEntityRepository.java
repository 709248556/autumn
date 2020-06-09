package com.autumn.domain.repositories;

import com.autumn.domain.entities.Entity;

/**
 * 默认实体仓储
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-31 21:31:49
 * @param <TEntity>
 *            实体类型
 */
public interface DefaultEntityRepository<TEntity extends Entity<Long>> extends EntityRepository<TEntity, Long> {

}
