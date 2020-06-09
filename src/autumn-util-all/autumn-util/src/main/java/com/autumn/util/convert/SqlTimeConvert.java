package com.autumn.util.convert;

import com.autumn.util.StringUtils;

/**
 * 
 * @author 老码农
 *
 *         2017-10-10 16:33:57
 */
public class SqlTimeConvert extends AbstractDataConvert<java.sql.Time> {

	public SqlTimeConvert() {
		super(java.sql.Time.class);
	}

	@Override
	public java.sql.Time getDefaultValue() {
		return null;
	}

	@Override
	protected java.sql.Time convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			if (StringUtils.isNullOrBlank(value)) {
				return this.getDefaultValue();
			}
			return java.sql.Time.valueOf(value);
		}
		throw this.throwConvertException(sourceClass, source);
	}

}
