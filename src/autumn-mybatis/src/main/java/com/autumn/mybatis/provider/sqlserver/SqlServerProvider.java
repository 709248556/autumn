package com.autumn.mybatis.provider.sqlserver;

import com.autumn.mybatis.provider.*;
import com.autumn.mybatis.provider.annotation.ProviderDrive;
import com.autumn.mybatis.provider.sqlserver.builder.*;

/**
 * SqlServer 驱动提供者
 *
 * @author shao
 * @date 2017/11/21 15:00
 */
@ProviderDrive(ProviderDriveType.SQL_SERVER)
public class SqlServerProvider extends AbstractSqlServerProvider {

    /**
     * 2005查询驱动
     */
    private final AbstractSqlserverQueryBuilder queryBuilder;

    /**
     *
     */
    public SqlServerProvider() {
        this.queryBuilder = new SqlServerQueryBuilder(this);
    }

    @Override
    public QueryBuilder getQueryBuilder() {
        return this.queryBuilder;
    }

}
