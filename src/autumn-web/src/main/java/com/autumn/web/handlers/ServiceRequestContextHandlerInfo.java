package com.autumn.web.handlers;

import org.springframework.web.method.HandlerMethod;

/**
 * 配置服务请求处理信息
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-08 02:28:53
 */
class ServiceRequestContextHandlerInfo {

	private final String headerParameterName;
	private final boolean required;
	private final HandlerMethod handlerMethod;

	public ServiceRequestContextHandlerInfo(String headerParameterName, boolean required, HandlerMethod handlerMethod) {
		super();
		this.headerParameterName = headerParameterName;
		this.required = required;
		this.handlerMethod = handlerMethod;
	}

	/**
	 * 获取头参数名称
	 * 
	 * @return
	 */
	public String getHeaderParameterName() {
		return headerParameterName;
	}

	/**
	 * 获取处理函数
	 * 
	 * @return
	 */
	public HandlerMethod getHandlerMethod() {
		return handlerMethod;
	}

	public boolean isRequired() {
		return required;
	}

}
