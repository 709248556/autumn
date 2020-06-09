package com.autumn.mybatis.factory;

import com.autumn.mybatis.provider.DbProvider;

import javax.sql.DataSource;

/**
 * 动态单路油数据源
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-30 15:12:53
 */
public class DynamicSingleRoutingDataSource extends AbstractDynamicRoutingDataSource {

    /**
     *
     * @param dataSourceFactory
     * @param provider
     * @param typeAliasesPackages
     * @param mapperInterfacePackages
     */
    public DynamicSingleRoutingDataSource(DataSourceFactory dataSourceFactory,
                                          DbProvider provider,
                                          String[] typeAliasesPackages,
                                          String[] mapperInterfacePackages) {
        super(dataSourceFactory, provider, typeAliasesPackages, mapperInterfacePackages);
    }

    private DataSource singleDataSource = null;

    @Override
    public String getRoutingKey() {
        return null;
    }

    @Override
    protected DataSource getCurrentDataSource() {
        if (this.singleDataSource != null) {
            return this.singleDataSource;
        }
        synchronized (this) {
            if (this.singleDataSource != null) {
                return this.singleDataSource;
            }
            this.singleDataSource = this.getDataSourceFactory().getDataSource(this.getRoutingKey(), this.getProvider());
            this.addDataSourceDestroy(singleDataSource);
            return this.singleDataSource;
        }
    }
}
