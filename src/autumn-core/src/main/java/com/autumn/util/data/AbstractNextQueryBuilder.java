package com.autumn.util.data;

import com.autumn.application.dto.NextEntityDto;
import com.autumn.application.dto.input.DefaultNextQueryInput;
import com.autumn.application.dto.input.NextQueryInput;
import com.autumn.application.dto.output.DefaultNextQueryResult;
import com.autumn.application.dto.output.NextQueryResult;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.function.FunctionOneResult;
import com.autumn.util.function.FunctionTwoAction;

import java.util.List;

/**
 * 下一页查询
 * 
 * @author 老码农
 *
 *         2018-04-11 11:08:22
 */
public abstract class AbstractNextQueryBuilder<TQueryBuilder extends AbstractNextQueryBuilder<TQueryBuilder>>
		extends AbstractQueryBuilder<TQueryBuilder> {

	private NextQueryInput input = null;
	private int limit;

	/**
	 * 
	 */
	public AbstractNextQueryBuilder() {
		this.limit = 10;
	}

	/**
	 * 重置
	 * 
	 * @return
	 */
	@Override
	public TQueryBuilder reset() {
		super.reset();
		this.input = null;
		this.limit = 10;
		return this.returnThis();
	}

	/**
	 * 限制
	 * 
	 * @param input
	 *            输入
	 * @param limit
	 *            限制大小
	 * @return
	 */
	public TQueryBuilder limit(NextQueryInput input, int limit) {
		if (input == null) {
			input = new DefaultNextQueryInput();
		}
		if (input.getLastRowNumber() == null || input.getLastRowNumber() < 0) {
			input.setLastRowNumber(0);
		}
		if (limit < 1) {
			limit = 1;
		}
		this.limit = limit;
		this.input = input;
		return this.returnThis();
	}

	/**
	 * 页
	 * 
	 * @param offset
	 *            偏移
	 * @param limit
	 *            限制大小
	 * @return
	 */
	public TQueryBuilder limit(Integer offset, int limit) {
		if (this.input == null) {
			this.input = new DefaultNextQueryInput();
		}
		this.input.setLastRowNumber(offset);
		if (limit < 1) {
			limit = 1;
		}
		this.limit = limit;
		return this.returnThis();
	}

	/**
	 * 输出结果
	 * 
	 * @param queryDelegate
	 *            查询委托
	 * @return
	 *
	 */
	protected <TResult extends NextEntityDto> NextQueryResult<TResult> toNextResult(
			FunctionOneResult<QueryWrapper, List<TResult>> queryDelegate) {
		ExceptionUtils.checkNotNull(queryDelegate, "queryDelegate");
		if (this.getQuery().getOrderColumns() == 0) {
			ExceptionUtils.throwValidationException("无排序列,无法向后查询。");
		}
		if (this.input == null) {
			ExceptionUtils.throwValidationException("未设置偏移信息。");
		}
		DefaultNextQueryResult<TResult> result = new DefaultNextQueryResult<>(this.input.getLastRowNumber(), this.limit);
		this.getQuery().limit(result.getStartRowNumber(), result.getLimit());		
		List<TResult> items = queryDelegate.apply(this.getQuery());
		this.setResultRowNumber(result, items);
		return result;
	}

	/**
	 * 输出结果
	 * 
	 * @param queryDelegate
	 * @param resultClass
	 * @return
	 *
	 */
	protected <TEntity, TResult extends NextEntityDto> NextQueryResult<TResult> toNextResult(
			FunctionOneResult<QueryWrapper, List<TEntity>> queryDelegate, Class<TResult> resultClass) {
		return this.toNextResult(queryDelegate, resultClass, null);
	}

	/**
	 * 输出结果
	 * 
	 * @param queryDelegate
	 * @param resultClass
	 * @param convertDelegate
	 * @return
	 *
	 */
	protected <TEntity, TResult extends NextEntityDto> NextQueryResult<TResult> toNextResult(
			FunctionOneResult<QueryWrapper, List<TEntity>> queryDelegate, Class<TResult> resultClass,
			FunctionTwoAction<TEntity, TResult> convertDelegate) {
		if (this.getQuery().getOrderColumns() == 0) {
			ExceptionUtils.throwValidationException("无排序列,无法向后查询。");
		}
		if (this.input == null) {
			ExceptionUtils.throwValidationException("未设置偏移信息。");
		}
		DefaultNextQueryResult<TResult> result = new DefaultNextQueryResult<>(this.input.getLastRowNumber(), this.limit);
		this.getQuery().limit(result.getStartRowNumber(), result.getLimit());		
		List<TEntity> entitys = queryDelegate.apply(this.getQuery());
		List<TResult> items = AutoMapUtils.mapForList(entitys, resultClass, convertDelegate);
		this.setResultRowNumber(result, items);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param items
	 *
	 */
	private <TResult extends NextEntityDto> void setResultRowNumber(DefaultNextQueryResult<TResult> result,
			List<TResult> items) {
		int start = result.getStartRowNumber() + 1;
		for (TResult item : items) {
			item.setRowNumber(start);
			start++;
		}
		result.setItems(items);
	}
}
