package com.autumn.domain.repositories;

/**
 * 由于数据库出错产生的异常
 * 
 * @author 老码农
 *
 *         2017-09-27 14:42:57
 */
public class RepositoryDbException extends RepositoryException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3161987757028937711L;

	/**
	 * 无构造实例化
	 */
	public RepositoryDbException() {
		super("数据库出错");
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public RepositoryDbException(String message) {
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
	public RepositoryDbException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public RepositoryDbException(Throwable cause) {
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
	protected RepositoryDbException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
