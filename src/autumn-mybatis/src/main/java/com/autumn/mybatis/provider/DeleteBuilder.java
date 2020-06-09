package com.autumn.mybatis.provider;

import com.autumn.mybatis.metadata.EntityTable;

/**
 * 删除生成器
 * 
 * @author 老码农
 *
 *         2017-10-11 09:48:26
 */
public interface DeleteBuilder extends Builder {

	/**
	 * 获取快速清除表数据的Sql
	 * 
	 * @param table
	 * @return
	 */
	String getTruncateCommand(EntityTable table);
	
	/**
	 * 获取删除所有数据的Sql
	 * 
	 * @param table
	 * @return
	 */
	String getDeleteByAllCommand(EntityTable table);
	
	/**
	 * 获取根据主键删除记录的sql
	 * 
	 * @param table
	 * @return
	 */
	String getDeleteByIdCommand(EntityTable table);

	/**
	 * 获取根据条件删除记录的sql
	 * 
	 * @param table
	 *            表
	 * @param parmaName
	 *            参数名称
	 * @return
	 */
	String getDeleteByWhereCommand(EntityTable table, String parmaName);
}
