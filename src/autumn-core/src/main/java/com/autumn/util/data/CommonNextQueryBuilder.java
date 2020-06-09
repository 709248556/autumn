package com.autumn.util.data;

import java.util.List;

import com.autumn.application.dto.NextEntityDto;
import com.autumn.application.dto.output.NextQueryResult;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.util.function.FunctionOneResult;
import com.autumn.util.function.FunctionTwoAction;

/**
 * 公共向后查询生成器
 * 
 * @author 老码农
 *
 *         2018-04-11 11:30:58
 */
public class CommonNextQueryBuilder extends AbstractNextQueryBuilder<CommonNextQueryBuilder> {

	private final QueryWrapper query;

	/**
	 * 
	 */
	public CommonNextQueryBuilder() {
		this.query = new QueryWrapper();
	}

	@Override
	public QueryWrapper getQuery() {
		return this.query;
	}

	@Override
	public <TResult extends NextEntityDto> NextQueryResult<TResult> toNextResult(
			FunctionOneResult<QueryWrapper, List<TResult>> queryDelegate) {
		return super.toNextResult(queryDelegate);
	}

	@Override
	public <TEntity, TResult extends NextEntityDto> NextQueryResult<TResult> toNextResult(
			FunctionOneResult<QueryWrapper, List<TEntity>> queryDelegate, Class<TResult> resultClass) {
		return super.toNextResult(queryDelegate, resultClass);
	}

	@Override
	public <TEntity, TResult extends NextEntityDto> NextQueryResult<TResult> toNextResult(
			FunctionOneResult<QueryWrapper, List<TEntity>> queryDelegate, Class<TResult> resultClass,
			FunctionTwoAction<TEntity, TResult> convertDelegate) {
		return super.toNextResult(queryDelegate, resultClass, convertDelegate);
	}

}
