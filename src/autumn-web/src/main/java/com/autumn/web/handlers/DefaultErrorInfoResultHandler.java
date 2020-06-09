package com.autumn.web.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.autumn.web.vo.ErrorInfoResult;
import com.autumn.runtime.exception.filter.ExceptionFilterContext;

/**
 * 默认错误处理器
 * 
 * @author 老码农
 *
 *         2018-01-14 21:00:39
 */
public class DefaultErrorInfoResultHandler implements ErrorInfoResultHandler {

	/**
	 * 
	 */
	@Autowired
	private ExceptionFilterContext exceptionFilterContext;

	@Override
	public ErrorInfoResult getErrorInfo(HttpServletRequest request, Throwable err) {
		ErrorInfoResult result = exceptionFilterContext.doFilter(err);
		if (result != null) {
			result.getErrorInfo().setDetails(request.getRequestURI());
			return result;
		}
		return new ErrorInfoResult(true, err, 0, err.getMessage(), request.getRequestURI());
	}

}
