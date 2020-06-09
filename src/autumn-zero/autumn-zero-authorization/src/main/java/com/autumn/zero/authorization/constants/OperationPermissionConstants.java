package com.autumn.zero.authorization.constants;

import com.autumn.domain.values.StringConstantItemValue;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 操作权限常量
 * 
 * @author 老码农 2018-12-10 00:54:24
 */
public final class OperationPermissionConstants implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7917139277481318939L;

	/**
	 * 添加
	 */
	public static final String ADD = "add";

	/**
	 * 修改
	 */
	public static final String UPDATE = "update";

	/**
	 * 删除
	 */
	public static final String DELETE = "delete";

	/**
	 * 查询
	 */
	public static final String QUERY = "query";

	/**
	 * 导出
	 */
	public static final String EXPORT = "export";

	/**
	 * 清除
	 */
	public static final String CLEAR = "clear";

	@SuppressWarnings("unchecked")
	private static final Map<String, StringConstantItemValue> NAME_MAP = new CaseInsensitiveMap();

	static {
		NAME_MAP.put(ADD, new StringConstantItemValue(ADD, "添加", "添加数据权限"));
		NAME_MAP.put(UPDATE, new StringConstantItemValue(UPDATE, "修改", "修改数据权限"));
		NAME_MAP.put(DELETE, new StringConstantItemValue(DELETE, "删除", "删除数据权限"));
		NAME_MAP.put(QUERY, new StringConstantItemValue(QUERY, "查询", "数据查询权限"));
		NAME_MAP.put(EXPORT, new StringConstantItemValue(EXPORT, "导出", "将数据导出权限"));
		NAME_MAP.put(CLEAR, new StringConstantItemValue(CLEAR, "清除数据", "清除数据权限"));
	}

	/**
	 * 是否存在
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean exist(String value) {
		if (value == null) {
			return false;
		}
		return NAME_MAP.containsKey(value);
	}

	/**
	 * 获取名称
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static String getName(String value) {
		StringConstantItemValue item = NAME_MAP.get(value);
		if (item == null) {
			return "";
		}
		return item.getName();
	}

	/**
	 * 获取项目
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static StringConstantItemValue getItem(String value) {
		return NAME_MAP.get(value);
	}

	/**
	 * 项目集合
	 * 
	 * @return
	 */
	public static Collection<StringConstantItemValue> items() {
		return NAME_MAP.values();
	}
}
