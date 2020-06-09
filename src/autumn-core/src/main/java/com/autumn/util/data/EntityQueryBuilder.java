package com.autumn.util.data;

import java.util.List;

import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.function.FunctionTwoAction;

/**
 * 实体查询生成器
 * 
 * @author 老码农 2019-04-04 01:58:48
 * @param <TEntity>
 *            实体类型
 */
public class EntityQueryBuilder<TEntity> extends AbstractQueryBuilder<EntityQueryBuilder<TEntity>> {

	private final EntityQueryWrapper<TEntity> query;

	/**
	 * 
	 * @param entityClass
	 */
	public EntityQueryBuilder(Class<TEntity> entityClass) {
		ExceptionUtils.checkNotNull(entityClass, "entityClass");
		query = new EntityQueryWrapper<>(entityClass);
	}

	@Override
	public EntityQueryWrapper<TEntity> getQuery() {
		return this.query;
	}

	/**
	 * 输出记录数
	 * 
	 * @param mapper
	 *            仓储
	 * 
	 * @return
	 */
	public int toCount(EntityMapper<TEntity> mapper) {		
		return mapper.countByWhere(this.getQuery());
	}

	/**
	 * 输出结果
	 * 
	 * @param mapper
	 *            仓储
	 * 
	 * @return
	 */
	public List<TEntity> toResult(EntityMapper<TEntity> mapper) {		
		return mapper.selectForList(this.getQuery());
	}

	/**
	 * 输出结果
	 * 
	 * @param mapper
	 *            仓储
	 * @param resultClass
	 *            结果类型
	 * @return
	 */
	public <TResult> List<TResult> toResult(EntityMapper<TEntity> mapper, Class<TResult> resultClass) {
		return this.toResult(mapper, resultClass, null);
	}

	/**
	 * 输出结果
	 * 
	 * @param mapper
	 *            仓储
	 * @param resultClass
	 *            结果类型
	 * @param convertDelegate
	 *            转换器
	 * @return
	 */
	public <TResult> List<TResult> toResult(EntityMapper<TEntity> mapper, Class<TResult> resultClass,
			FunctionTwoAction<TEntity, TResult> convertDelegate) {
		List<TEntity> entitys = this.toResult(mapper);
		return AutoMapUtils.mapForList(entitys, resultClass, convertDelegate);
	}

}
