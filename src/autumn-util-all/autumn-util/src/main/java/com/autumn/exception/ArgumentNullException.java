package com.autumn.exception;

/**
 * 参数为 null 引发的异常
 * 
 * @author 老码农
 *
 *         2017-10-09 15:19:12
 */
public class ArgumentNullException extends ArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5943025773648894502L;

	/**
	 * 无构造实例化
	 */
	public ArgumentNullException() {
		super();
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public ArgumentNullException(String message) {
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
	public ArgumentNullException(String paramName, String message) {
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
	public ArgumentNullException(String message, Throwable cause) {
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
	public ArgumentNullException(String paramName, String message, Throwable cause) {
		super(paramName, message, cause);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public ArgumentNullException(Throwable cause) {
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
	protected ArgumentNullException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public final int getCode() {
		return SystemErrorCode.ARGUMENT_NULL_ERRORCODE;
	}

	@Override
	public final void setCode(int code) {
		
	}
}
