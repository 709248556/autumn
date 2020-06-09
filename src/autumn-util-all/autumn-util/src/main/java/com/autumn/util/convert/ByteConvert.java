package com.autumn.util.convert;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

import java.util.Date;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:29:53
 */
public class ByteConvert extends AbstractNumberConvert<Byte> {

	/**
	 *
	 */
	public ByteConvert() {
		super(Byte.class);
	}

	@Override
	public Byte getDefaultValue() {
		return null;
	}


	@Override
	protected Byte convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return checkOutOfBound(sourceClass, source).byteValue();
		}
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			try {
				if (StringUtils.isNullOrBlank(value)) {
					return this.getDefaultValue();
				}
				return Byte.valueOf(value);
			} catch (Exception e) {
				throw this.throwConvertException(sourceClass, source);
			}
		}
		if (Date.class.isAssignableFrom(sourceClass)) {
			throw this.throwConvertException(sourceClass, source);
		}
		if (Enum.class.isAssignableFrom(sourceClass)) {
			return (byte) getEnumOrdinal(source);
		}
		throw this.throwConvertException(sourceClass, source);
	}

	@Override
	protected Double checkOutOfBound(Class<?> sourceClass, Object source) {
		double val = super.checkOutOfBound(sourceClass, source);
		if (val > Byte.MAX_VALUE || val < Byte.MIN_VALUE) {
			throw this.throwConvertException(sourceClass, source);
		}
		return val;
	}
}
