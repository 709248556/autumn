package com.autumn.runtime.exception.filter;


import com.autumn.web.vo.ErrorInfoResult;

/**
 * 异常过滤
 * 
 * @author 老码农 2018-12-06 23:30:26
 */
public interface ExceptionFilter {

	/**
	 * 调用过滤
	 * 
	 * @param e
	 *            异常
	 * @param chain
	 *            链条
	 * @return
	 */
	ErrorInfoResult doFilter(Throwable e, ExceptionFilterChain chain);
}
