package com.autumn.util;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.reflect.ReflectUtils;

/**
 * Lambda 工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-09 16:11
 */
public class LambdaUtils {

	/**
	 * Lambda 缓存
	 */
	private static final Map<Class<?>, WeakReference<SerializedLambda>> LAMBDA_CACHE = new ConcurrentHashMap<>();

	/**
	 * Lambda 签名缓存
	 */
	private static final Map<String, Class<?>> LAMBDA_SIGNATURE_CACHE = new ConcurrentHashMap<>();

	/**
	 * 反转 Lambda 表达式
	 *
	 * @param function
	 *            函数
	 * @return 返回 SerializedLambda 信息
	 */
	public static SerializedLambda resolveLambda(Serializable function) {
		Method writeReplace;
		try {
			writeReplace = function.getClass().getDeclaredMethod("writeReplace");
		} catch (NoSuchMethodException noException) {
			throw ExceptionUtils.throwSystemException("指定的 Lambda 表达式接口必须实现 Serializable 接口。");
		}		
		try {
			writeReplace.setAccessible(Boolean.TRUE);
			SerializedLambda lambda = (SerializedLambda) writeReplace.invoke(function);
			if(lambda.getImplMethodName().contains("$")) {
				throw ExceptionUtils.throwSystemException("错误的 Lambda 表达式，必须采用  ClassName::methodName 的表达式方式。");
			}
			return lambda;
		} catch (InvocationTargetException | IllegalAccessException ex) {
			throw ExceptionUtils.throwSystemException("调用 Lambda 出错:" + ex.getMessage(), ex);
		}
	}

	/**
	 * 解析 lambda 表达式并缓存
	 *
	 * @param function
	 *            需要解析的 lambda 对象
	 * 
	 * @return 返回解析后的 SerializedLambda 结果
	 */
	public static SerializedLambda resolveCacheLambda(Serializable function) {
		Class<?> clazz = function.getClass();
		return Optional.ofNullable(LAMBDA_CACHE.get(clazz)).map(WeakReference::get).orElseGet(() -> {
			SerializedLambda lambda = resolveLambda(function);
			String key = lambda.getImplClass() + "|" + lambda.getImplMethodName() + "|"
					+ lambda.getImplMethodSignature();
			Class<?> oldClass = LAMBDA_SIGNATURE_CACHE.get(key);
			if (oldClass != null) {
				if (!oldClass.equals(clazz)) {
					LAMBDA_CACHE.remove(oldClass);
					LAMBDA_SIGNATURE_CACHE.put(key, clazz);
				}
			} else {
				LAMBDA_SIGNATURE_CACHE.put(key, clazz);
			}
			LAMBDA_CACHE.put(clazz, new WeakReference<>(lambda));
			return lambda;
		});
	}

	/**
	 * 获取读函数字段名称
	 * 
	 * @param serializedLambda
	 *            lambda信息
	 * @return
	 */
	public static String getReadFiledName(SerializedLambda serializedLambda) {
		String methodName = serializedLambda.getImplMethodName();
		String fieldName;
		if (methodName.length() > 3 && methodName.startsWith(ReflectUtils.PROPERTY_GET_NAME)) {
			fieldName = methodName.substring(3);
		} else if (methodName.length() > 2 && methodName.startsWith(ReflectUtils.PROPERTY_IS_NAME)) {
			fieldName = methodName.substring(2);
		} else {
			fieldName = methodName;
		}
		return StringUtils.lowerCaseCapitalize(fieldName);
	}

	/**
	 * 获取写函数字段名称
	 * 
	 * @param serializedLambda
	 *            lambda信息
	 * @return
	 */
	public static String getWriteFiledName(SerializedLambda serializedLambda) {
		String methodName = serializedLambda.getImplMethodName();
		String fieldName;
		if (methodName.length() > 3 && methodName.startsWith(ReflectUtils.PROPERTY_SET_NAME)) {
			fieldName = methodName.substring(3);
		} else {
			fieldName = methodName;
		}
		return StringUtils.lowerCaseCapitalize(fieldName);
	}

}
