package com.autumn.util.convert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:30:42
 */
public class DoubleTypeConvert extends DoubleConvert {

	@Override
	public Double getDefaultValue() {
		return 0.0;
	}

	@Override
	public Class<?> getType() {
		return Double.TYPE;
	}
}
