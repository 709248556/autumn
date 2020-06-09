package com.autumn.util.convert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:29:43
 */
public class BooleanTypeConvert extends BooleanConvert {

	@Override
	public Boolean getDefaultValue() {
		return false;
	}

	@Override
	public Class<?> getType() {
		return Boolean.TYPE;
	}
}
