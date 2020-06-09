package com.autumn.mybatis.provider.sqlserver;

import com.autumn.mybatis.provider.QueryBuilder;
import com.autumn.mybatis.provider.ProviderDriveType;
import com.autumn.mybatis.provider.annotation.ProviderDrive;
import com.autumn.mybatis.provider.sqlserver.builder.AbstractSqlserverQueryBuilder;
import com.autumn.mybatis.provider.sqlserver.builder.SqlServerQueryBuilder2012;

/**
 * SqlServer 2012 驱动提供者
 *
 * @author shao
 * @date 2017/11/21 15:00
 */
@ProviderDrive(ProviderDriveType.SQL_SERVER)
public class SqlServerProvider2012 extends AbstractSqlServerProvider {

    /**
     * 2012驱动
     */
    protected final AbstractSqlserverQueryBuilder queryBuilder;

    /**
     * SqlServer 2012 查询驱动
     */
    public SqlServerProvider2012() {
        this.queryBuilder = new SqlServerQueryBuilder2012(this);
    }

    @Override
    public QueryBuilder getQueryBuilder() {
        return this.queryBuilder;
    }
}
