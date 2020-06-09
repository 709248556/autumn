package com.autumn.mybatis.mapper;

import com.autumn.exception.AutumnError;
import com.autumn.exception.SystemException;

/**
 * 映射异常
 * 
 * @author 老码农
 *
 *         2017-09-27 18:12:12
 */
public class MapperException extends SystemException {

	/**
	 * 映射产生的异常
	 */
	public static final int MAPPER_ERRORCODE = AutumnError.SystemErrorCode.SYSTEM_ERRORCODE + 5000;

	/**
	 * 
	 */
	private static final long serialVersionUID = -9048689820047759795L;

	/**
	 * 无构造实例化
	 */
	public MapperException() {
		super("映射出错");
	}

	/**
	 * 实例化
	 * 
	 * @param message
	 *            消息
	 */
	public MapperException(String message) {
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
	public MapperException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 实例化
	 * 
	 * @param cause
	 *            源异常
	 */
	public MapperException(Throwable cause) {
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
	protected MapperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public final int getCode() {
		return MAPPER_ERRORCODE;
	}

	@Override
	public final void setCode(int code) {

	}
}
