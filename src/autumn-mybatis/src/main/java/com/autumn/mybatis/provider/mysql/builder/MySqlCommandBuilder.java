package com.autumn.mybatis.provider.mysql.builder;

import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.builder.AbstractCommandBuilder;

/**
 * 
 * 
 * @author 老码农 2018-04-01 00:50:10
 */
public class MySqlCommandBuilder extends AbstractCommandBuilder {

	public MySqlCommandBuilder(DbProvider dbProvider) {
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
