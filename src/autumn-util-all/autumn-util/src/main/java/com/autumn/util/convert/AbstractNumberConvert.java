package com.autumn.util.convert;

/**
 * 数字类型转换
 * 
 * @author 老码农
 *
 *         2017-09-29 15:59:36
 */
public abstract class AbstractNumberConvert<T extends Number> extends AbstractDataConvert<T> {

	/**
	 *
	 * @param convertClass
	 */
	public AbstractNumberConvert(Class<T> convertClass) {
		super(convertClass);
	}

	@Override
	public final boolean isBaseType() {
		return true;
	}

	@Override
	public final boolean isNumberType() {
		return true;
	}
	
	/**
	 * 检查数值是否超过范围
	 * 
	 * @param t
	 * @param sourceClass
	 * @param source
	 * @return
	 */
	protected Double checkOutOfBound(Class<?> sourceClass, Object source) {
		return  ((Number) source).doubleValue();
	}
	
}
