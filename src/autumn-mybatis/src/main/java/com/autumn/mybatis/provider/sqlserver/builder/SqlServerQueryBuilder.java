package com.autumn.mybatis.provider.sqlserver.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.sqlserver.AbstractSqlServerProvider;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * SqlServer 兼容2005 查询生成器
 *
 * @author shao
 * @date 2017/11/21 15:55
 */
public class SqlServerQueryBuilder extends AbstractSqlserverQueryBuilder {

    /**
     * @param dbProvider
     */
    public SqlServerQueryBuilder(AbstractSqlServerProvider dbProvider) {
        super(dbProvider);
    }

    private String getSelectByListCommand(EntityTable table, String parmaName, boolean isSupportAlias) {
        String pageClause = parmaName + ".pageClause";
        StringBuilder sql = new StringBuilder();
        sql.append("<choose>");
        // 分页查询
        sql.append("<when test=\"" + pageClause + ".offset>0 and " + pageClause + ".limit>0\">");
        sql.append(selectPageSql(table, MybatisSqlUtils.getSelectColumns(this.getProvider(), parmaName, isSupportAlias),
                parmaName));
        sql.append("WHERE ");
        sql.append("P.rownum &gt; ((#{" + pageClause + ".offset}-1)* #{" + pageClause + ".limit}) and ");
        // 此处应该是小于等于，所以+1之后就是小于
        sql.append("P.rownum &lt; (#{" + pageClause + ".offset} * #{" + pageClause + ".limit})+1");
        sql.append("</when>");
        // 跳过多少行
        sql.append("<when test=\"" + pageClause + ".offset>0\">");
        sql.append(selectPageSql(table, MybatisSqlUtils.getSelectColumns(this.getProvider(), parmaName, isSupportAlias),
                parmaName));
        sql.append("WHERE P.rownum &gt; #{" + pageClause + ".offset} ");
        sql.append("</when>");
        // 限制查询行数
        sql.append("<when test=\"" + pageClause + ".limit>0\">");
        sql.append(selectTopSql(table, MybatisSqlUtils.getSelectColumns(this.getProvider(), parmaName, isSupportAlias),
                parmaName));
        sql.append("</when>");
        sql.append("<otherwise>");
        // 只做条件查询
        sql.append(selectSql(table, MybatisSqlUtils.getSelectColumns(this.getProvider(), parmaName, isSupportAlias),
                parmaName));
        sql.append("</otherwise>");
        sql.append("</choose>");
        return sql.toString();
    }

    /**
     * 查询一个集合
     *
     * @param table     表
     * @param paramName 参数名称
     * @return
     */
    @Override
    public String getSelectForListCommand(EntityTable table, String paramName) {
        return this.getSelectByListCommand(table, paramName, false);
    }

    /**
     * 查询返回指定列
     *
     * @param table     表
     * @param paramName 参数
     * @return
     */
    @Override
    public String getSelectForMapListCommand(EntityTable table, String paramName) {
        return this.getSelectByListCommand(table, paramName, true);
    }

    /**
     * 分页查询，返回第一个数据
     *
     * @param table     表
     * @param paramName 参数
     * @return
     */
    @Override
    public String getSelectForMapFirstCommand(EntityTable table, String paramName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP 1 ");
        sql.append(MybatisSqlUtils.getSelectColumns(this.getProvider(), paramName, true));
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
     * 分页查询sql
     *
     * @param table
     * @param columns
     * @param paramName
     * @return
     */
    private String selectPageSql(EntityTable table, String columns, String paramName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(columns);
        sql.append(" FROM (");
        sql.append("SELECT ROW_NUMBER() OVER (");
        sql.append(MybatisSqlUtils.getOrderBy(this.getProvider(), paramName));
        sql.append(" )  as rownum , ");
        sql.append(MybatisSqlUtils.getAllColumns(table, this.getProvider()));
        sql.append(" FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), paramName));
        sql.append(lockMode(String.format("%s.lockMode", paramName)));
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(paramName));
        sql.append(") AS P ");
        return sql.toString();
    }

    /**
     * 只有条件查询的sql
     *
     * @param table
     * @param columns
     * @param paramName
     * @return
     */
    private String selectSql(EntityTable table, String columns, String paramName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(columns);
        sql.append(" FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), paramName));
        sql.append(lockMode(String.format("%s.lockMode", paramName)));
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(paramName));
        sql.append(MybatisSqlUtils.getOrderBy(this.getProvider(), paramName));
        return sql.toString();
    }

    /**
     * top 查询sql
     *
     * @param table
     * @param columns
     * @param paramName
     * @return
     */
    private String selectTopSql(EntityTable table, String columns, String paramName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP ");
        sql.append("${" + paramName + ".pageClause.limit} ");
        sql.append(columns);
        sql.append(" FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), paramName));
        sql.append(lockMode(String.format("%s.lockMode", paramName)));
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(paramName));
        sql.append(MybatisSqlUtils.getOrderBy(this.getProvider(), paramName));
        return sql.toString();
    }

}
