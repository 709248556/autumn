package com.autumn.audited;

/**
 * 
 * @author 老码农
 *
 * 2017-10-20 13:45:07
 */
public interface ClientBrowserInfo {
	/**
	 * 获取浏览器信息
	 * 
	 * @return
	 */
	String getBrowserInfo();

	/**
	 * 获取浏览器名称
	 * 
	 * @return
	 */
	String getBrowserName();
	
	/**
	 * 获取浏览器对应的平台
	 * 
	 * @return
	 */
	String getBrowserPlatform();
}
