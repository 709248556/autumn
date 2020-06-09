package com.autumn.mybatis.factory;

import com.autumn.mybatis.provider.DbProvider;

import javax.sql.DataSource;

/**
 * 数据源工厂
 * 
 * @author 老码农
 *
 *         2017-12-29 17:00:54
 */
public interface DataSourceFactory {

	/**
	 * 获取数据源
	 * 
	 * @param routingKey
	 *            路油键
	 * @param provider
	 *            驱动提供
	 * @return
	 *
	 */
	DataSource getDataSource(Object routingKey, DbProvider provider);
}
