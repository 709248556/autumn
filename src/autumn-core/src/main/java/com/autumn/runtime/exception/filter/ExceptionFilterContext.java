package com.autumn.runtime.exception.filter;

import com.autumn.web.vo.ErrorInfoResult;

import java.util.List;



/**
 * 异常过滤上下文
 * 
 * @author 老码农 2018-12-07 01:05:50
 */
public interface ExceptionFilterContext {

	/**
	 * 添加过滤
	 * 
	 * @param filter
	 *            过滤
	 */
	void addFilter(ExceptionFilter filter);

	/**
	 * 获取过滤集合
	 * 
	 * @return
	 */
	List<ExceptionFilter> getFilters();
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	ErrorInfoResult doFilter(Throwable e);

}
