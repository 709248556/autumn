package com.autumn.util.convert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:31:38
 */
public class ShortTypeConvert extends ShortConvert {

	@Override
	public Short getDefaultValue() {
		return (short)0;
	}

	@Override
	public Class<?> getType() {
		return Short.TYPE;
	}
}
