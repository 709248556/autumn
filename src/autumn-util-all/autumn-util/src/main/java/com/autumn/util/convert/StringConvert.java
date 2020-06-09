package com.autumn.util.convert;

/**
 * 
 * @author 老码农
 *
 *         2017-10-10 11:30:24
 */
public class StringConvert extends AbstractDataConvert<String> {

	public StringConvert() {
		super(String.class);
	}

	@Override
	public final boolean isBaseType() {
		return true;
	}

	@Override
	public final boolean isNumberType() {
		return false;
	}

	@Override
	public String getDefaultValue() {
		return null;
	}

	@Override
	protected String convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		return source.toString();
	}

}
