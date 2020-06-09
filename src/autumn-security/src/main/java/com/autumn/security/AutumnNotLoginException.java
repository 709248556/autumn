package com.autumn.security;

import com.autumn.exception.ErrorLevel;

/**
 * 由于未登录而产生的异常
 * 
 * @author 老码农
 *
 *         2017-09-22 19:50:33
 */
public class AutumnNotLoginException extends AutumnAuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2184351505730439534L;

	/**
	 * 无构造实例化
	 */
	public AutumnNotLoginException() {
		super("用户未登录");
		this.setLevel(ErrorLevel.USER);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public AutumnNotLoginException(String message) {
		super(message);
		this.setLevel(ErrorLevel.USER);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public AutumnNotLoginException(String message, Throwable cause) {
		super(message, cause);
		this.setLevel(ErrorLevel.USER);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public AutumnNotLoginException(Throwable cause) {
		super("用户未登录", cause);
	}

	@Override
	public final int getCode() {
		return ApplicationErrorCode.NOT_LOGIN_ERRORCODE;
	}

	@Override
	public final void setCode(int code) {

	}
}
