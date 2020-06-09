package com.autumn.runtime.exception.filter;


import com.autumn.web.vo.ErrorInfoResult;

/**
 * 空指针异常
 * 
 * @author 老码农 2018-12-07 02:17:07
 */
public class NullPointerExceptionFilter extends AbstractExceptionFilter {

	/**
	 * 
	 */
	@Override
	protected ErrorInfoResult doInternalFilter(Throwable e) {
		NullPointerException nullPointerException = getException(e, NullPointerException.class);
		if (nullPointerException != null) {
			return new ErrorInfoResult(true, nullPointerException, 0, "服务器出错(空指针异常)。", "");
		}
		return null;
	}

}
