package com.autumn.mybatis.provider.sqlserver.builder;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.InsertSelectKey;
import com.autumn.mybatis.provider.builder.AbstractInsertBuilder;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;
import com.autumn.mybatis.provider.util.MybatisXmlUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.tuple.TupleTwo;

/**
 * SqlServer 插入生成器
 *
 * @author 老码农
 * <p>
 * 2017-10-19 08:32:04
 */
public class SqlServerInsertBuilder extends AbstractInsertBuilder {

    /**
     * @param dbProvider
     */
    public SqlServerInsertBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public InsertSelectKey getIdentitySelectKey(EntityColumn keyColumn) {
        return new InsertSelectKey(false, "SELECT @@identity");
    }

    /**
     * @param table
     * @return
     */
    private TupleTwo<String, String> insertValues(EntityTable table) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sql2 = new StringBuilder();
        sql.append("<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">");
        for (EntityColumn column : table.getColumns()) {
            if (!column.isInsertable()) {
                continue;
            }
            if (column.isIdentityAssignKey()) {
                sql2.append(MybatisXmlUtils.createBindCacheNode(column));
                continue;
            } else {
                sql.append(column.getColumnHolder());
            }
            sql.append(",");
        }
        sql.append("</trim>");
        return new TupleTwo<>(sql.toString(), sql2.toString());
    }

    private String insertColumns(EntityTable table, boolean skipNullValue) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (EntityColumn column : table.getColumns()) {
            if (!column.isInsertable() || column.isIdentityAssignKey()) {
                continue;
            }
            if (skipNullValue) {
                sql.append(MybatisXmlUtils.createIfTestValueNotNullNode(column, false, column.getColumnName(), false));
            } else {
                sql.append(column.getColumnName());
            }
            sql.append(",");
        }
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * 单条插入
     */
    @Override
    public String getInsertCommand(EntityTable table) {
        StringBuilder sql = new StringBuilder();
        TupleTwo<String, String> tSql = insertValues(table);
        if (!StringUtils.isNullOrBlank(tSql.getItem2())) {
            sql.append(tSql.getItem2());
        }
        sql.append("INSERT INTO ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider()));
        sql.append(" ");
        sql.append(insertColumns(table, false));
        sql.append(tSql.getItem1());
        return sql.toString();
    }

    /**
     * 批量插入值集合(values)
     *
     * @param table         表
     * @param skipId        跳过 Id
     * @param skipNullValue 跳过 null 值
     * @return
     */
    private String batchInsertValues(EntityTable table, String listParmaName, boolean skipId, boolean skipNullValue) {
        StringBuilder sql = new StringBuilder();
        sql.append("VALUES");
        sql.append("<foreach collection =\"" + listParmaName + "\" item=\"param\" index= \"index\" separator =\",\"> ");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> ");
        for (EntityColumn column : table.getColumns()) {
            if (!column.isInsertable() || column.isIdentityAssignKey()) {
                continue;
            }
            sql.append(column.getColumnHolder(false, "param.", null));
            sql.append(",");
        }
        sql.append("</trim>");
        sql.append("</foreach>");
        return sql.toString();
    }

    /**
     * 批量插入
     *
     * @param table 表
     * @return
     */
    @Override
    public String getInsertByListCommand(EntityTable table, String listParmaName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test='" + listParmaName + " !=null and " + listParmaName + ".size()>0'>");
        sql.append("INSERT INTO ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider()));
        sql.append(" ");
        sql.append(insertColumns(table, false));
        sql.append(batchInsertValues(table, listParmaName, false, false));
        sql.append("</if>");
        return sql.toString();
    }

}
