package com.autumn.util.convert;

import java.util.Date;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:31:32
 */
public class ShortConvert extends AbstractNumberConvert<Short> {

	/**
	 *
	 */
	public ShortConvert() {
		super(Short.class);
	}

	@Override
	public Short getDefaultValue() {
		return null;
	}

	@Override
	protected Short convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return checkOutOfBound(sourceClass, source).shortValue();
		}
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			try {
				if (StringUtils.isNullOrBlank(value)) {
					return this.getDefaultValue();
				}
				return Short.valueOf(value);
			} catch (Exception e) {
				throw this.throwConvertException(sourceClass, source);
			}
		}
		if (Date.class.isAssignableFrom(sourceClass)) {
			throw this.throwConvertException(sourceClass, source);
		}
		if (Enum.class.isAssignableFrom(sourceClass)) {
			return (short) getEnumOrdinal(source);
		}
		throw this.throwConvertException(sourceClass, source);
	}

	@Override
	protected Double checkOutOfBound(Class<?> sourceClass, Object source) {
		double val = super.checkOutOfBound(sourceClass, source);
		if (val > Short.MAX_VALUE || val < Short.MIN_VALUE) {
			throw this.throwConvertException(sourceClass, source);
		}
		return val;
	}

}
