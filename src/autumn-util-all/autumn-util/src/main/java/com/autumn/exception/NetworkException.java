package com.autumn.exception;

/**
 * 由于网络而产生的异常
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-23 20:46:20
 */
public class NetworkException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 141156240900151798L;

	/**
	 * 无构造实例化
	 */
	public NetworkException() {
		super();
		this.setCode(SystemErrorCode.NETWORK_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public NetworkException(String message) {
		super(message);
		this.setCode(SystemErrorCode.NETWORK_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public NetworkException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(SystemErrorCode.NETWORK_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public NetworkException(Throwable cause) {
		super(cause);
		this.setCode(SystemErrorCode.NETWORK_ERRORCODE);
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
	protected NetworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCode(SystemErrorCode.NETWORK_ERRORCODE);
	}
}
