package com.autumn.security;

import com.autumn.exception.AutumnError;

/**
 * 无效票据异常
 * 
 * @author 老码农
 *
 *         2017-11-05 16:13:12
 */
public class AutumnInvalidTokenException extends AutumnAuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3285634349720933454L;

	/**
	 * 无构造实例化
	 */
	public AutumnInvalidTokenException() {
		super("非法的 token 。");
		this.setCode(AutumnError.ApplicationErrorCode.TOKEN_INVALID_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message 消息
	 */
	public AutumnInvalidTokenException(String message) {
		super(message);
		this.setCode(AutumnError.ApplicationErrorCode.TOKEN_INVALID_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message 消息
	 * @param cause   源异常
	 */
	public AutumnInvalidTokenException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(AutumnError.ApplicationErrorCode.TOKEN_INVALID_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause 源异常
	 */
	public AutumnInvalidTokenException(Throwable cause) {
		super("非法的 token 。", cause);
		this.setCode(AutumnError.ApplicationErrorCode.TOKEN_INVALID_ERRORCODE);
	}
}
