package com.autumn.util.convert;

import java.util.Date;
import java.util.Objects;

import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

/**
 * 
 * @author 老码农
 *
 *         2017-10-10 16:35:59
 */
public class SqlDateConvert extends AbstractDataConvert<java.sql.Date> {

	public SqlDateConvert() {
		super(java.sql.Date.class);
	}

	@Override
	public java.sql.Date getDefaultValue() {
		return null;
	}

	@Override
	protected java.sql.Date convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (sourceClass.equals(long.class)) {
			long value = (long) source;
			return new java.sql.Date(value);
		}
		if (sourceClass.equals(Long.class)) {
			Long value = (Long) source;
			return new java.sql.Date(value);
		}
		if (Date.class.isAssignableFrom(sourceClass)) {
			Date value = (Date) source;
			return new java.sql.Date(value.getTime());
		}
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			if (StringUtils.isNullOrBlank(value)) {
				return this.getDefaultValue();
			}
			return new java.sql.Date(Objects.requireNonNull(DateUtils.parseDate(value)).getTime());
		}
		throw this.throwConvertException(sourceClass, source);
	}

}
