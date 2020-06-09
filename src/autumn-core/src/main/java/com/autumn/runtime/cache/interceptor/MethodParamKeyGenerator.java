package com.autumn.runtime.cache.interceptor;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 方法参数键生成器
 * 
 * @author 老码农 2019-05-04 17:44:44
 */
public class MethodParamKeyGenerator implements KeyGenerator {

	/**
	 * Bean 名称
	 */
	public static final String BEAN_NAME = "methodParamKeyGenerator";
	
	@Override
	public Object generate(Object target, Method method, Object... params) {
		return generateKey(method, params);
	}

	/**
	 * 
	 * @param method
	 * @param params
	 * @return
	 */
	private static Object generateKey(Method method, Object... params) {
		if (params.length == 0) {
			return new MethodParamKey(method.getName());
		}
		if (params.length == 1) {
			Object param = params[0];
			if (param != null && !param.getClass().isArray()) {
				return new MethodParamKey(method.getName(), param);
			}
		}
		return new MethodParamKey(method.getName(), params);
	}
}
