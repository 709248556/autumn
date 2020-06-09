package com.autumn.util;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.json.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.StringReader;
import java.util.*;
import java.util.Map.Entry;

/**
 * Autumn 上下文帮肋
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-24 13:44:07
 */
public class AutumnContextUtils {

	/**
	 * 应用名称的属性
	 */
	public final static String PROPERTIES_SPRING_APPLICATION_NAME = "spring.application.name";

	/**
	 * 获取属性或对象
	 * 
	 * @param key
	 *            键
	 * @param clazz
	 *            类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getBaseTypeValue(String value, Class<T> clazz) {
		if (String.class.equals(clazz)) {
			return (T) value;
		}
		if (StringUtils.isNullOrBlank(value)) {
			return TypeUtils.toConvert(clazz, null);
		}
		return TypeUtils.toConvert(clazz, value);
	}

	/**
	 * 获取字符属性
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static String getProperty(String key) {
		return System.getProperty(ExceptionUtils.checkNotNullOrBlank(key, "key"));
	}

	/**
	 * 获取属性或对象
	 * 
	 * @param key
	 *            键
	 * @param clazz
	 *            类型
	 * @return
	 */
	public static <T> T getProperty(String key, Class<T> clazz) {
		String value = getProperty(key);
		if (value == null) {
			return null;
		}
		if (TypeUtils.isBaseType(clazz)) {
			return getBaseTypeValue(value, clazz);
		}
		return JsonUtils.parseObject(value, clazz);
	}

	/**
	 * 获取属性或对象
	 * 
	 * @param key
	 *            键
	 * @param clazz
	 *            类型
	 * @return
	 */
	public static <T> List<T> getPropertyList(String key, Class<T> clazz) {
		String value = getProperty(key);
		if (value == null) {
			return new ArrayList<T>();
		}
		return JsonUtils.parseList(value, clazz);
	}

	/**
	 * 获取属性或对象
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static Map<String, Object> getPropertyMap(String key) {
		String value = getProperty(key);
		if (value == null) {
			return CollectionUtils.newHashMap();
		}
		return JsonUtils.parseMap(value);
	}

	/**
	 * 设置属性
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void setProperty(String key, Object value) {
		ExceptionUtils.checkNotNullOrBlank(key, "key");
		if (value == null) {
			System.setProperty(key, null);
		} else {
			Class<?> clazz = value.getClass();
			if (TypeUtils.isBaseType(clazz)) {
				System.setProperty(key, value.toString());
			} else {
				System.setProperty(key, JsonUtils.toJSONString(value));
			}
		}
	}

	/**
	 * 显示属性
	 * 
	 * @param propertiesTreeMap
	 *            属性树 Map
	 *
	 */
	public static Map<String, Object> showProperties(Map<String, Object> propertiesTreeMap) {
		if (propertiesTreeMap == null) {
			return new LinkedHashMap<>(0);
		}
		Map<String, Object> showMap = new LinkedHashMap<>(0);
		for (Map.Entry<String, Object> entry : propertiesTreeMap.entrySet()) {
			handleShowProperties(null, showMap, entry);
		}
		return showMap;
	}

	/**
	 * 
	 * @param parentName
	 * @param showMap
	 * @param treeItem
	 *
	 */
	@SuppressWarnings("unchecked")
	private static void handleShowProperties(String parentName, Map<String, Object> showMap,
			Entry<String, Object> treeItem) {
		Object value = treeItem.getValue();
		String key;
		if (StringUtils.isNullOrBlank(parentName)) {
			key = treeItem.getKey();
		} else {
			key = String.format("%s.%s", parentName, treeItem.getKey());
		}
		if (value instanceof Map) {
			Map<String, Object> valueMap = (Map<String, Object>) value;
			for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
				handleShowProperties(key, showMap, entry);
			}
		} else {
			showMap.put(key, value);
		}
	}

	/**
	 * 处理属性名称
	 * <p>
	 * driver-class-name = driverClassName
	 * </p>
	 * <p>
	 * Driver-class-name = driverClassName
	 * </p>
	 * <p>
	 * type = type
	 * </p>
	 * <p>
	 * Type = type
	 * </p>
	 * 
	 * @param properties
	 */
	public static Map<String, Object> handlePropertiesName(Map<String, Object> properties) {
		return handlePropertiesName(properties, "");
	}

	/**
	 * 处理属性名称
	 * <p>
	 * driver-class-name = driverClassName
	 * </p>
	 * <p>
	 * Driver-class-name = driverClassName
	 * </p>
	 * <p>
	 * type = type
	 * </p>
	 * <p>
	 * Type = type
	 * </p>
	 * 
	 * @param properties
	 * @param prefix
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> handlePropertiesName(Map<String, Object> properties, String prefix) {
		List<String> keys = new ArrayList<>(properties.keySet());
		if (prefix == null) {
			prefix = "";
		}
		for (String key : keys) {
			Object value = properties.get(key);
			String cfgKey = prefix + StringUtils.configurePropertieName(key);
			properties.remove(key);
			properties.put(cfgKey, value);
			if (value instanceof Map) {
				handlePropertiesName((Map<String, Object>) value, prefix);
			}
		}
		return properties;
	}

	/**
	 * 解析属性
	 * 
	 * @param propertiesContext
	 *            属性内容
	 * @param isTree
	 *            是否为树
	 * @return 返回属性 Map 树，即存在父子级关系,value = String or Map , isTree = false 时，永远为
	 *         String
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseYml(String propertiesContext, boolean isTree) {
		if (StringUtils.isNullOrBlank(propertiesContext)) {
			return new LinkedHashMap<>(0);
		}
		try {
			Yaml aml = new Yaml();
			Map<String, Object> result = aml.loadAs(propertiesContext, Map.class);
			handlePropertiesName(result);
			if (!isTree) {
				return showProperties(result);
			}
			return result;
		} catch (Exception e) {
			throw ExceptionUtils.throwFormatException("yml 格式不正确:" + e.getMessage(), e);
		}
	}

	/**
	 * 解析属性
	 * 
	 * @param propertiesContext
	 *            属性内容
	 * @param isTree
	 *            是否为树
	 * @return 返回属性 Map 树，即存在父子级关系,value = String or Map , isTree = false 时，永远为
	 *         String
	 *
	 */
	public static Map<String, Object> parseProperties(String propertiesContext, boolean isTree) {
		if (StringUtils.isNullOrBlank(propertiesContext)) {
			return new LinkedHashMap<>(0);
		}
		Properties pro = new Properties();
		StringReader reader = null;
		try {
			reader = new StringReader(propertiesContext);
			pro.load(reader);
			Map<String, Object> treeMap = new LinkedHashMap<>(pro.size());
			for (Map.Entry<Object, Object> entry : pro.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					String keyString = StringUtils.configurePropertieName(entry.getKey().toString());
					if (isTree) {
						String[] keys = keyString.split("\\.");
						if (keys.length > 1) {
							Map<String, Object> lastMap = findOrCreateParent(keys, 0, treeMap);
							lastMap.put(keys[keys.length - 1], entry.getValue().toString().trim());
						} else {
							treeMap.put(keyString, entry.getValue().toString().trim());
						}
					} else {
						treeMap.put(keyString, entry.getValue().toString().trim());
					}
				}
			}
			return treeMap;
		} catch (Exception e) {
			throw ExceptionUtils.throwFormatException("properties 格式不正确:" + e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

	/**
	 * 查找或创建父级
	 * 
	 * @param keys
	 * @param index
	 * @param treeMap
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Object> findOrCreateParent(String[] keys, int index, Map<String, Object> treeMap) {
		if (keys.length == 1 || index == keys.length - 1) {
			return treeMap;
		}
		String key = keys[index].trim();
		Map<String, Object> mapValue = (Map<String, Object>) treeMap.get(key);
		if (mapValue == null) {
			mapValue = new LinkedHashMap<>(keys.length);
			treeMap.put(key, mapValue);
		}
		index++;
		return findOrCreateParent(keys, index, mapValue);
	}

}
