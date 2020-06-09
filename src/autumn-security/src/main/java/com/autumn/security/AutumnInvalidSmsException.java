package com.autumn.security;

import com.autumn.exception.AutumnError;

/**
 * 
 * 由于短信验证码无效而产生的异常
 * 
 * @author 老码农 2018-12-06 10:59:09
 */
public class AutumnInvalidSmsException extends AutumnAuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8164538366015333682L;

	/**
	 * 无构造实例化
	 */
	public AutumnInvalidSmsException() {
		super("无效的短信验证码。");
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_CREDENTIALS_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message 消息
	 */
	public AutumnInvalidSmsException(String message) {
		super(message);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_CREDENTIALS_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message 消息
	 * @param cause   源异常
	 */
	public AutumnInvalidSmsException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(AutumnError.ApplicationErrorCode.ACCOUNT_CREDENTIALS_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause 源异常
	 */
	public AutumnInvalidSmsException(Throwable cause) {
		super("无效的短信验证码。", cause);
		this.setCode(AutumnError.ApplicationErrorCode.TOKEN_INVALID_ERRORCODE);
	}
}
