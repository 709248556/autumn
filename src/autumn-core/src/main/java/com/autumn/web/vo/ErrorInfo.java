package com.autumn.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;

/**
 * 错误信息
 * 
 * @author 老码农
 *
 *         2017-10-31 10:37:22
 */
public class ErrorInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4111395405547168456L;

	@FriendlyProperty(value = "错误代码")
	@JSONField(ordinal = 1)
	private int code;
	@JSONField(ordinal = 2)
	@FriendlyProperty(value = "错误消息")
	private String message;
	@JSONField(ordinal = 3)
	@FriendlyProperty(value = "错误消息详情")
	private String details;

	public ErrorInfo() {

	}

	public ErrorInfo(int code, String message, String details) {
		this.code = code;
		this.message = message;
		this.details = details;
	}

	/**
	 * 获取错误代码
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 设置错误代码
	 * 
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 获取错误消息
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置错误消息
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取错误详情
	 * 
	 * @return
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * 设置错误详情
	 * 
	 * @param details
	 */
	public void setDetails(String details) {
		this.details = details;
	}
}
