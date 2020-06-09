package com.autumn.security.filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 默认的权限配置
 * 
 * @author 老码农 2018-12-12 11:59:31
 */
public class DefaultAutumnPermissionFilter implements AutumnPermissionFilter {

	@Override
	public void initFilter(ShiroFilterFactoryBean factoryBean, SecurityManager securityManager) {
		// 权限控制map.
		Map<String, String> filterMap = new LinkedHashMap<>();
		// filterMap.put("/logout", "logout");
		// filterMap.put("/favicon.ico", "anon");
		// filterMap.put("/adminLogin", "anon");
		// filterMap.put("/teacherLogin", "anon");
		// 允许访问静态资源
		// filterMap.put("/static/**", "anon");
		// 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
		filterMap.put("/**", "anon");
		// authc表示需要验证身份才能访问，还有一些比如anon表示不需要验证身份就能访问等。
		factoryBean.setFilterChainDefinitionMap(filterMap);
	}

	@Override
	public void resetPermission() {

	}
}
