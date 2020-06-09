package com.autumn.mybatis.provider.sqlite.builder;

import com.autumn.mybatis.provider.builder.AbstractCommandBuilder;
import com.autumn.mybatis.provider.sqlite.SqliteProvider;
import com.autumn.exception.ExceptionUtils;

/**
 * @author 老码农 2018-04-01 00:50:10
 */
public class SqliteCommandBuilder extends AbstractCommandBuilder {

    public SqliteCommandBuilder(SqliteProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public String getProcedureCommand(String functionParmaName) {
        throw ExceptionUtils.throwNotSupportException("不支持的自定义函数。");
    }

}
