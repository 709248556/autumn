package com.autumn.runtime.cache.interceptor;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 方法参数键
 * 
 * @author 老码农 2019-05-04 17:35:03
 */
public class MethodParamKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6437008315169920423L;
	private final String methodName;
	private final Object[] params;
	private final int hashCode;

	/**
	 * 
	 * @param methodName
	 */
	public MethodParamKey(String methodName) {
		Assert.notNull(methodName, "methodName must not be null");
		this.methodName = methodName;
		this.hashCode = methodName.hashCode();
		this.params = new Object[0];
	}

	/**
	 * 
	 * @param methodName
	 * @param elements
	 */
	public MethodParamKey(String methodName, Object... elements) {
		Assert.notNull(methodName, "methodName must not be null");
		this.methodName = methodName;
		Assert.notNull(elements, "Elements must not be null");
		this.params = new Object[elements.length];
		System.arraycopy(elements, 0, this.params, 0, elements.length);
		this.hashCode = methodName.hashCode() * 31 + Arrays.deepHashCode(this.params);
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj || (obj instanceof MethodParamKey
				&& this.methodName.equals(((MethodParamKey) obj).methodName)
				&& Arrays.deepEquals(this.params, ((MethodParamKey) obj).params)));
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
