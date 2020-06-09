package com.autumn.util.convert;

import java.util.Date;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:31:13
 */
public class IntegerConvert extends AbstractNumberConvert<Integer> {

	/**
	 *
	 */
	public IntegerConvert() {
		super(Integer.class);
	}

	@Override
	public Integer getDefaultValue() {
		return null;
	}

	@Override
	protected Integer convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return checkOutOfBound(sourceClass, source).intValue();
		}
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			try {
				if (StringUtils.isNullOrBlank(value)) {
					return this.getDefaultValue();
				}
				return Integer.valueOf(value);
			} catch (Exception e) {
				throw this.throwConvertException(sourceClass, source);
			}
		}
		if (Date.class.isAssignableFrom(sourceClass)) {
			throw this.throwConvertException(sourceClass, source);
		}
		if (Enum.class.isAssignableFrom(sourceClass)) {
			return getEnumOrdinal(source);
		}
		throw this.throwConvertException(sourceClass, source);
	}

	@Override
	protected Double checkOutOfBound(Class<?> sourceClass, Object source) {
		double val = super.checkOutOfBound(sourceClass, source);
		if (val > Integer.MAX_VALUE || val < Integer.MIN_VALUE) {
			throw this.throwConvertException(sourceClass, source);
		}
		return val;
	}

}
