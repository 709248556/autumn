package com.autumn.mybatis.provider.postgresql.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.builder.AbstractUpdateBuilder;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-05-06 17:35
 **/
public class PostgreSqlUpdateBuilder extends AbstractUpdateBuilder {

    /**
     * @param dbProvider
     */
    public PostgreSqlUpdateBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public String getUpdateByWhereCommand(EntityTable table, String paraName) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), paraName));
        sql.append("<set>");
        sql.append("<foreach collection=\"" + paraName + ".updateClauses\" item=\"item\" separator=\",\">");
        sql.append(String.format("%s${item.columnName}%s = #{item.value}", this.getProvider().getSafeNamePrefix(),
                this.getProvider().getSafeNameSuffix()));
        sql.append("</foreach>");
        sql.append("</set>");
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(paraName));
        return sql.toString();
    }
}
