package com.autumn.exception;

/**
 * 异常级别
 * 
 * @author 老码农
 *
 *         2017-10-09 10:10:17
 */
public enum ErrorLevel {

	/**
	 * 未知
	 */
	NONE(0),
	/**
	 * 用户级别
	 */
	USER(1),
	/**
	 * 应用级别
	 */
	APPLICATION(2),
	/**
	 * 系统级别
	 */
	SYSTEM(3);

	private final int code;

	/**
	 * 实例化
	 * 
	 * @param code
	 */
	private ErrorLevel(int code) {
		this.code = code;
	}

	/**
	 * 获取代码
	 * 
	 * @return
	 * @author 老码农 2017-10-09 10:15:40
	 */
	public int getCode() {
		return code;
	}

}
