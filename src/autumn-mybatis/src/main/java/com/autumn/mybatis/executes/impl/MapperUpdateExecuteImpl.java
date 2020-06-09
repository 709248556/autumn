package com.autumn.mybatis.executes.impl;

import com.autumn.mybatis.executes.MapperUpdateExecute;
import com.autumn.mybatis.wrapper.EntityUpdateWrapper;

/**
 * 更新执行器
 * 
 * @author 老码农 2019-06-11 19:03:04
 * @param <TEntity>
 *            实体类型
 */
public class MapperUpdateExecuteImpl<TEntity> extends MapperExecuteImpl<EntityUpdateWrapper<TEntity>, TEntity>
		implements MapperUpdateExecute<TEntity> {

	/**
	 * 
	 * @param wrapper
	 * @param entityClass
	 */
	public MapperUpdateExecuteImpl(EntityUpdateWrapper<TEntity> wrapper, Class<TEntity> entityClass) {
		super(wrapper, entityClass);
	}

	@Override
	public int update() {
		return this.getEntityMapper().updateByWhere(this.wrapper);
	}
}
