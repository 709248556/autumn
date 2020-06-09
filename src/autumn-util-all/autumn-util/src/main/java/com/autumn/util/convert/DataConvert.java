package com.autumn.util.convert;

/**
 * 数据转换抽象
 * 
 * @author 老码农
 *
 *         2017-09-29 14:59:45
 */
public interface DataConvert {

	/**
	 * 获取默认值
	 * 
	 * @return
	 * @author 老码农 2017-09-29 15:00:18
	 */
	Object getDefaultValue();

	/**
	 * 获取类型
	 * 
	 * @return
	 * @author 老码农 2017-09-29 15:00:41
	 */
	Class<?> getType();

	/**
	 * 是否为基本类型
	 * 
	 * @return
	 * @author 老码农 2017-09-29 15:01:05
	 */
	boolean isBaseType();

	/**
	 * 是否为数字类型
	 * 
	 * @return
	 * @author 老码农 2017-09-29 15:01:40
	 */
	boolean isNumberType();

	/**
	 * 转换
	 * 	
	 * @param source
	 *            源
	 * @return
	 * @author 老码农 2017-09-29 15:02:43
	 */
	Object convert(Object source);

	/**
	 * 转换
	 * 
	 * @param targetClass
	 *            目标类型
	 * @param source
	 *            源
	 * @return
	 * @author 老码农 2017-09-29 15:02:43
	 */
	Object convert(Class<?> targetClass, Object source);
}
