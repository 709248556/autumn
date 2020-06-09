package com.autumn.exception;

/**
 * 由于不支持而产生的异常
 * 
 * @author 老码农
 *
 *         2017-09-22 19:44:40
 */
public class NotSupportException extends SystemException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9168134619907037458L;

	/**
	 * 无构造实例化
	 */
	public NotSupportException() {
		super("不支持的操作");
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public NotSupportException(String message) {
		super(message);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public NotSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public NotSupportException(Throwable cause) {
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
	protected NotSupportException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public final int getCode() {		
		return SystemErrorCode.NOT_SUPPORT_ERRORCODE;
	}

	@Override
	public final void setCode(int code) {
		
	}	
	
}
