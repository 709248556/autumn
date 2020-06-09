package com.autumn.util.convert;

import java.math.BigInteger;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

/**
 * 
 * @author 老码农
 *
 *         2017-10-10 12:16:24
 */
public class BigIntegerConvert extends AbstractNumberConvert<BigInteger> {

	/**
	 *
	 */
	public BigIntegerConvert() {
		super(BigInteger.class);
	}

	@Override
	public BigInteger getDefaultValue() {
		return null;
	}

	@Override
	protected BigInteger convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		if (TypeUtils.isNumberType(sourceClass)) {
			return BigInteger.valueOf(((Number) source).longValue());
		}
		if (sourceClass.equals(String.class)) {
			String value = source.toString().trim();
			if (StringUtils.isNullOrBlank(value)) {
				return this.getDefaultValue();
			}
			return new BigInteger(value);
		}
		throw this.throwConvertException(sourceClass, source);
	}

}
