package com.autumn.exception;

/**
 * 由于格式出错引发的异常
 * 
 * @author 老码农
 *
 *         2017-09-29 17:26:51
 */
public class FormatException extends SystemException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1258222969966119189L;

	/**
	 * 无构造实例化
	 */
	public FormatException() {
		super("格式不正确");
		this.setCode(SystemErrorCode.FORMAT_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public FormatException(String message) {
		super(message);
		this.setCode(SystemErrorCode.FORMAT_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public FormatException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(SystemErrorCode.FORMAT_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public FormatException(Throwable cause) {
		super(cause);
		this.setCode(SystemErrorCode.FORMAT_ERRORCODE);
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
	protected FormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCode(SystemErrorCode.FORMAT_ERRORCODE);
	}
}
