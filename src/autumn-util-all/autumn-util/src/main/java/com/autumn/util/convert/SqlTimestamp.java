package com.autumn.util.convert;


import java.util.Date;

import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

/**
 * Sql时间
 * 
 * @author 老码农
 *
 *         2017-10-11 15:32:03
 */
public class SqlTimestamp extends AbstractDataConvert<java.sql.Timestamp> {

	public SqlTimestamp() {
		super(java.sql.Timestamp.class);
	}

	@Override
	public java.sql.Timestamp getDefaultValue() {
		return null;
	}

	@Override
	public Class<?> getType() {
		return java.sql.Timestamp.class;
	}

	@Override
	protected java.sql.Timestamp convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (sourceClass.equals(long.class)) {
			long value = (long) source;
			return new java.sql.Timestamp(value);
		}
		if (sourceClass.equals(Long.class)) {
			Long value = (Long) source;
			return new java.sql.Timestamp(value);
		}
		if (Date.class.isAssignableFrom(sourceClass)) {
			Date value = (Date) source;
			return new java.sql.Timestamp(value.getTime());
		}
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			if (StringUtils.isNullOrBlank(value)) {
				return this.getDefaultValue();
			}
			return new java.sql.Timestamp(DateUtils.parseDate(value).getTime());
		}
		throw this.throwConvertException(sourceClass, source);
	}

}
