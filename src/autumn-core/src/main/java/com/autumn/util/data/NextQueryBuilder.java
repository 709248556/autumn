package com.autumn.util.data;

import com.autumn.application.dto.NextEntityDto;
import com.autumn.application.dto.output.NextQueryResult;
import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.function.FunctionTwoAction;

/**
 * 向后查询生成器
 * 
 * @author 老码农
 *
 *         2018-04-03 11:22:33
 */
public class NextQueryBuilder<TEntity> extends AbstractNextQueryBuilder<NextQueryBuilder<TEntity>> {

	private final EntityQueryWrapper<TEntity> query;

	/**
	 * 
	 * @param entityClass
	 */
	public NextQueryBuilder(Class<TEntity> entityClass) {
		ExceptionUtils.checkNotNull(entityClass, "entityClass");
		query = new EntityQueryWrapper<>(entityClass);
	}

	@Override
	public EntityQueryWrapper<TEntity> getQuery() {
		return this.query;
	}

	/**
	 * 输出结果
	 * 
	 * @param mapper
	 *            仓储
	 * @param resultClass
	 * @return
	 */
	public <TResult extends NextEntityDto> NextQueryResult<TResult> toNextResult(EntityMapper<TEntity> mapper,
			Class<TResult> resultClass) {
		return this.toNextResult(mapper, resultClass, null);
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
	public <TResult extends NextEntityDto> NextQueryResult<TResult> toNextResult(EntityMapper<TEntity> mapper,
			Class<TResult> resultClass, FunctionTwoAction<TEntity, TResult> convertDelegate) {		
		return this.toNextResult(mapper::selectForList, resultClass, convertDelegate);
	}
}
