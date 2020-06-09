package com.autumn.runtime.cache.interceptor;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;

/**
 * 简单参数键生成器
 * 
 * @author 老码农 2019-05-04 17:28:02
 */
public class SimpleParamKeyGenerator implements KeyGenerator {

	/**
	 * Bean 名称
	 */
	public static final String BEAN_NAME = "simpleParamKeyGenerator";

	@Override
	public Object generate(Object target, Method method, Object... params) {
		return generateKey(params);
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	private static Object generateKey(Object... params) {
		if (params.length == 0) {
			return SimpleKey.EMPTY;
		}
		if (params.length == 1) {
			Object param = params[0];
			if (param != null && !param.getClass().isArray()) {
				return param;
			}
		}
		return new SimpleKey(params);
	}
}
