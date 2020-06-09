package com.autumn.mybatis.factory;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.provider.DbProvider;

/**
 * 动态名称路油数据源
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-30 12:18:59
 */
public class DynamicNameRoutingDataSource extends AbstractDynamicRoutingKeyDataSource {

	private final String name;

	/**
	 * 
	 * @param dataSourceFactory
	 * @param provider
	 * @param name
	 */
	public DynamicNameRoutingDataSource(DataSourceFactory dataSourceFactory,
										DbProvider provider,
										String[] typeAliasesPackages,
										String[] mapperInterfacePackages,
										String name) {
		super(dataSourceFactory, provider,typeAliasesPackages,mapperInterfacePackages);
		this.name = ExceptionUtils.checkNotNullOrBlank(name, "name");
	}

	@Override
	public String getRoutingKey() {
		return this.name;
	}
}