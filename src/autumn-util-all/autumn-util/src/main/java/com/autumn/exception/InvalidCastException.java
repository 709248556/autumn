package com.autumn.exception;

/**
 * 无效的转换引发的异常
 * 
 * @author 老码农
 *
 *         2017-09-29 16:17:37
 */
public class InvalidCastException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4231745397024831642L;

	/**
	 * 无构造实例化
	 */
	public InvalidCastException() {
		super();
		this.setCode(SystemErrorCode.INVALIDCAST_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public InvalidCastException(String message) {
		super(message);
		this.setCode(SystemErrorCode.INVALIDCAST_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public InvalidCastException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(SystemErrorCode.INVALIDCAST_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public InvalidCastException(Throwable cause) {
		super(cause);
		this.setCode(SystemErrorCode.INVALIDCAST_ERRORCODE);
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
	protected InvalidCastException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
