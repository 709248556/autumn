package com.autumn.runtime.exception.filter;

import com.autumn.exception.AutumnError;
import com.autumn.web.vo.ErrorInfoResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

/**
 * 验证绑定异常过滤
 * 
 * @author 老码农 2018-12-07 02:13:40
 */
public class ValidationBindExceptionFilter extends AbstractExceptionFilter {

	@Override
	protected ErrorInfoResult doInternalFilter(Throwable e) {
		BindException bindException = getException(e, BindException.class);
		if (bindException != null) {
			FieldError fieldErr = bindException.getBindingResult().getFieldError();
			return new ErrorInfoResult(false, bindException, AutumnError.SystemErrorCode.ARGUMENT_ERRORCODE,
					fieldErr.getDefaultMessage(), "");
		}
		return null;
	}

}
