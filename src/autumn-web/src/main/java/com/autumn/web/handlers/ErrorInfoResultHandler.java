package com.autumn.web.handlers;

import javax.servlet.http.HttpServletRequest;

import com.autumn.web.vo.ErrorInfoResult;

/**
 * 错误信息处理器
 * 
 * @author 老码农
 *
 *         2018-01-14 20:58:24
 */
public interface ErrorInfoResultHandler {

	/**
	 * 获取错误信息
	 * 
	 * @param request
	 *            请求
	 * @param err
	 *            异常
	 * @return
	 *
	 */
	ErrorInfoResult getErrorInfo(HttpServletRequest request, Throwable err);
}
