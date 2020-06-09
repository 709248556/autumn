package com.autumn.mybatis.provider;

import com.autumn.mybatis.metadata.EntityTable;

/**
 * 更新生成器
 * 
 * @author 老码农
 *
 *         2017-10-11 09:47:50
 */
public interface UpdateBuilder extends Builder {

	/**
	 * 获取更具主键更新的sql
	 * @param table
	 * @return
	 */
	String getUpdateCommand(EntityTable table);
	
	/**
	 * 获取更具主键更新的sql
	 * @param table
	 * @param skipNullValue  是否跳过空值
	 * @return
	 */
	String getUpdateCommand(EntityTable table, boolean skipNullValue);

	/**
	 * 传入map修改固定的参数
	 * @param table
	 * @param paramName
	 * @return
	 */
	String getUpdateByWhereCommand(EntityTable table , String paramName);
	
}
