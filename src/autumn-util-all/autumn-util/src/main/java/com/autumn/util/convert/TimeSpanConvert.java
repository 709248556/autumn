package com.autumn.util.convert;

import com.autumn.util.StringUtils;
import com.autumn.util.TimeSpan;
import com.autumn.util.TypeUtils;

/**
 * 时间间隔
 * 
 * @author 老码农
 *
 *         2017-10-10 15:39:38
 */
public class TimeSpanConvert  extends AbstractDataConvert<TimeSpan> {

	public TimeSpanConvert() {
		super(TimeSpan.class);
	}

	@Override
	public TimeSpan getDefaultValue() {
		return null;
	}

	@Override
	protected TimeSpan convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return new TimeSpan(((Number) source).longValue());
		}
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			if (StringUtils.isNullOrBlank(value)) {
				return this.getDefaultValue();
			}
			return TimeSpan.parse(value);
		}
		throw this.throwConvertException(sourceClass, source);
	}

}
