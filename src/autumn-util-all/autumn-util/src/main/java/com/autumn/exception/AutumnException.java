package com.autumn.exception;

/**
 * 异常
 * 
 * @author 老码农
 *
 *         2017-09-22 19:26:51
 */
public class AutumnException extends RuntimeException implements AutumnError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4598505394679894431L;

	/**
	 * 无构造实例化
	 */
	public AutumnException() {
		super();
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public AutumnException(String message) {
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
	public AutumnException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public AutumnException(Throwable cause) {
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
	protected AutumnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
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

	private ErrorLevel level = ErrorLevel.NONE;

	@Override
	public ErrorLevel getLevel() {
		return this.level;
	}

	@Override
	public void setLevel(ErrorLevel level) {
		this.level = level;
	}
}
