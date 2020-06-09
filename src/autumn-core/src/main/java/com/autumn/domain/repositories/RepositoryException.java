package com.autumn.domain.repositories;

import com.autumn.exception.SystemException;

/**
 * 由于仓储产生的异常
 * 
 * @author 老码农
 *
 *         2017-09-27 14:41:07
 */
public class RepositoryException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6191601374174381000L;

	/**
	 * 无构造实例化
	 */
	public RepositoryException() {
		super("仓储出错");
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public RepositoryException(String message) {
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
	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public RepositoryException(Throwable cause) {
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
	protected RepositoryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
