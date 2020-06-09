package com.autumn.runtime.cache.interceptor;

import java.lang.reflect.Method;

import org.springframework.aop.support.AopUtils;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 目标类型方法参数键生成器
 * 
 * @author 老码农 2019-05-04 17:56:31
 */
public class TargetTypeMethodParamKeyGenerator implements KeyGenerator {

	/**
	 * Bean 名称
	 */
	public static final String BEAN_NAME = "targetTypeMethodParamKeyGenerator";
	
	@Override
	public Object generate(Object target, Method method, Object... params) {
		return generateKey(target, method, params);
	}

	/**
	 * 
	 * @param method
	 * @param params
	 * @return
	 */
	private static Object generateKey(Object target, Method method, Object... params) {		
		Class<?> actualClass = AopUtils.getTargetClass(target);
		if (params.length == 0) {
			return new TargetTypeMethodParamKey(actualClass, method.getName());
		}
		if (params.length == 1) {
			Object param = params[0];
			if (param != null && !param.getClass().isArray()) {
				return new TargetTypeMethodParamKey(actualClass, method.getName(), param);
			}
		}
		return new TargetTypeMethodParamKey(actualClass, method.getName(), params);
	}
}
