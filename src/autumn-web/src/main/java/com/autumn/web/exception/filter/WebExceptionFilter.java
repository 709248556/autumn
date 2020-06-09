package com.autumn.web.exception.filter;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.autumn.web.vo.ErrorInfoResult;
import com.autumn.runtime.exception.filter.AbstractExceptionFilter;
import com.autumn.exception.AutumnError;

/**
 * Web 产生的异常过滤
 * 
 * @author 老码农 2018-12-07 02:51:10
 */
public class WebExceptionFilter extends AbstractExceptionFilter {

	/**
	 * 
	 */
	@Override
	protected ErrorInfoResult doInternalFilter(Throwable e) {
		MethodArgumentNotValidException methodArgumentNotValidException = getException(e,
				MethodArgumentNotValidException.class);
		if (methodArgumentNotValidException != null) {
			FieldError fieldErr = methodArgumentNotValidException.getBindingResult().getFieldError();
			return new ErrorInfoResult(false, methodArgumentNotValidException,
					AutumnError.SystemErrorCode.ARGUMENT_ERRORCODE, fieldErr.getDefaultMessage(), "");
		}
		NoHandlerFoundException noHandlerFoundException = getException(e, NoHandlerFoundException.class);
		if (noHandlerFoundException != null) {
			return new ErrorInfoResult(false, noHandlerFoundException, HttpStatus.NOT_FOUND.value(), e.getMessage(),
					"");
		}
		return null;
	}

}
