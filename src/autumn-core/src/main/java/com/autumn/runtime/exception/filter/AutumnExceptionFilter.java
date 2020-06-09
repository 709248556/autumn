package com.autumn.runtime.exception.filter;


import com.autumn.exception.AutumnError;
import com.autumn.exception.AutumnException;
import com.autumn.exception.ErrorLevel;
import com.autumn.web.vo.ErrorInfoResult;

/**
 * 基于 AutumnException 而产生的异常过滤
 * 
 * @author 老码农 2018-12-07 02:02:52
 */
public class AutumnExceptionFilter extends AbstractExceptionFilter {

	@Override
	protected ErrorInfoResult doInternalFilter(Throwable e) {
		AutumnException autumnException = this.getException(e, AutumnException.class);
		if (autumnException != null) {
			return new ErrorInfoResult(!ErrorLevel.USER.equals(autumnException.getLevel()), e,
					autumnException.getCode(), autumnException.getMessage(), "");
		}
		if (e instanceof AutumnError) {
			AutumnError autumnError = (AutumnError) e;
			return new ErrorInfoResult(!ErrorLevel.USER.equals(autumnError.getLevel()), e, autumnError.getCode(),
					e.getMessage(), "");

		}
		return null;
	}

}
