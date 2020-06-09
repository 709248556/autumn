package com.autumn.mybatis.provider.sqlserver.builder;

import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.builder.AbstractCommandBuilder;

/**
 * 
 * 
 * @author 老码农 2018-04-01 00:51:29
 */
public class SqlServerCommandBuilder extends AbstractCommandBuilder {

	public SqlServerCommandBuilder(DbProvider dbProvider) {
		super(dbProvider);
	}

	@Override
	public String getProcedureCommand(String functionParmaName) {
		StringBuilder sql = new StringBuilder();
		sql.append("EXEC ");
		sql.append("${" + functionParmaName + ".functionName}");
		sql.append("(");
		sql.append("<foreach collection=\"" + functionParmaName + ".param\"  item=\"item\" separator=\",\">");
		sql.append("#{item}");
		sql.append("</foreach>");
		sql.append(")");
		return sql.toString();
	}

}
