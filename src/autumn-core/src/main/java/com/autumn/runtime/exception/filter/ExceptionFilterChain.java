package com.autumn.runtime.exception.filter;


import com.autumn.web.vo.ErrorInfoResult;

/**
 * 异常过滤链条
 * 
 * @author 老码农 2018-12-06 23:43:05
 */
public interface ExceptionFilterChain {

	/**
	 * 调用过滤
	 * 
	 * @param e
	 *            异常
	 * @return
	 */
	ErrorInfoResult doFilter(Throwable e);
}
