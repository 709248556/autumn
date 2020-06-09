package com.autumn.validation;

import com.autumn.exception.SystemException;

/**
 * 由于验证产生的异常
 * 
 * @author 老码农
 *
 *         2017-10-09 10:31:44
 */
public class ValidationException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2181792685858365192L;

	/**
	 * 无构造实例化
	 */
	public ValidationException() {
		super();
		this.setCode(SystemErrorCode.VALIDATION_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public ValidationException(String message) {
		super(message);
		this.setCode(SystemErrorCode.VALIDATION_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(SystemErrorCode.VALIDATION_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public ValidationException(Throwable cause) {
		super(cause);
		this.setCode(SystemErrorCode.VALIDATION_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 * @param enableSuppression
	 * @param writableStackTrace
	 *            写入跟踪
	 */
	protected ValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCode(SystemErrorCode.VALIDATION_ERRORCODE);
	}
}
