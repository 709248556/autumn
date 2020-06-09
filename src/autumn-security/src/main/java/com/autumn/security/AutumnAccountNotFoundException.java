package com.autumn.security;

import com.autumn.exception.AutumnError;

/**
 * 账号不存在而引发的异常
 * 
 * @author 老码农 2018-04-09 20:24:46
 */
public class AutumnAccountNotFoundException extends AutumnAuthenticationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4933192343739528972L;

	public AutumnAccountNotFoundException(String msg) {
		super(msg);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_INVALID_ERRORCODE);
	}

	/**
	 * Constructs a {@code AccountNotFoundException} with the specified message and
	 * root cause.
	 *
	 * @param msg
	 *            the detail message.
	 * @param t
	 *            root cause
	 */
	public AutumnAccountNotFoundException(String msg, Throwable t) {
		super(msg, t);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_INVALID_ERRORCODE);
	}
}
