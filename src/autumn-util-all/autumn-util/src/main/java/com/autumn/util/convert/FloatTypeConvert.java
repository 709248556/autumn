package com.autumn.util.convert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:31:02
 */
public class FloatTypeConvert extends FloatConvert {

	@Override
	public Float getDefaultValue() {
		return 0.0f;
	}

	@Override
	public Class<?> getType() {
		return Float.TYPE;
	}
}
