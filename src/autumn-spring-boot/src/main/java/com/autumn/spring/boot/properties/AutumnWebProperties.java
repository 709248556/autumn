package com.autumn.spring.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Web属性
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-18 21:28:41
 */
@SuppressWarnings("serial")
@ConfigurationProperties(prefix = AutumnWebProperties.PREFIX)
public class AutumnWebProperties extends AbstractAutumnProperties {

	/**
	 * 属性前缀
	 */
	public final static String PREFIX = "autumn.web";

	/**
	 * Web发布类型键
	 */
	public final static String PUBLISH_TYPE_KEY = PREFIX + ".publish-type";

	/**
	 * Web发布类型属性
	 */
	public final static String PUBLISH_TYPE_PROPERTIES = PREFIX + ".publishType";

	/**
	 * 调试
	 */
	public final static String PUBLISH_TYPE_DEBUG = "DEBUG";

	/**
	 * 发布
	 */
	public final static String PUBLISH_TYPE_RELEASE = "RELEASE";

	/**
	 * Web发布类型为调试条件表达式
	 */
	public final static String WEB_PUBLISH_TYPE_DEBUG = "'${" + PUBLISH_TYPE_KEY + "}' != '" + PUBLISH_TYPE_RELEASE
			+ "'";

	/**
	 * 获取是否是发布
	 * 
	 * @return
	 */
	public boolean isRelease() {
		String value = this.getPublishType();
		if (value == null) {
			return false;
		}
		return PUBLISH_TYPE_RELEASE.equalsIgnoreCase(value.trim());
	}

	private String publishType = "DEBUG";

	/**
	 * 获取发布类型
	 * 
	 * @return
	 */
	public String getPublishType() {
		return publishType;
	}

	/**
	 * 设置发布类型
	 * 
	 * @param publishType
	 *            发布类型
	 */
	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}
}
