package com.autumn.security;

import com.autumn.exception.ErrorLevel;
import com.autumn.exception.AutumnError;

/**
 * 账户状态异常
 * <p>
 * 不能修改继承
 * </p>
 * 
 * @author 老码农 2018-04-13 18:04:10
 */
public class AutumnAccountStatusException extends AutumnAuthenticationException
		implements AutumnError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6277015584797811830L;

	public AutumnAccountStatusException(String msg) {
		super(msg);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_STATUS_ERRORCODE);
		this.setLevel(ErrorLevel.APPLICATION);
	}

	/**
	 * Constructs a {@code AccountStatusException} with the specified message and
	 * root cause.
	 *
	 * @param msg
	 *            the detail message.
	 * @param t
	 *            root cause
	 */
	public AutumnAccountStatusException(String msg, Throwable t) {
		super(msg, t);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_STATUS_ERRORCODE);
		this.setLevel(ErrorLevel.APPLICATION);
	}	
}
