package com.autumn.exception;

/**
 * 系统产生的异常
 * 
 * @author 老码农
 *
 *         2017-10-09 09:49:09
 */
public class SystemException extends AutumnException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2823114222389358086L;

	/**
	 * 无构造实例化
	 */
	public SystemException() {
		super();
		this.setCode(SystemErrorCode.SYSTEM_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public SystemException(String message) {
		super(message);
		this.setCode(SystemErrorCode.SYSTEM_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(SystemErrorCode.SYSTEM_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public SystemException(Throwable cause) {
		super(cause);
		this.setCode(SystemErrorCode.SYSTEM_ERRORCODE);
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
	protected SystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCode(SystemErrorCode.SYSTEM_ERRORCODE);
	}

	@Override
	public final ErrorLevel getLevel() {
		return ErrorLevel.SYSTEM;
	}

	@Override
	public final void setLevel(ErrorLevel level) {
	}
}
