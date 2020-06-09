package com.autumn.mybatis.provider.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.UpdateBuilder;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * 更新生成器
 * 
 * @author 老码农
 *
 *         2017-10-19 08:34:16
 */
public abstract class AbstractUpdateBuilder extends AbstractBuilder implements UpdateBuilder {

	/**
	 * 
	 * @param dbProvider
	 */
	public AbstractUpdateBuilder(DbProvider dbProvider) {
		super(dbProvider);
	}

	/**
	 * 獲取根据主键更新的sql,默认跳过空值
	 * 
	 * @param table
	 * @return
	 */
	@Override
	public String getUpdateCommand(EntityTable table) {
		return MybatisSqlUtils.updateByPrimaryKeys(table, true, this.getProvider());
	}

	/**
	 * 获取更具主键更新的sql
	 * 
	 * @param table
	 * @param skipNullValue 是否跳过空值
	 * @return
	 */
	@Override
	public String getUpdateCommand(EntityTable table, boolean skipNullValue) {
		return MybatisSqlUtils.updateByPrimaryKeys(table, skipNullValue, this.getProvider());
	}

}
