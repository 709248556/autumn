package com.autumn.mybatis.provider.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.InsertBuilder;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * 插入生成器
 * 
 * @author 老码农
 *
 *         2017-10-19 08:27:39
 */
public abstract class AbstractInsertBuilder extends AbstractBuilder implements InsertBuilder {

	/**
	 * 
	 * @param dbProvider
	 */
	public AbstractInsertBuilder(DbProvider dbProvider) {
		super(dbProvider);
	}

	/**
	 * 获取插入Sql
	 * 
	 * @param table 表
	 * @return
	 */
	@Override
	public String getInsertCommand(EntityTable table) {
		return MybatisSqlUtils.insert(table, false, false, this.getProvider());
	}

}
