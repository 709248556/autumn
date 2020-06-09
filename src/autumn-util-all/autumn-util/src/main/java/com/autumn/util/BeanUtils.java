package com.autumn.util;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.function.FunctionOneResult;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Bean 工具
 * 
 * @author 老码农
 *
 *         2018-04-10 15:09:57
 */
public class BeanUtils {

	/**
	 * 设置字符属性为空白值
	 * 
	 * @param instance
	 *            实例
	 * @param isNullPredicate
	 *            是否值为 null 的属性还是全部，isNullPredicate = false 时为全部
	 *
	 */
	public static void setBigDecimalPropertyForZero(Object instance, boolean isNullPredicate) {
		setPropertyValue(instance, p -> {
			if (p.getType().equals(BigDecimal.class)) {
				if (isNullPredicate) {
					Object value = p.getValue(instance);
					return value == null;
				} else {
					return true;
				}
			}
			return false;
		}, BigDecimal.ZERO);
	}

	/**
	 * 设置字符属性为空白值
	 * 
	 * @param instance
	 *            实例
	 * @param isNullPredicate
	 *            是否值为 null 的属性还是全部，isNullPredicate = false 时为全部
	 *
	 */
	public static void setStringPropertyForEmpty(Object instance, boolean isNullPredicate) {
		setPropertyValue(instance, p -> {
			if (p.getType().equals(String.class)) {
				if (isNullPredicate) {
					Object value = p.getValue(instance);
					return value == null;
				} else {
					return true;
				}
			}
			return false;
		}, "");
	}

	/**
	 * 将数据库实体的null转为默认值，字符去掉两并头空格，null 字符转为空白字符
	 * 
	 * @param instance
	 *
	 */
	public static void setDbEntityforNullToDefault(Object instance) {
		ExceptionUtils.checkNotNull(instance, "instance");
		Map<String, BeanProperty> propertyMap = ReflectUtils.getBeanPropertyMap(instance.getClass());
		for (BeanProperty property : propertyMap.values()) {
			if (property.canWrite()) {
				Object value;
				if (property.getType().equals(String.class)) {
					value = property.getValue(instance);
					if (value == null) {
						property.setValue(instance, "");
					} else {
						property.setValue(instance, value.toString().trim());
					}
				} else if (property.getType().equals(BigDecimal.class)) {
					value = property.getValue(instance);
					if (value == null) {
						property.setValue(instance, BigDecimal.ZERO);
					}
				}
			}
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param instance
	 *            实例
	 * @param predicate
	 *            条件
	 * @param defaultValue
	 *            默认值
	 *
	 */
	public static void setPropertyValue(Object instance, Predicate<BeanProperty> predicate, Object defaultValue) {
		setPropertyValue(instance, predicate, p -> defaultValue);
	}

	/**
	 * 设置属性值
	 * 
	 * @param instance
	 *            实例
	 * @param predicate
	 *            条件
	 * @param valueFun
	 *            值函数
	 *
	 */
	public static void setPropertyValue(Object instance, Predicate<BeanProperty> predicate,
			FunctionOneResult<BeanProperty, Object> valueFun) {
		ExceptionUtils.checkNotNull(predicate, "predicate");
		if (instance != null) {
			Map<String, BeanProperty> propertyMap = ReflectUtils.getBeanPropertyMap(instance.getClass());
			for (BeanProperty property : propertyMap.values()) {
				if (property.canWrite() && predicate.test(property)) {
					property.setValue(instance, valueFun.apply(property));
				}
			}
		}
	}
}
