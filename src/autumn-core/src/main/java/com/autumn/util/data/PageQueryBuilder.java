package com.autumn.util.data;

import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.function.FunctionTwoAction;

/**
 * 页查询生成器
 * 
 * @author 老码农 2018-04-03 00:34:06
 */
public class PageQueryBuilder<TEntity> extends AbstractPageQueryBuilder<PageQueryBuilder<TEntity>> {

	private final EntityQueryWrapper<TEntity> query;

	/**
	 * 
	 * @param entityClass
	 */
	public PageQueryBuilder(Class<TEntity> entityClass) {
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
	 * 
	 * @return
	 */
	public PageResult<TEntity> toPageResult(EntityMapper<TEntity> mapper) {
		ExceptionUtils.checkNotNull(mapper, "mapper");
		return this.toPageResult(mapper::countByWhere, mapper::selectForList);
	}

	/**
	 * 输出结果
	 * 
	 * @param mapper
	 *            仓储
	 * @param resultClass
	 * @return
	 */
	public <TResult> PageResult<TResult> toPageResult(EntityMapper<TEntity> mapper, Class<TResult> resultClass) {
		return this.toPageResult(mapper, resultClass, null);
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
	public <TResult> PageResult<TResult> toPageResult(EntityMapper<TEntity> mapper, Class<TResult> resultClass,
			FunctionTwoAction<TEntity, TResult> convertDelegate) {
		ExceptionUtils.checkNotNull(mapper, "mapper");
		ExceptionUtils.checkNotNull(resultClass, "resultClass");
		return this.toPageResult(mapper::countByWhere, mapper::selectForList, resultClass, convertDelegate);
	}
}
