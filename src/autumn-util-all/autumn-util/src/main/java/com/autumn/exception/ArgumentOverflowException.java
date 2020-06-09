package com.autumn.exception;

/**
 * 参数超过范围产生的异常
 * 
 * @author 老码农
 *
 *         2017-10-09 15:23:52
 */
public class ArgumentOverflowException extends ArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9025609276709834025L;

	private Object actualValue;

	/**
	 * 无构造实例化
	 */
	public ArgumentOverflowException() {
		super();
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public ArgumentOverflowException(String message) {
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
	public ArgumentOverflowException(String paramName, String message) {
		super(paramName, message);
	}

	/**
	 * 实例化
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 * @param actualValue
	 *            导致异常的参数值
	 */
	public ArgumentOverflowException(String paramName, String message, Object actualValue) {
		super(paramName, message);
		this.actualValue = actualValue;
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public ArgumentOverflowException(String message, Throwable cause) {
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
	public ArgumentOverflowException(String paramName, String message, Throwable cause) {
		super(paramName, message, cause);
	}

	/**
	 * 实例化
	 * 
	 * @param paramName
	 *            参数
	 * @param message
	 *            消息
	 * @param actualValue
	 *            导致异常的参数值
	 * @param cause
	 *            源异常
	 */
	public ArgumentOverflowException(String paramName, String message, Object actualValue, Throwable cause) {
		super(paramName, message, cause);
		this.actualValue = actualValue;
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public ArgumentOverflowException(Throwable cause) {
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
	protected ArgumentOverflowException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * 获取导致异常的参数值
	 * 
	 * @return
	 * @author 老码农 2017-10-09 15:25:55
	 */
	public Object getActualValue() {
		return actualValue;
	}
	
	@Override
	public final int getCode() {
		return SystemErrorCode.ARGUMENT_OVERFLOW_ERRORCODE;
	}

	@Override
	public final void setCode(int code) {
		
	}
}
