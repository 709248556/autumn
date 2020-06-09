package com.autumn.mybatis.provider.sqlserver.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.builder.AbstractQueryBuilder;
import com.autumn.mybatis.provider.sqlserver.AbstractSqlServerProvider;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * @author shao
 * @date 2017/11/21 19:24
 */
public abstract class AbstractSqlserverQueryBuilder extends AbstractQueryBuilder {
    /**
     * @param dbProvider
     */
    public AbstractSqlserverQueryBuilder(AbstractSqlServerProvider dbProvider) {
        super(dbProvider);
    }

    /**
     * 锁
     *
     * @param lockModeName
     * @return
     */
    protected static String lockMode(String lockModeName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<if test=\"%1$s != null \">", lockModeName));
        sql.append("<choose>");
        sql.append(String.format("<when test=\"%1$s.name == 'SHARE'\">", lockModeName));
        sql.append("WITH(HOLDLOCK)");
        sql.append("</when>");
        sql.append(String.format("<when test=\"%1$s.name == 'UPDATE'\">", lockModeName));
        sql.append("WITH(UPDLOCK)");
        sql.append("</when>");
        sql.append("</choose>");
        sql.append("</if>");
        return sql.toString();
    }

    @Override
    public String getSelectByIdCommand(EntityTable table, String keyParamName, String lockModeParamName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(MybatisSqlUtils.getAllColumns(table, this.getProvider()));
        sql.append(" FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider()));
        sql.append(lockMode(lockModeParamName));
        sql.append(MybatisSqlUtils.wherePrimaryKeys(table, keyParamName, this.getProvider()));
        return sql.toString();
    }

    /**
     * 查询返回一条数据
     *
     * @param table     表
     * @param paramName 参数名称
     * @return
     */
    @Override
    public String getSelectForFirstCommand(EntityTable table, String paramName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP 1 ");
        sql.append(MybatisSqlUtils.getSelectColumns(this.getProvider(), paramName, false));
        sql.append(" FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), paramName));
        sql.append(lockMode(String.format("%s.lockMode", paramName)));
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(paramName));
        sql.append(MybatisSqlUtils.getGroupBy(this.getProvider(), paramName));
        sql.append(this.getProvider().getXmlBuilder().getHavingCommand(paramName));
        sql.append(MybatisSqlUtils.getOrderBy(this.getProvider(), paramName));
        return sql.toString();
    }

    /**
     * 根据条件统计
     *
     * @param table     表
     * @param paramName 参数名称
     * @return
     */
    @Override
    public String getCountByWhereCommand(EntityTable table, String paramName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), paramName));
        sql.append(lockMode(String.format("%s.lockMode", paramName)));
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(paramName));
        return sql.toString();
    }
}
