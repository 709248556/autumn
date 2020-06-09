package com.autumn.security;

import com.autumn.exception.AutumnError;

/**
 * 账户认证异常
 * 
 * @author 老码农 2018-04-13 18:12:08
 */
public class AutumnAccountCredentialsException extends AutumnAuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3728665939818746790L;

	public AutumnAccountCredentialsException(String msg) {
		super(msg);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_CREDENTIALS_ERRORCODE);
	}

	/**
	 * Constructs a {@code AccountCredentialsException} with the specified message
	 * and root cause.
	 *
	 * @param msg the detail message.
	 * @param t   root cause
	 */
	public AutumnAccountCredentialsException(String msg, Throwable t) {
		super(msg, t);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_CREDENTIALS_ERRORCODE);
	}
}
