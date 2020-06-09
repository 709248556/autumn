package com.autumn.runtime.exception.filter;

import com.autumn.web.vo.ErrorInfoResult;

import java.util.List;



/**
 * 代理异常过滤链条
 * <p>
 * 此对象不能使用单实例，否则将引发未知结果
 * </p>
 * 
 * @author 老码农 2018-12-06 23:59:48
 */
public class ProxyExceptionFilterChain implements ExceptionFilterChain {

	/**
	 * 
	 */
	private final List<ExceptionFilter> filters;
	private int index;

	/**
	 * 实例化
	 * 
	 * @param filters
	 *            过滤集合
	 */
	public ProxyExceptionFilterChain(List<ExceptionFilter> filters) {
		this.filters = filters;
		this.index = 0;
	}

	@Override
	public ErrorInfoResult doFilter(Throwable e) {
		if (this.filters == null || this.filters.size() == this.index) {
			return null;
		}
		return this.filters.get(this.index++).doFilter(e, this);
	}

}
