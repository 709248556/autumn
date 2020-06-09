package com.autumn.mybatis.provider.util;

import com.autumn.mybatis.metadata.EntityColumn;

/**
 * MyBatis 的 Xml 帮助
 * 
 * @author 老码农
 *
 *         2017-10-17 10:56:01
 */
public class MybatisXmlUtils {

	/**
	 * 绑定缓存标签后缀
	 */
	public static final String BIND_LABEL_CACHE_SUFFIX = "cache";

	/**
	 * 绑定值标签后缀
	 */
	public static final String BIND_LABEL_VALUE_SUFFIX = "bind";

	/**
	 * 创建绑定节点
	 * 
	 * @param name
	 *            名称
	 * @param value
	 *            值 *
	 * @return
	 *         <p>
	 *         &#x3C;bind name="name" value="value" /&#x3E;
	 *         </p>
	 */
	public static String createBindNode(String name, String value) {
		return String.format("<bind name=\"%s\" value=\"%s\" />", name, value);
	}

	/**
	 * 创建绑定缓存节点
	 * 
	 * @param column
	 *            列
	 * @return
	 *         <p>
	 *         &#x3C;bind name="propertyName_cache" value="propertyName" /&#x3E;
	 *         </p>
	 */
	public static String createBindCacheNode(EntityColumn column) {
		return createBindNode(String.format("%s_%s", column.getPropertyName(), BIND_LABEL_CACHE_SUFFIX),
				column.getPropertyName());
	}

	/**
	 * 创建绑定值节点
	 * 
	 * @param column
	 *            列
	 * @param value
	 *            值
	 * @return
	 *         <p>
	 *         &#x3C;bind name="propertyName_bind" value="propertyName" /&#x3E;
	 *         </p>
	 */
	public static String createBindValueNode(EntityColumn column, String value) {
		return createBindNode(String.format("%s_%s", column.getPropertyName(), BIND_LABEL_VALUE_SUFFIX), value);
	}

	/**
	 * 创建 If Test 节点
	 * 
	 * @param condition
	 *            条件
	 * @param content
	 *            内容
	 * @return
	 *         <p>
	 *         &#x3C;if test="condition"&#x3E;content&#x3C;/if&#x3E;
	 *         </p>
	 */
	public static String createIfTestNode(String condition, String content) {
		return String.format("<if test=\"%s\">%s,</if>", condition, content);
	}

	/**
	 * 创建 缓存 if test 为非 null
	 * 
	 * @param column
	 *            列
	 * @param content
	 *            内空
	 * @return
	 */
	public static String createIfTestCacheNotNullNode(EntityColumn column, String content) {
		return createIfTestNode(String.format("%s_%s != null", column.getPropertyName(), BIND_LABEL_CACHE_SUFFIX),
				content);
	}

	/**
	 * 创建 缓存 if test 为 null
	 * 
	 * @param column
	 *            列
	 * @param content
	 *            内空
	 * @return
	 */
	public static String createIfTestCacheIsNullNode(EntityColumn column, String content) {
		return createIfTestNode(String.format("%s_%s == null", column.getPropertyName(), BIND_LABEL_CACHE_SUFFIX),
				content);
	}

	/**
	 * 创建 值 if test 为非 null
	 * 
	 * @param column
	 *            列
	 * @param isContainEntityClass
	 *            是否包含实体类
	 * @param content
	 *            内空
	 * @param isEmpty
	 *            是否只读
	 * @return
	 */
	public static String createIfTestValueNotNullNode(EntityColumn column, boolean isContainEntityClass, String content,
			boolean isEmpty) {
		StringBuilder condition = new StringBuilder();
		String fullPropertyName;
		if (isContainEntityClass) {
			fullPropertyName = String.format("%s.%s", column.getTable().getEntityClass().getName(),
					column.getPropertyName());
		} else {
			fullPropertyName = column.getPropertyName();
		}
		condition.append(fullPropertyName);
		condition.append(" != null");
		if (isEmpty && column.getJavaType().equals(String.class)) {
			condition.append(" and ");
			condition.append(fullPropertyName);
			condition.append(" != ''");
		}
		return createIfTestNode(condition.toString(), content);
	}

	/**
	 * 创建 值 if test 为 null
	 * 
	 * @param column
	 *            列
	 * @param isContainEntityClass
	 *            是否包含实体类
	 * @param content
	 *            内空
	 * @param isEmpty
	 *            是否只读
	 * @return
	 */
	public static String createIfTestValueIsNullNode(EntityColumn column, boolean isContainEntityClass, String content,
			boolean isEmpty) {
		StringBuilder condition = new StringBuilder();
		String fullPropertyName;
		if (isContainEntityClass) {
			fullPropertyName = String.format("%s.%s", column.getTable().getEntityClass().getName(),
					column.getPropertyName());
		} else {
			fullPropertyName = column.getPropertyName();
		}
		condition.append(fullPropertyName);
		condition.append(" == null");
		if (isEmpty && column.getJavaType().equals(String.class)) {
			condition.append(" or ");
			condition.append(fullPropertyName);
			condition.append(" == ''");
		}
		return createIfTestNode(condition.toString(), content);
	}

	/**
	 * 创建 SelectKey 节点
	 * 
	 * @param column
	 *            列
	 * @return
	 */
	public static String createSelectKeyNode(EntityColumn column) {
		StringBuilder node = new StringBuilder();
		node.append("<selectKey keyProperty=");
		node.append(String.format("\"%s\"", column.getPropertyName()));
		node.append(" resultType=");
		node.append(String.format("\"%s\"", column.getJavaType().getCanonicalName()));
		node.append(">");
		node.append("SELECT LAST_INSERT_ID()");
		node.append("</selectKey>");
		return node.toString();
	}

}
