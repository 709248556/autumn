package com.autumn.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.annotation.FriendlyProperty;

/**
 * 
 * @author 老码农
 *
 *         2017-10-31 10:49:58
 */
public class ApiResponse<TResult> extends AbstractApiResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8760962482040802320L;

	@FriendlyProperty(value = "结果")
	@JSONField(ordinal = 2)
	private TResult result;

	/**
	 * 获取结果
	 * 
	 * @return
	 */
	public TResult getResult() {
		return result;
	}

	/**
	 * 设置结果
	 * 
	 * @param result
	 *            结果
	 */
	public void setResult(TResult result) {
		this.result = result;
	}
}
