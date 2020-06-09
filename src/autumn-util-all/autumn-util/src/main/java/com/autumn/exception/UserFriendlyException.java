package com.autumn.exception;

/**
 * 用户友好异常
 * 
 * @author 老码农
 *
 *         2017-10-09 10:05:07
 */
public class UserFriendlyException extends AutumnException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8756660714668218046L;

	/**
	 * 无构造实例化
	 */
	public UserFriendlyException() {
		super();
		this.setCode(UserFriendlyErrorCode.USERFRIENDLY_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public UserFriendlyException(String message) {
		super(message);
		this.setCode(UserFriendlyErrorCode.USERFRIENDLY_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源异常
	 */
	public UserFriendlyException(String message, Throwable cause) {
		super(message, cause);
		this.setCode(UserFriendlyErrorCode.USERFRIENDLY_ERRORCODE);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public UserFriendlyException(Throwable cause) {
		super(cause);
		this.setCode(UserFriendlyErrorCode.USERFRIENDLY_ERRORCODE);
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
	protected UserFriendlyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCode(UserFriendlyErrorCode.USERFRIENDLY_ERRORCODE);
	}

	@Override
	public final ErrorLevel getLevel() {
		return ErrorLevel.USER;
	}

	@Override
	public final void setLevel(ErrorLevel level) {
	}

}
