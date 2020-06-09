package com.autumn.mybatis.provider;

import com.autumn.mybatis.metadata.EntityTable;

/**
 * 查询生成器
 * 
 * @author 老码农
 *
 *         2017-10-11 09:46:24
 */
public interface QueryBuilder extends Builder {

	/**
	 * 获取根据主键查询单条记录的sql
	 * 
	 * @param table
	 *            表
	 * @return
	 */
	String getSelectByIdCommand(EntityTable table);

	/**
	 * 获取根据主键查询单条记录的锁查询sql
	 * 
	 * @param table
	 *            表
	 * @param keyParamName
	 *            主键参数名
	 * @param lockModeParamName
	 *            锁参数名
	 * @return
	 */
	String getSelectByIdCommand(EntityTable table, String keyParamName, String lockModeParamName);

	/**
	 * 获取根据查询全部记录的sql
	 * 
	 * @param table
	 * @return
	 */
	String getSelectForAllCommand(EntityTable table);

	/**
	 * 查询得到list集合
	 * 
	 * @param table
	 *            表
	 * @param paramName
	 *            参数名称
	 * @return
	 */
	String getSelectForListCommand(EntityTable table, String paramName);

	/**
	 * 查询首条记录
	 * 
	 * @param table
	 *            表
	 * @param paramName
	 *            参数名称
	 * @return
	 */
	String getSelectForFirstCommand(EntityTable table, String paramName);

	/**
	 * 获取全部记录条数的sql
	 * 
	 * @param table
	 * @return
	 */
	String getCountCommand(EntityTable table);	

	/**
	 * 根据条件查询记录条数的sql
	 * 
	 * @param table
	 *            表
	 * @param paramName
	 *            参数名称
	 * @return
	 */
	String getCountByWhereCommand(EntityTable table, String paramName);

	/**
	 * 指定查询
	 * 
	 * @param table
	 *            表
	 * @param paramName
	 *            参数
	 * @return
	 */
	String getSelectForMapListCommand(EntityTable table, String paramName);

	/**
	 * 获取首条记录
	 * 
	 * @param table
	 *            表
	 * @param paramName
	 *            参数
	 * @return
	 */
	String getSelectForMapFirstCommand(EntityTable table, String paramName);

	/**
	 * 获取健康检查Sql
	 * 
	 * @return
	 *
	 */
	String getHealthyCheckSql();
}
