package com.autumn.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.annotation.FriendlyProperty;
import com.autumn.util.json.JsonUtils;

import java.io.Serializable;

/**
 * Ajax 响应抽象
 * 
 * @author 老码农
 *
 *         2017-10-31 10:43:48
 */
public abstract class AbstractApiResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7516847600393712664L;
	@FriendlyProperty(value = "是否成功")
	@JSONField(ordinal = 1)
	private boolean success;
	@JSONField(ordinal = 3)
	@FriendlyProperty(value = "异常信息")
	private ErrorInfo error;

	/**
	 * 获取是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置是否成功
	 * 
	 * @param success
	 *            成功标识
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 获取错误
	 * 
	 * @return
	 */
	public ErrorInfo getError() {
		return error;
	}

	/**
	 * 设置错误
	 * 
	 * @param error
	 *            错误信息
	 */
	public void setError(ErrorInfo error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return JsonUtils.toJSONString(this);
	}
}
