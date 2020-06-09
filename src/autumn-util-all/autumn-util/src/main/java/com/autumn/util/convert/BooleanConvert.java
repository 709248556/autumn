package com.autumn.util.convert;

import java.util.HashMap;
import java.util.Map;

import com.autumn.util.StringUtils;

/**
 * 
 * @author 老码农
 *
 *         2017-12-06 12:29:34
 */
public class BooleanConvert extends AbstractDataConvert<Boolean> {

	/**
	 * 
	 */
	private final static Map<String, Boolean> BOOL_VALUE=new HashMap<>();

	static {
		BOOL_VALUE.put("true", true);
		BOOL_VALUE.put("t", true);
		BOOL_VALUE.put("yes", true);
		BOOL_VALUE.put("y", true);
		BOOL_VALUE.put("1", true);
		BOOL_VALUE.put("false", false);
		BOOL_VALUE.put("f", false);
		BOOL_VALUE.put("no", false);
		BOOL_VALUE.put("n", false);
		BOOL_VALUE.put("0", false);
	}

	public BooleanConvert() {
		super(Boolean.class);
	}

	@Override
	public Boolean getDefaultValue() {
		return null;
	}

	@Override
	protected Boolean convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		String value = source.toString().trim();
		if (StringUtils.isNullOrBlank(value)) {
			return this.getDefaultValue();
		}
		value = value.toLowerCase();
		Boolean v = BOOL_VALUE.get(value);
		if (v != null) {
			return v;
		}
		int intValue = Double.valueOf(value).intValue();
		return intValue == 1;
	}

}
