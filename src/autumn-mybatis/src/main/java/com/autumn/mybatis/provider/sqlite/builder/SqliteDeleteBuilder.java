package com.autumn.mybatis.provider.sqlite.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.builder.AbstractDeleteBuilder;
import com.autumn.mybatis.provider.sqlite.SqliteProvider;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * Sqlite 删除生成器
 *
 * @author 老码农
 * <p>
 * 2017-10-19 08:41:06
 */
public class SqliteDeleteBuilder extends AbstractDeleteBuilder {

    /**
     * @param dbProvider
     */
    public SqliteDeleteBuilder(SqliteProvider dbProvider) {
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
