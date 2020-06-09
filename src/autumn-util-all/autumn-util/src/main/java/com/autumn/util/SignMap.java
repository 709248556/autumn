package com.autumn.util;

import java.util.Map;
import java.util.TreeMap;

import com.autumn.util.StringUtils;
import com.autumn.util.function.FunctionOneResult;

/**
 * 签名专用的Map
 * 
 * @author 老码农
 *
 *         2017-12-14 10:43:54
 */
public class SignMap extends TreeMap<String, String> {

	private static final long serialVersionUID = -5878619227205262211L;

	/**
	 * 实例化 SignMap 类新实例
	 */
	public SignMap() {
		super();
	}

	/**
	 * 实例化 SignMap 类新实例
	 * 
	 * @param map
	 *            初始Map
	 */
	public SignMap(Map<String, String> map) {
		super(map);
	}

	/**
	 * 获取Url连接字符
	 * 
	 * @return
	 */
	public String urlLinkString() {
		return this.urlLinkString(null, null);
	}

	/**
	 * 获取Url连接字符
	 * 
	 * @param keyFunn
	 * @param valueFunc
	 * @return
	 */
	public String urlLinkString(FunctionOneResult<String, String> keyFunn,
			FunctionOneResult<String, String> valueFunc) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (java.util.Map.Entry<String, String> pair : this.entrySet()) {
			String key = pair.getKey();
			String value = pair.getValue();
			if (!StringUtils.isNullOrBlank(key) && !StringUtils.isNullOrBlank(value)) {
				key = keyFunn != null ? keyFunn.apply(key) : key;
				value = valueFunc != null ? valueFunc.apply(value) : value;
				if (i > 0) {
					builder.append("&");
				}
				builder.append(key).append("=").append(value);
				i++;
			}
		}
		return builder.toString();
	}

	/**
	 * 克隆
	 * 
	 * @return
	 */
	@Override
	public SignMap clone() {
		return clone(null, null);
	}

	/**
	 * 克隆
	 * 
	 * @param keyFunn
	 *            键生成
	 * @param valueFunc
	 *            值生成
	 * @return
	 */
	public SignMap clone(FunctionOneResult<String, String> keyFunn, FunctionOneResult<String, String> valueFunc) {
		SignMap map = new SignMap();
		for (java.util.Map.Entry<String, String> pair : this.entrySet()) {
			String key = pair.getKey();
			String value = pair.getValue();
			if (!StringUtils.isNullOrBlank(key) && !StringUtils.isNullOrBlank(value)) {
				key = keyFunn != null ? keyFunn.apply(key) : key;
				value = valueFunc != null ? valueFunc.apply(value) : value;
				map.put(key, value);
			}
		}
		return map;
	}
}
