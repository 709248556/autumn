package com.autumn.util.convert;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

import java.util.Date;

/**
 * 
 * @author 老码农
 *
 *         2017-12-06 12:30:13
 */
public class CharacterConvert extends AbstractDataConvert<Character> {

	public CharacterConvert() {
		super(Character.class);
	}

	@Override
	public Character getDefaultValue() {
		return null;
	}

	@Override
	protected Character convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return (char) checkOutOfBound(sourceClass, source).intValue();
		}
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			// 上面是null的时候已经返回，这里不会是null
			if (StringUtils.isNullOrBlank(value)) {
				return this.getDefaultValue();
			}
		}
		if (Date.class.isAssignableFrom(sourceClass)) {
			throw this.throwConvertException(sourceClass, source);
		}
		if (Enum.class.isAssignableFrom(sourceClass)) {
			return (char) getEnumOrdinal(source);
		}
		throw this.throwConvertException(sourceClass, source);
	}

	private final static double SHORT_U_MAX_VALUE = Short.MAX_VALUE * 2 + 1;

	protected Double checkOutOfBound(Class<?> sourceClass, Object source) {
		double val = ((Number) source).doubleValue();
		if (val > SHORT_U_MAX_VALUE || val < 0) {
			throw this.throwConvertException(sourceClass, source);
		}
		return val;
	}

}
