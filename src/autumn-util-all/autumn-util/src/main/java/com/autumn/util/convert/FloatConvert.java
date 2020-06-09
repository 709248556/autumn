package com.autumn.util.convert;

import java.util.Date;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:30:52
 */
public class FloatConvert extends AbstractNumberConvert<Float> {

	/**
	 *
	 */
	public FloatConvert() {
		super(Float.class);
	}

	@Override
	public Float getDefaultValue() {
		return null;
	}

	@Override
	public Class<?> getType() {
		return Float.class;
	}

	@Override
	protected Float convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return ((Number) source).floatValue();
		}
		if (sourceClass.equals(String.class)) {
			try {
				String value = source.toString().trim();
				if (StringUtils.isNullOrBlank(value)) {
					return this.getDefaultValue();
				}
				return Float.valueOf(value);
			} catch (Exception e) {
				throw this.throwConvertException(sourceClass, source);
			}
		}
		if (Date.class.isAssignableFrom(sourceClass)) {
			return (float)((Date) source).getTime();
		}
		if (Enum.class.isAssignableFrom(sourceClass)) {
			return (float) getEnumOrdinal(source);
		}
		throw this.throwConvertException(sourceClass, source);
	}
}
