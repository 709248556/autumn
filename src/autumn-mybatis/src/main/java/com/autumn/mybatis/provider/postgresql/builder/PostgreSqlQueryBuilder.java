package com.autumn.mybatis.provider.postgresql.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.builder.AbstractQueryBuilder;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-05-06 17:35
 **/
public class PostgreSqlQueryBuilder extends AbstractQueryBuilder {

    /**
     * @param dbProvider
     */
    public PostgreSqlQueryBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    /**
     * 锁
     *
     * @param lockModeName
     * @return
     */
    private static String lockMode(String lockModeName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<if test=\"%1$s != null \">", lockModeName));
        sql.append("<choose>");
        sql.append(String.format("<when test=\"%1$s.name == 'SHARE'\">", lockModeName));
        sql.append("FOR SHARE");
        sql.append("</when>");
        sql.append(String.format("<when test=\"%1$s.name == 'UPDATE'\">", lockModeName));
        sql.append("FOR UPDATE");
        sql.append("</when>");
        sql.append("</choose>");
        sql.append("</if>");
        return sql.toString();
    }

    private String getSelectByBaseCommand(EntityTable table, String paramName, boolean isSupportAlias, boolean isFirst) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(MybatisSqlUtils.getSelectColumns(this.getProvider(), paramName, isSupportAlias));
        sql.append(" FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), paramName));
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(paramName));
        sql.append(MybatisSqlUtils.getGroupBy(this.getProvider(), paramName));
        sql.append(this.getProvider().getXmlBuilder().getHavingCommand(paramName));
        sql.append(MybatisSqlUtils.getOrderBy(this.getProvider(), paramName));
        if (isFirst) {
            sql.append(" LIMIT 1");
        } else {
            sql.append(getLimit(paramName));
        }
        sql.append(lockMode(String.format("%s.lockMode", paramName)));
        return sql.toString();
    }

    @Override
    public String getSelectByIdCommand(EntityTable table, String keyParamName, String lockModeParamName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(MybatisSqlUtils.getAllColumns(table, this.getProvider()));
        sql.append(" FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider()));
        sql.append(MybatisSqlUtils.wherePrimaryKeys(table, keyParamName, this.getProvider()));
        sql.append(lockMode(lockModeParamName));
        return sql.toString();
    }

    private String getSelectByListCommand(EntityTable table, String paramName, boolean isSupportAlias) {
        return this.getSelectByBaseCommand(table, paramName, isSupportAlias, false);
    }

    private String getSelectByFirstCommand(EntityTable table, String paramName, boolean isSupportAlias) {
        return this.getSelectByBaseCommand(table, paramName, isSupportAlias, true);
    }

    @Override
    public String getSelectForListCommand(EntityTable table, String paramName) {
        return this.getSelectByListCommand(table, paramName, false);
    }

    @Override
    public String getSelectForFirstCommand(EntityTable table, String paramName) {
        return this.getSelectByFirstCommand(table, paramName, false);
    }

    @Override
    public String getSelectForMapListCommand(EntityTable table, String paramName) {
        return this.getSelectByListCommand(table, paramName, true);
    }

    @Override
    public String getSelectForMapFirstCommand(EntityTable table, String paramName) {
        return this.getSelectByFirstCommand(table, paramName, true);
    }

    @Override
    public String getCountByWhereCommand(EntityTable table, String paramName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), paramName));
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(paramName));
        // FOR UPDATE 不能用于聚合函数中
        // sql.append(lockMode(String.format("%s.lockMode", paramName)));
        return sql.toString();
    }

    /**
     * 分页查询
     *
     * @param parmaName
     * @return
     */
    private String getLimit(String parmaName) {
        String pageClause = parmaName + ".pageClause";
        StringBuilder sql = new StringBuilder();
        sql.append("<choose>");
        sql.append("<when test=\"" + pageClause + ".offset>0 and " + pageClause + ".limit>0\">");
        sql.append("LIMIT #{" + pageClause + ".limit} OFFSET #{" + pageClause + ".offset}");
        sql.append("</when>");
        sql.append("<when test=\"" + pageClause + ".offset>0\">");
        sql.append("OFFSET #{" + pageClause + ".offset}");
        sql.append("</when>");
        sql.append("<when test=\"" + pageClause + ".limit>0\">");
        sql.append("LIMIT #{" + pageClause + ".limit}");
        sql.append("</when>");
        sql.append("</choose>");
        return sql.toString();
    }
}
