package com.autumn.util.convert;

import com.autumn.util.StringUtils;
import com.autumn.util.Time;
import com.autumn.util.TypeUtils;

/**
 * 时间类型转换
 * 
 * @author 老码农
 *
 *         2017-10-10 15:22:28
 */
public class TimeConvert extends AbstractDataConvert<Time> {

	public TimeConvert() {
		super(Time.class);
	}

	@Override
	public Time getDefaultValue() {
		return null;
	}

	@Override
	protected Time convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return new Time(((Number) source).longValue());
		}
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			if (StringUtils.isNullOrBlank(value)) {
				return this.getDefaultValue();
			}
			return Time.parse(value);
		}
		throw this.throwConvertException(sourceClass, source);
	}

}
