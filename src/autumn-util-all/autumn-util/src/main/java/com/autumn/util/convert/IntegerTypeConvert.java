package com.autumn.util.convert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:31:22
 */
public class IntegerTypeConvert extends IntegerConvert {

	@Override
	public Integer getDefaultValue() {
		return 0;
	}

	@Override
	public Class<?> getType() {
		return Integer.TYPE;
	}
}
