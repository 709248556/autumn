package com.autumn.mybatis.mapper;

import java.util.Collections;
import java.util.List;

/**
 * 查询结果映射
 * 
 * @author 老码农
 *
 *         2017-10-30 12:34:13
 */
public class QueryResultMap {
	private final String id;
	private final Class<?> type;
	private final List<QueryResultMapping> resultMappings;

	/**
	 * 
	 * @param id
	 * @param type
	 * @param resultMappings
	 */
	public QueryResultMap(String id, Class<?> type, List<QueryResultMapping> resultMappings) {
		this.id = id;
		this.type = type;
		this.resultMappings = Collections.unmodifiableList(resultMappings);
	}

	/**
	 * 获取 Id
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 获取类型
	 * 
	 * @return
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * 获取地图
	 * 
	 * @return
	 */
	public List<QueryResultMapping> getResultMappings() {
		return resultMappings;
	}

}
