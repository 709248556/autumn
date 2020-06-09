package com.autumn.mybatis.provider;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;

/**
 * 插入生成器
 * 
 * @author 老码农
 *
 *         2017-10-11 09:47:06
 */
public interface InsertBuilder extends Builder {

	/**
	 * 获取单条插入Sql
	 * 
	 * @param table
	 *            表
	 * @return
	 */
	String getInsertCommand(EntityTable table);

	/**
	 * 获取批量插入Sql
	 *
	 * @param table
	 *            表
	 * @param listParmaName
	 *            列表参数名称
	 * @return
	 */
	String getInsertByListCommand(EntityTable table, String listParmaName);

	/**
	 * 获取 Identity 查询key的信息
	 *
	 * @param keyColumn
	 * @return
	 */
	InsertSelectKey getIdentitySelectKey(EntityColumn keyColumn);

}
