package com.autumn.runtime.exception.filter;


import com.autumn.web.vo.ErrorInfoResult;

/**
 * 异常过滤抽象
 * 
 * @author 老码农 2018-12-07 01:57:01
 */
public abstract class AbstractExceptionFilter implements ExceptionFilter {

	/**
	 * 
	 */
	@Override
	public final ErrorInfoResult doFilter(Throwable e, ExceptionFilterChain chain) {
		ErrorInfoResult result = doInternalFilter(e);
		if (result != null) {
			return result;
		}
		return chain.doFilter(e);
	}

	/**
	 * 调用内部过滤
	 * 
	 * @param e
	 *            异常
	 * @return
	 */
	protected abstract ErrorInfoResult doInternalFilter(Throwable e);

	/**
	 * 获取 Api 异常
	 * 
	 * @param e
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <TError extends Exception> TError getException(Throwable e, Class<TError> errorClass) {
		if (e == null) {
			return null;
		}
		if (e.getClass().equals(errorClass) || errorClass.isAssignableFrom(e.getClass())) {
			return (TError) e;
		}
		if (e.getCause() != null) {
			e = e.getCause();
			return getException(e, errorClass);
		}
		return null;
	}
}
