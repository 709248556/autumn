package com.autumn.mybatis.provider.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.QueryBuilder;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * 查询生成器
 * 
 * @author 老码农
 *
 *         2017-10-19 08:38:14
 */
public abstract class AbstractQueryBuilder extends AbstractBuilder implements QueryBuilder {

	/**
	 * 
	 * @param dbProvider
	 */
	public AbstractQueryBuilder(DbProvider dbProvider) {
		super(dbProvider);
	}

	/**
	 * 获取根据主键查询单条记录的sql
	 * 
	 * @param table
	 * @return
	 */
	@Override
	public String getSelectByIdCommand(EntityTable table) {
		return MybatisSqlUtils.selectByPrimaryKey(table, this.getProvider());
	}

	/**
	 * 获取根据查询全部记录的sql
	 * 
	 * @param table
	 * @return
	 */
	@Override
	public String getSelectForAllCommand(EntityTable table) {
		return MybatisSqlUtils.selectAll(table, this.getProvider());
	}

	/**
	 * 获取根据查询全部记录条数的sql
	 * 
	 * @param table
	 * @return
	 */
	@Override
	public String getCountCommand(EntityTable table) {
		return MybatisSqlUtils.selectAllCount(table, this.getProvider());
	}

	@Override
	public String getHealthyCheckSql() {
		return "SELECT 1 AS Healthy";
	}
}
