package com.autumn.mybatis.provider.postgresql.builder;

import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.builder.AbstractCommandBuilder;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-05-06 17:33
 **/
public class PostgreSqlCommandBuilder extends AbstractCommandBuilder {

    /**
     * @param dbProvider
     */
    public PostgreSqlCommandBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public String getProcedureCommand(String functionParmaName) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL ");
        sql.append("${" + functionParmaName + ".functionName}");
        sql.append("(");
        sql.append("<foreach collection=\"" + functionParmaName + ".param\"  item=\"item\" separator=\",\">");
        sql.append("#{item}");
        sql.append("</foreach>");
        sql.append(")");
        return sql.toString();
    }
}
