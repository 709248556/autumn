package com.autumn.mybatis.provider.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.DeleteBuilder;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * 删除生成器
 * 
 * @author 老码农
 *
 *         2017-10-19 08:36:11
 */
public abstract class AbstractDeleteBuilder extends AbstractBuilder implements DeleteBuilder {

	public AbstractDeleteBuilder(DbProvider dbProvider) {
		super(dbProvider);
	}

	@Override
	public String getTruncateCommand(EntityTable table) {
		return MybatisSqlUtils.truncateByTable(table, this.getProvider());
	}

	@Override
	public String getDeleteByAllCommand(EntityTable table) {
		return MybatisSqlUtils.deleteByAll(table, this.getProvider());
	}

	@Override
	public String getDeleteByIdCommand(EntityTable table) {
		return MybatisSqlUtils.deleteByPrimaryKey(table, this.getProvider());
	}
}
