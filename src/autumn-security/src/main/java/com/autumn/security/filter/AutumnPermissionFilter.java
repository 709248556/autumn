package com.autumn.security.filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;

/**
 * 权限过滤
 * 
 * @author 老码农 2018-12-12 11:54:59
 */
public interface AutumnPermissionFilter {

	/**
	 * 初始化过滤器
	 * 
	 * @param factoryBean     工厂 Bean
	 * @param securityManager 安全管理
	 */
	void initFilter(ShiroFilterFactoryBean factoryBean, SecurityManager securityManager);

	/**
	 * 重置权限
	 */
	void resetPermission();

}
