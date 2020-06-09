package com.autumn.exception;

/**
 * 由于参数引发的异常
 * 
 * @author 老码农
 *
 *         2017-10-09 13:58:24
 */
public class ArgumentException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7905829096302186485L;

	private String paramName;

	/**
	 * 无构造实例化
	 */
	public ArgumentException() {
		super();
		this.setCode(SystemErrorCode.ARGUMENT_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public ArgumentException(String message) {
		super(message);
		this.setCode(SystemErrorCode.ARGUMENT_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 */
	public ArgumentException(String paramName, String message) {
		super(message);
		this.paramName = paramName;
		this.setCode(SystemErrorCode.ARGUMENT_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public ArgumentException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(SystemErrorCode.ARGUMENT_ERRORCODE);
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
	public ArgumentException(String paramName, String message, Throwable cause) {
		super(message, cause);
		this.paramName = paramName;
		this.setCode(SystemErrorCode.ARGUMENT_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public ArgumentException(Throwable cause) {
		super(cause);
		this.setCode(SystemErrorCode.ARGUMENT_ERRORCODE);
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
	protected ArgumentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCode(SystemErrorCode.ARGUMENT_ERRORCODE);
	}

	/**
	 * 获取参数名称
	 * 
	 * @return
	 * @author 老码农 2017-10-09 13:59:55
	 */
	public String getParamName() {
		return paramName;
	}

}
