package com.autumn.exception;

/**
 * 由于签名不正确产生的异常
 * 
 * @author 老码农
 *
 *         2017-12-14 11:00:08
 */
public class SignException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7902096688371118524L;

	/**
	 * 无构造实例化
	 */
	public SignException() {
		super();
		this.setCode(SystemErrorCode.SIGN_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public SignException(String message) {
		super(message);
		this.setCode(SystemErrorCode.SIGN_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public SignException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(SystemErrorCode.SIGN_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public SignException(Throwable cause) {
		super(cause);
		this.setCode(SystemErrorCode.SIGN_ERRORCODE);
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
	protected SignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCode(SystemErrorCode.SIGN_ERRORCODE);
	}
}
