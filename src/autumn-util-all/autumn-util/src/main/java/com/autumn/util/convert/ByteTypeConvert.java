package com.autumn.util.convert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:30:03
 */
public class ByteTypeConvert extends ByteConvert {

	@Override
	public Byte getDefaultValue() {
		return (byte)0;
	}

	@Override
	public Class<?> getType() {
		return Byte.TYPE;
	}
}
