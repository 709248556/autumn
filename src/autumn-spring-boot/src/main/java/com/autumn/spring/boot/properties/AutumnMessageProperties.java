package com.autumn.spring.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息属性
 * 
 * @author 老码农
 *
 *         2018-06-29 17:57:05
 */
@ConfigurationProperties(prefix = AutumnMessageProperties.PREFIX)
public class AutumnMessageProperties extends AbstractAutumnProperties {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4867678658593104276L;

	/**
	 * 属性前缀
	 */
	public final static String PREFIX = "autumn.message";
	
	
}
