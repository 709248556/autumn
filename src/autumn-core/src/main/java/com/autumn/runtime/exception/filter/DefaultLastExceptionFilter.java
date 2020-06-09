package com.autumn.runtime.exception.filter;

import com.autumn.exception.AutumnError;
import com.autumn.exception.ErrorLevel;
import com.autumn.web.vo.ErrorInfoResult;
import org.springframework.core.annotation.Order;

/**
 * 默认最后异常过滤
 * 
 * @author 老码农 2018-12-07 02:25:06
 */
@Order(Integer.MAX_VALUE)
public class DefaultLastExceptionFilter extends AbstractExceptionFilter {

	@Override
	protected ErrorInfoResult doInternalFilter(Throwable e) {
		if (e instanceof AutumnError) {
			AutumnError autumnError = (AutumnError) e;
			return new ErrorInfoResult(!ErrorLevel.USER.equals(autumnError.getLevel()), e, autumnError.getCode(),
					e.getMessage(), "");

		}
		return new ErrorInfoResult(true, e, 0, e.getMessage(), "");
	}

}
