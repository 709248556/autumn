package com.autumn.mybatis.provider.postgresql.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.builder.AbstractDeleteBuilder;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-05-06 17:34
 **/
public class PostgreSqlDeleteBuilder extends AbstractDeleteBuilder {

    public PostgreSqlDeleteBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public String getDeleteByWhereCommand(EntityTable table, String parmaName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), parmaName));
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(parmaName));
        return sql.toString();
    }
}
