package com.autumn.mybatis.factory;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.provider.DbProvider;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于路油 Key 动态数据源
 *
 * @author 老码农
 * <p>
 * 2018-01-10 16:38:15
 */
public abstract class AbstractDynamicRoutingKeyDataSource extends AbstractDynamicRoutingDataSource
        implements DynamicDataSourceRouting {

    private final Map<String, DataSource> targetDataSources;

    public AbstractDynamicRoutingKeyDataSource(DataSourceFactory dataSourceFactory,
											   DbProvider provider,
                                               String[] typeAliasesPackages,
                                               String[] mapperInterfacePackages) {
        super(dataSourceFactory, provider, typeAliasesPackages, mapperInterfacePackages);
        this.targetDataSources = new ConcurrentHashMap<>();
    }

    @Override
    protected DataSource getCurrentDataSource() {
        return this.targetDataSources.computeIfAbsent(this.getRoutingKey(), (key) -> {
            DataSource ds = this.getDataSourceFactory().getDataSource(key, this.getProvider());
            if (ds == null) {
                ExceptionUtils.throwSystemException("当前 routingKey = " + key + " 对应的 dataSource 值为 null");
            }
            this.addDataSourceDestroy(ds);
            return ds;
        });
    }

}
