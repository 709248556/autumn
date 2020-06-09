package com.autumn.util.convert;

/**
 * 长整类型转换
 * 
 * @author 老码农
 *
 *         2017-09-29 16:24:51
 */
public class LongTypeConvert extends LongConvert {

	@Override
	public Long getDefaultValue() {
		return 0L;
	}

	@Override
	public Class<?> getType() {
		return Long.TYPE;
	}
}
