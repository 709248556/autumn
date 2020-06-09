package com.autumn.spring.boot.context;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;

/**
 * Autumn 应用上下文
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-09 19:38:55
 */
public class AutumnApplicationContext implements DisposableBean {

	private volatile static ApplicationContext context = null;

	/**
	 * 
	 * @param applicationContext
	 */
	public AutumnApplicationContext(ApplicationContext applicationContext) {
		AutumnApplicationContext.context = applicationContext;
	}

	/**
	 * 获取上下文
	 * 
	 * @return 2017-12-07 12:38:43
	 */
	public static ApplicationContext getContext() {
		return AutumnApplicationContext.context;
	}

	/**
	 * 获取版本
	 * 
	 * @return 返回版本,默认 v3.0
	 */
	public static String getVersion() {
		Package pkg = AutumnApplicationContext.class.getPackage();
		return (pkg != null ? pkg.getImplementationVersion() : "v3.0");
	}

	@Override
	public synchronized void destroy() throws Exception {
		AutumnApplicationContext.context = null;
	}
}
