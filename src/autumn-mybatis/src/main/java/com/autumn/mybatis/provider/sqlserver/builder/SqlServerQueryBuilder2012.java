package com.autumn.mybatis.provider.sqlserver.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.sqlserver.AbstractSqlServerProvider;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * SqlServer 兼容2012 查询生成器
 *
 * @author shao
 * @date 2017/11/21 15:55
 */
public class SqlServerQueryBuilder2012 extends AbstractSqlserverQueryBuilder {

    /**
     * @param dbProvider
     */
    public SqlServerQueryBuilder2012(AbstractSqlServerProvider dbProvider) {
        super(dbProvider);
    }

    private String getSelectByListCommand(EntityTable table, String parmaName, boolean isSupportAlias) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(MybatisSqlUtils.getSelectColumns(this.getProvider(), parmaName, isSupportAlias));
        sql.append(" FROM ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), parmaName));
        sql.append(lockMode(String.format("%s.lockMode", parmaName)));
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(parmaName));
        sql.append(MybatisSqlUtils.getGroupBy(this.getProvider(), parmaName));
        sql.append(this.getProvider().getXmlBuilder().getHavingCommand(parmaName));
        sql.append(MybatisSqlUtils.getOrderBy(this.getProvider(), parmaName));
        sql.append(limit(parmaName));
        return sql.toString();
    }

    /**
     * 分页查询
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
     * 查询指定列
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
     * 根据条件查询返回一条数据
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
     * SqlServer 2012 数据库分页
     *
     * @param parmaName
     * @return
     */
    private String limit(String parmaName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<choose >");
        sql.append("<when test=\"" + parmaName + ".pageClause.offset>0 and " + parmaName + ".pageClause.limit>0\">");
        sql.append("OFFSET (#{" + parmaName + ".pageClause.offset} * #{" + parmaName
                + ".pageClause.limit}) ROW FETCH NEXT #{" + parmaName + ".pageClause.limit} ROWS ONLY");
        sql.append("</when>");
        sql.append("<when test=\"" + parmaName + ".pageClause.offset>0\">");
        sql.append("OFFSET #{" + parmaName + ".pageClause.offset} ROW");
        sql.append("</when>");
        sql.append("<when test=\"" + parmaName + ".pageClause.limit>0\">");
        sql.append("OFFSET 0 ROW FETCH NEXT #{" + parmaName + ".pageClause.limit} ROWS ONLY");
        sql.append("</when>");
        sql.append("</choose>");
        return sql.toString();
    }

}
