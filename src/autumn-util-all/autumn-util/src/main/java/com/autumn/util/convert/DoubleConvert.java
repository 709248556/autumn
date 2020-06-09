package com.autumn.util.convert;

import java.util.Date;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:30:33
 */
public class DoubleConvert extends AbstractNumberConvert<Double> {

	public DoubleConvert() {
		super(Double.class);
	}

	@Override
	public Double getDefaultValue() {
		return null;
	}

	@Override
	protected Double convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return ((Number) source).doubleValue();
		}
		if (sourceClass.equals(String.class)) {
			try {
				String value = source.toString().trim();
				if (StringUtils.isNullOrBlank(value)) {
					return this.getDefaultValue();
				}
				return Double.valueOf(value);
			} catch (Exception e) {
				throw this.throwConvertException(sourceClass, source);
			}
		}
		if (Date.class.isAssignableFrom(sourceClass)) {
			return (double)(((Date) source).getTime());
		}
		if (Enum.class.isAssignableFrom(sourceClass)) {
			return (double) getEnumOrdinal(source);
		}
		throw this.throwConvertException(sourceClass, source);
	}

}
