package com.autumn.runtime.cache.interceptor;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 目标类型方法参数键
 * 
 * @author 老码农 2019-05-04 17:54:48
 */
public class TargetTypeMethodParamKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6720445059080302155L;
	private final Class<?> targetClass;
	private final String methodName;
	private final Object[] params;
	private final int hashCode;

	/**
	 * 
	 * @param targetClass
	 * @param methodName
	 */
	public TargetTypeMethodParamKey(Class<?> targetClass, String methodName) {
		Assert.notNull(targetClass, "targetClass must not be null");
		Assert.notNull(methodName, "methodName must not be null");
		this.targetClass = targetClass;
		this.methodName = methodName;
		this.hashCode = methodName.hashCode();
		this.params = new Object[0];
	}

	/**
	 * 
	 * @param methodName
	 * @param elements
	 */
	public TargetTypeMethodParamKey(Class<?> targetClass, String methodName, Object... elements) {
		Assert.notNull(targetClass, "targetClass must not be null");
		this.targetClass = targetClass;
		Assert.notNull(methodName, "methodName must not be null");
		this.methodName = methodName;
		Assert.notNull(elements, "Elements must not be null");
		this.params = new Object[elements.length];
		System.arraycopy(elements, 0, this.params, 0, elements.length);
		this.hashCode = (this.targetClass.hashCode() * 31) + (methodName.hashCode() * 31)
				+ Arrays.deepHashCode(this.params);
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj || (obj instanceof TargetTypeMethodParamKey
				&& this.targetClass.equals(((TargetTypeMethodParamKey) obj).targetClass)
				&& this.methodName.equals(((TargetTypeMethodParamKey) obj).methodName)
				&& Arrays.deepEquals(this.params, ((TargetTypeMethodParamKey) obj).params)));
	}

	@Override
	public final int hashCode() {
		return this.hashCode;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "." + this.methodName + " ["
				+ StringUtils.arrayToCommaDelimitedString(this.params) + "]";
	}
}
