package com.autumn.evaluator;

import java.util.Map;

//
// * 内部和全局变量,全局变量优先级最低,即若上下文与全局变量存在同名,则取上下文变量
// * 不区分键的大小写
// 

import org.apache.commons.collections.map.CaseInsensitiveMap;

/**
 * 变量
 */
@SuppressWarnings("unchecked")
public class Variable {

	/**
	 * 系统变量
	 */
	private static Map<String, Variable> systemVariable;

	/**
	 * 初始变更
	 */
	static {
		Variable.systemVariable = new CaseInsensitiveMap();

		// 变量的键名必须是大写,Name是生成表达树时的名称
		// 可在此处添加其他系统变量(系统变量优先于上下文变量,即与上下文变量重复时,则取系统变量)
		Variable.systemVariable.put("TRUE", new Variable("True", new Variant(true)));
		Variable.systemVariable.put("FALSE", new Variable("False", new Variant(false)));
		Variable.systemVariable.put("NULL", new Variable("NULL", new Variant()));
	}

	/**
	 * 是否存在变量
	 *
	 * @param name
	 * @return
	 */
	public static boolean hasVariable(String name) {
		return Variable.systemVariable.containsKey(name);
	}

	/**
	 * 获取指定的变量
	 *
	 * @param name
	 *            名称
	 * @return
	 */
	public static Variable getVariable(String name) {
		return Variable.systemVariable.get(name);
	}	

	/**
	 * 实例化 variable 新实例
	 *
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 */
	private Variable(String name, Variant value) {
		this.setName(name);
		this.setValue(value);
	}

	/**
	 * 获取变量名称
	 */
	private String name;

	public final String getName() {
		return name;
	}

	private void setName(String value) {
		name = value;
	}

	/**
	 * 获取变量值
	 */
	private Variant value;

	public final Variant getValue() {
		return value;
	}

	private void setValue(Variant value) {
		this.value = value;
	}
}