package com.autumn.util.convert;

import java.lang.reflect.Array;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.ReflectUtils;

/**
 * 枚举转换
 * 
 * @author 老码农
 *
 *         2017-10-10 14:13:25
 */
public class EnumConvert extends AbstractDataConvert<Enum> {

	public EnumConvert() {
		super(Enum.class);
	}

	@Override
	public Enum getDefaultValue() {
		return null;
	}

	@Override
	protected Enum convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
		Object values = ReflectUtils.getEnumTypeArray(targetClass);
		int size = Array.getLength(values);
		if (sourceClass.equals(String.class)) {
			String name = source.toString().trim();
			if (StringUtils.isNullOrBlank(name)) {
				if (size > 0) {
					return (Enum) Array.get(values, 0);
				}
			}
			for (int i = 0; i < size; i++) {
				Object enumValue = Array.get(values, i);
				if (this.getEnumName(enumValue).equalsIgnoreCase(name)) {
					return (Enum) enumValue;
				}
			}
		}
		if (TypeUtils.isIntegerType(sourceClass)) {
			int ordinal = ((Number) source).intValue();
			for (int i = 0; i < size; i++) {
				Object enumValue = Array.get(values, i);
				if (this.getEnumOrdinal(enumValue) == ordinal) {
					return (Enum) enumValue;
				}
			}
		}
		throw this.throwConvertException(targetClass, sourceClass, source, null);
	}

}
