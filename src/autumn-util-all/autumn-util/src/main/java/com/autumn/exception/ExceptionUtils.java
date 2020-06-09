package com.autumn.exception;

import com.autumn.util.StringUtils;
import com.autumn.validation.ValidationException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常帮肋
 * 
 * @author 老码农
 *
 *         2017-10-09 16:55:44
 */
public class ExceptionUtils {

	/**
	 * 输出跟踪异常
	 * 
	 * @param e
	 *            异常
	 * @return
	 */
	public static String toStackTraceString(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		return sw.getBuffer().toString();
	}

	/**
	 * 检查非空
	 * 
	 * @param value
	 *            值
	 * @return
	 * @author 老码农 2017-10-09 16:57:30
	 */
	public static <T> T checkNotNull(T value) {
		if (value == null) {
			throw new ArgumentNullException("参数值为 null。");
		}
		return value;
	}

	/**
	 * 检查非空
	 * 
	 * @param value
	 *            值
	 * @param paramName
	 *            参数名
	 * @return
	 * @author 老码农 2017-10-09 16:57:30
	 */
	public static <T> T checkNotNull(T value, String paramName) {
		if (value == null) {
			throw new ArgumentNullException(paramName, String.format("参数 %s 的值为 null。", paramName));
		}
		return value;
	}

	/**
	 * 检查非空和非空白
	 * 
	 * @param value
	 *            值
	 * @return
	 * @author 老码农 2017-10-09 16:57:30
	 */
	public static String checkNotNullOrBlank(String value) {
		checkNotNull(value);
		if (StringUtils.isNullOrBlank(value)) {
			throw new ArgumentBlankException("参数值为空白值。");
		}
		return value;
	}

	/**
	 * 检查非空和非空白
	 * 
	 * @param value
	 *            值
	 * @return
	 * @author 老码农 2017-10-09 16:57:30
	 */
	public static String checkNotNullOrBlank(String value, String paramName) {
		checkNotNull(value, paramName);
		if (StringUtils.isNullOrBlank(value)) {
			throw new ArgumentBlankException(paramName, String.format("参数 %s 的值为空白值。", paramName));
		}
		return value;
	}

	/**
	 * 抛出 AutumnException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static AutumnException throwAutumnException(String message) {
		return throwAutumnException(message, null);
	}

	/**
	 * 抛出 AutumnException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static AutumnException throwAutumnException(String message, Throwable cause) {
		throw new AutumnException(message, cause);
	}

	/**
	 * 抛出 AutumnException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @param errorLevel
	 *            错误级别
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static AutumnException throwAutumnException(String message, int errorCode, ErrorLevel errorLevel) {
		return throwAutumnException(message, errorCode, errorLevel, null);
	}

	/**
	 * 抛出 AutumnException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @param errorLevel
	 *            错误级别
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static AutumnException throwAutumnException(String message, int errorCode, ErrorLevel errorLevel,
			Throwable cause) {
		AutumnException exception = new AutumnException(message, cause);
		exception.setCode(errorCode);
		exception.setLevel(errorLevel);
		throw exception;
	}

	/**
	 * 抛出 UserFriendlyException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static UserFriendlyException throwUserFriendlyException(String message) {
		return throwUserFriendlyException(message, null);
	}

	/**
	 * 抛出 UserFriendlyException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static UserFriendlyException throwUserFriendlyException(String message, Throwable cause) {
		throw new UserFriendlyException(message, cause);
	}

	/**
	 * 抛出 UserFriendlyException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static UserFriendlyException throwUserFriendlyException(String message, int errorCode) {
		return throwUserFriendlyException(message, errorCode, null);
	}

	/**
	 * 抛出 UserFriendlyException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static UserFriendlyException throwUserFriendlyException(String message, int errorCode, Throwable cause) {
		UserFriendlyException exception = new UserFriendlyException(message, cause);
		exception.setCode(errorCode);
		throw exception;
	}

	/**
	 * 抛出 ApplicationException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static ApplicationException throwApplicationException(String message) {
		return throwApplicationException(message, null);
	}

	/**
	 * 抛出 ApplicationException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static ApplicationException throwApplicationException(String message, Throwable cause) {
		throw new ApplicationException(message, cause);
	}

	/**
	 * 抛出 ApplicationException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static ApplicationException throwApplicationException(String message, int errorCode) {
		return throwApplicationException(message, errorCode, null);
	}

	/**
	 * 抛出 ApplicationException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static ApplicationException throwApplicationException(String message, int errorCode, Throwable cause) {
		ApplicationException exception = new ApplicationException(message, cause);
		exception.setCode(errorCode);
		throw exception;
	}

	/**
	 * 抛出 SystemException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static SystemException throwSystemException(String message) {
		return throwSystemException(message, null);
	}

	/**
	 * 抛出 SystemException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static SystemException throwSystemException(String message, Throwable cause) {
		throw new ApplicationException(message, cause);
	}

	/**
	 * 抛出 SystemException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static SystemException throwSystemException(String message, int errorCode) {
		return throwSystemException(message, errorCode, null);
	}

	/**
	 * 抛出 SystemException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static SystemException throwSystemException(String message, int errorCode, Throwable cause) {
		ApplicationException exception = new ApplicationException(message, cause);
		exception.setCode(errorCode);
		throw exception;
	}

	/**
	 * 抛出 NotSupportException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static NotSupportException throwNotSupportException(String message) {
		return throwNotSupportException(message, null);
	}

	/**
	 * 抛出 NotSupportException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static NotSupportException throwNotSupportException(String message, Throwable cause) {
		throw new NotSupportException(message, cause);
	}

	/**
	 * 抛出 InvalidCastException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static InvalidCastException throwInvalidCastException(String message) {
		return throwInvalidCastException(message, null);
	}

	/**
	 * 抛出 InvalidCastException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 18:00:44
	 */
	public static InvalidCastException throwInvalidCastException(String message, Throwable cause) {
		throw new InvalidCastException(message, cause);
	}

	/**
	 * 抛出 OverflowException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static OverflowException throwOverflowException(String message) {
		return throwOverflowException(message, null);
	}

	/**
	 * 抛出 OverflowException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 18:00:44
	 */
	public static OverflowException throwOverflowException(String message, Throwable cause) {
		throw new OverflowException(message, cause);
	}

	/**
	 * 抛出 FormatException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static FormatException throwFormatException(String message) {
		return throwFormatException(message, null);
	}

	/**
	 * 抛出 FormatException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 18:00:44
	 */
	public static FormatException throwFormatException(String message, Throwable cause) {
		throw new FormatException(message, cause);
	}

	/**
	 * 抛出 SignException 异常
	 * 
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static SignException throwSingException() {
		return throwSingException("签名不正确");
	}

	/**
	 * 抛出 SignException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static SignException throwSingException(String message) {
		return throwSingException(message, null);
	}

	/**
	 * 抛出 SignException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static SignException throwSingException(String message, Throwable cause) {
		throw new SignException(message, cause);
	}

	/**
	 * 抛出 ValidationException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static ValidationException throwValidationException(String message) {
		return throwValidationException(message, null);
	}

	/**
	 * 抛出 ValidationException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 18:00:44
	 */
	public static ValidationException throwValidationException(String message, Throwable cause) {
		throw new ValidationException(message, cause);
	}

	/**
	 * 抛出 ArgumentException 异常
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 18:18:25
	 */
	public static ArgumentException throwArgumentException(String paramName, String message) {
		return throwArgumentException(paramName, message, null);
	}

	/**
	 * 抛出 ArgumentException 异常
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 18:16:51
	 */
	public static ArgumentException throwArgumentException(String paramName, String message, Throwable cause) {
		throw new ArgumentException(paramName, message, cause);
	}

	/**
	 * 抛出 ArgumentNullException 异常
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 18:18:25
	 */
	public static ArgumentNullException throwArgumentNullException(String paramName, String message) {
		return throwArgumentNullException(paramName, message, null);
	}

	/**
	 * 抛出 ArgumentNullException 异常
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 18:16:51
	 */
	public static ArgumentNullException throwArgumentNullException(String paramName, String message, Throwable cause) {
		throw new ArgumentNullException(paramName, message, cause);
	}

	/**
	 * 抛出 ArgumentBlankException 异常
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 18:18:25
	 */
	public static ArgumentBlankException throwArgumentBlankException(String paramName, String message) {
		return throwArgumentBlankException(paramName, message, null);
	}

	/**
	 * 抛出 ArgumentBlankException 异常
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 18:16:51
	 */
	public static ArgumentBlankException throwArgumentBlankException(String paramName, String message,
			Throwable cause) {
		throw new ArgumentBlankException(paramName, message, cause);
	}

	/**
	 * 抛出 ArgumentOverflowException 异常
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 18:18:25
	 */
	public static ArgumentOverflowException throwArgumentOverflowException(String paramName, String message) {
		return throwArgumentOverflowException(paramName, message, null);
	}

	/**
	 * 抛出 ArgumentOverflowException 异常
	 * 
	 * @param paramName
	 *            参数名称
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 18:16:51
	 */
	public static ArgumentOverflowException throwArgumentOverflowException(String paramName, String message,
			Throwable cause) {
		throw new ArgumentOverflowException(paramName, message, cause);
	}

	/**
	 * 抛出 SystemException 异常
	 * 
	 * @param message
	 *            消息
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static ConfigureException throwConfigureException(String message) {
		return throwConfigureException(message, null);
	}

	/**
	 * 抛出 ConfigureException 异常
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:47:24
	 */
	public static ConfigureException throwConfigureException(String message, Throwable cause) {
		throw new ConfigureException(message, cause);
	}

	/**
	 * 抛出 ConfigureException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static ConfigureException throwConfigureException(String message, int errorCode) {
		return throwConfigureException(message, errorCode, null);
	}

	/**
	 * 抛出 ConfigureException 异常
	 * 
	 * @param message
	 *            消息
	 * @param errorCode
	 *            错误代码
	 * @param cause
	 *            源
	 * @return
	 * @author 老码农 2017-10-09 17:51:44
	 */
	public static ConfigureException throwConfigureException(String message, int errorCode, Throwable cause) {
		ConfigureException exception = new ConfigureException(message, cause);
		exception.setCode(errorCode);
		throw exception;
	}

}
