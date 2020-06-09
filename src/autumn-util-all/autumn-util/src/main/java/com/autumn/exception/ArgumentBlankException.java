package com.autumn.exception;

/**
 * 参数空白引发的异常
 * 
 * @author 老码农
 *
 *         2017-10-09 15:33:13
 */
public class ArgumentBlankException extends ArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8079640731892709920L;

	/**
	 * 无构造实例化
	 */
	public ArgumentBlankException() {
		super();
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public ArgumentBlankException(String message) {
		super(message);
	}

	/**
	 * 实例化
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 */
	public ArgumentBlankException(String paramName, String message) {
		super(paramName, message);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public ArgumentBlankException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 实例化
	 * 
	 * @param paramName
	 *            参数
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public ArgumentBlankException(String paramName, String message, Throwable cause) {
		super(paramName, message, cause);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public ArgumentBlankException(Throwable cause) {
		super(cause);
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
	protected ArgumentBlankException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public final int getCode() {
		return SystemErrorCode.ARGUMENT_BLANK_ERRORCODE;
	}

	@Override
	public final void setCode(int code) {

	}
}
