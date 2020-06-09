package com.autumn.security;

import org.apache.shiro.authc.AuthenticationException;

import com.autumn.exception.ErrorLevel;
import com.autumn.exception.AutumnError;

/**
 * 由于权限而产生的异常
 * 
 * @author 老码农
 *
 *         2017-09-22 19:51:19
 */
public class AutumnAuthenticationException extends AuthenticationException implements AutumnError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8778454101718918271L;

	/**
	 * 无构造实例化
	 */
	public AutumnAuthenticationException() {
		super("没有权限或权限不足");
		this.setCode(ApplicationErrorCode.AUTHORIZATION_ERRORCODE);
		this.setLevel(ErrorLevel.APPLICATION);
	}

	/**
	 * 实例化
	 * 
	 * @param message 消息
	 */
	public AutumnAuthenticationException(String message) {
		super(message);
		this.setCode(ApplicationErrorCode.AUTHORIZATION_ERRORCODE);
		this.setLevel(ErrorLevel.APPLICATION);
	}

	/**
	 * 实例化
	 * 
	 * @param message 消息
	 * @param cause   源异常
	 */
	public AutumnAuthenticationException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(ApplicationErrorCode.AUTHORIZATION_ERRORCODE);
		this.setLevel(ErrorLevel.APPLICATION);
	}

	private int code;

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public void setCode(int code) {
		this.code = code;
	}

	private ErrorLevel level;

	@Override
	public ErrorLevel getLevel() {
		return this.level;
	}

	@Override
	public void setLevel(ErrorLevel level) {
		this.level = level;
	}

}
