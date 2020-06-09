package com.autumn.security;

import com.autumn.exception.AutumnError;

/**
 * 
 * 无效的提供程序
 * 
 * @author 老码农 2018-04-09 20:17:06
 */
public class AutumnProviderNotFoundException extends AutumnAuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8622449957918374632L;

	public AutumnProviderNotFoundException(String msg) {
		super(msg);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_CREDENTIALS_ERRORCODE);
	}

	/**
	 * Constructs a {@code ProviderNotFoundException} with the specified message and
	 * root cause.
	 *
	 * @param msg the detail message.
	 * @param t   root cause
	 */
	public AutumnProviderNotFoundException(String msg, Throwable t) {
		super(msg, t);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_CREDENTIALS_ERRORCODE);
	}

}
