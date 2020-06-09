package com.autumn.util.convert;

import java.util.Date;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

/**
 * 长整数转换
 * 
 * @author 老码农
 *
 *         2017-09-29 16:02:46
 */
public class LongConvert extends AbstractNumberConvert<Long> {

	/**
	 *
	 */
	public LongConvert() {
		super(Long.class);
	}

	@Override
	public Long getDefaultValue() {
		return null;
	}

	@Override
	protected Long convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return ((Number) source).longValue();
		}
		if (sourceClass.equals(String.class)) {
			try {
				String value = source.toString().trim();
				if (StringUtils.isNullOrBlank(value)) {
					return this.getDefaultValue();
				}
				return Double.valueOf(value).longValue();
			} catch (Exception e) {
				throw this.throwConvertException(sourceClass, source);
			}
		}
		if (Date.class.isAssignableFrom(sourceClass)) {
			return ((Date) source).getTime();
		}
		if (Enum.class.isAssignableFrom(sourceClass)) {
			return (long) getEnumOrdinal(source);
		}
		throw this.throwConvertException(sourceClass, source);
	}

}
