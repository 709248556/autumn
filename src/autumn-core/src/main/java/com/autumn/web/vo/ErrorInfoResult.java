package com.autumn.web.vo;

import com.autumn.annotation.FriendlyProperty;

/**
 * 异常信息结果
 * 
 * @author 老码农
 *
 *         2017-12-07 10:33:25
 */
public class ErrorInfoResult {

	@FriendlyProperty(value = "是否写日志")
	private final boolean isWriteLog;
	@FriendlyProperty(value = "错误信息")
	private final ErrorInfo errorInfo;
	@FriendlyProperty(value = "异常")
	private final Throwable exception;

	/**
	 * 
	 * @param isWriteLog
	 * @param exception
	 * @param errorInfo
	 */
	public ErrorInfoResult(boolean isWriteLog, Throwable exception, ErrorInfo errorInfo) {
		this.isWriteLog = isWriteLog;
		this.errorInfo = errorInfo;
		this.exception = exception;
	}

	/**
	 * 
	 * @param isWriteLog
	 * @param exception
	 * @param code
	 * @param message
	 * @param details
	 */
	public ErrorInfoResult(boolean isWriteLog, Throwable exception, int code, String message, String details) {
		this(isWriteLog, exception, new ErrorInfo(code, message, details));
	}

	/**
	 * 获取是否写日志
	 * 
	 * @return 2017-12-07 10:35:22
	 */
	public boolean isWriteLog() {
		return isWriteLog;
	}

	/**
	 * 获取异常信息
	 * 
	 * @return 2017-12-07 10:35:44
	 */
	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	/**
	 * 获取异常
	 * 
	 * @return 2017-12-07 10:56:12
	 */
	public Throwable getException() {
		return exception;
	}
}
