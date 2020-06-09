package com.autumn.exception;

/**
 * 由于配置引发的异常
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-11-04 03:35:55
 */
public class ConfigureException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7445804885869734076L;

	/**
	 * 无构造实例化
	 */
	public ConfigureException() {
		super();
		this.setCode(SystemErrorCode.CONFIGURE_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public ConfigureException(String message) {
		super(message);
		this.setCode(SystemErrorCode.CONFIGURE_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public ConfigureException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(SystemErrorCode.CONFIGURE_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public ConfigureException(Throwable cause) {
		super(cause);
		this.setCode(SystemErrorCode.CONFIGURE_ERRORCODE);
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
	protected ConfigureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCode(SystemErrorCode.CONFIGURE_ERRORCODE);
	}

}
