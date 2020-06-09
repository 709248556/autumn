package com.autumn.spring.boot.context;

import com.autumn.util.AutumnContextUtils;

/**
 * 上下文环境
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-18 21:52:01
 */
public class AutumnContextEnvironment {

	/**
	 * 上下文环境前缀
	 */
	public final static String CONTEXT_ENVIRONMENT_PREFIX = "autumn.environment";

	/**
	 * 上下文是否启用 Swagger
	 */
	public final static String CONTEXT_ENVIRONMENT_ENABLE_SWAGGER = CONTEXT_ENVIRONMENT_PREFIX + ".enable.swagger";

	/**
	 * 实例
	 */
	public final static AutumnContextEnvironment INSTANCE = new AutumnContextEnvironment();

	/**
	 * 
	 */
	private AutumnContextEnvironment() {

	}

	private boolean enableSwagger = false;

	/**
	 * 获取是否启动 Swagger
	 * 
	 * @return
	 */
	public boolean isEnableSwagger() {
		return enableSwagger;
	}

	/**
	 * 设置是否启用 Swagger
	 * 
	 * @param enableSwagger
	 *            是否启用 Swagger
	 */
	public void setEnableSwagger(boolean enableSwagger) {
		this.enableSwagger = enableSwagger;
		AutumnContextUtils.setProperty(CONTEXT_ENVIRONMENT_ENABLE_SWAGGER, enableSwagger);
	}

}
