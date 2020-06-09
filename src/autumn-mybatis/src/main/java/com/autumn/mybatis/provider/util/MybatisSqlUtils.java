package com.autumn.mybatis.provider.util;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.wrapper.PolymerizationEnum;
import com.autumn.util.StringUtils;

import javax.persistence.GenerationType;

/**
 * @author 老码农
 * <p>
 * 2017-10-19 15:08:54
 */
public class MybatisSqlUtils {

    /**
     * 获取表或视图名称
     *
     * @param table      表
     * @param dbProvider 驱动
     * @return
     */
    public static String getTableOrViewName(EntityTable table, DbProvider dbProvider) {
        boolean isViewCode = table.isView() && !StringUtils.isNullOrBlank(table.getViewQueryStatement());
        if (isViewCode) {
            String viewCode = table.getViewQueryStatement().trim();
            if (!viewCode.startsWith("(")) {
                return String.format("(%s) AS %s", viewCode, dbProvider.getSafeName(table.getName()));
            }
        }
        return dbProvider.getSafeTableName(table);
    }

    /**
     * 获取表或视图名称
     *
     * @param table      表名称
     * @param dbProvider 驱动
     * @param parmaName  参数名称
     * @return
     */
    public static String getTableOrViewName(EntityTable table, DbProvider dbProvider, String parmaName) {
        boolean isViewCode = table.isView() && !StringUtils.isNullOrBlank(table.getViewQueryStatement());
        if (isViewCode) {
            String viewCode = table.getViewQueryStatement().trim();
            StringBuilder sql = new StringBuilder();
            sql.append(String.format("(%s) AS ", viewCode));
            sql.append("<choose>");
            sql.append(String.format("<when test=\"%1$s.alias != null\">", parmaName));
            sql.append(String.format("%s${%s.alias}%s", dbProvider.getSafeNamePrefix(), parmaName,
                    dbProvider.getSafeNameSuffix()));
            sql.append("</when>");
            sql.append("<otherwise>");
            sql.append(dbProvider.getSafeName(table.getName()));
            sql.append("</otherwise>");
            sql.append("</choose>");
            return sql.toString();
        } else {
            StringBuilder sql = new StringBuilder();
            sql.append(dbProvider.getSafeTableName(table));
            sql.append(String.format(" <if test=\"%1$s.alias != null\">", parmaName));
            sql.append(String.format(" AS %s${%s.alias}%s", dbProvider.getSafeNamePrefix(), parmaName,
                    dbProvider.getSafeNameSuffix()));
            sql.append("</if>");
            return sql.toString();
        }
    }

    /**
     * 插入列集合(Insert into table())
     *
     * @param table         表
     * @param skipId        跳过 Id
     * @param skipNullValue 跳过 null 值
     * @return
     */
    public static String insertColumns(EntityTable table, boolean skipId, boolean skipNullValue,
                                       DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (EntityColumn column : table.getColumns()) {
            if (!column.isInsertable()) {
                continue;
            }
            if (skipId && column.isPrimaryKey()) {
                continue;
            }
            if (skipNullValue) {
                sql.append(MybatisXmlUtils.createIfTestValueNotNullNode(column, false,
                        dbProvider.getSafeName(column.getColumnName()), false));
            } else {
                sql.append(dbProvider.getSafeName(column.getColumnName()));
            }
            sql.append(",");
        }
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * 插入值集合(values)
     *
     * @param table         表
     * @param skipId        跳过 Id
     * @param skipNullValue 跳过 null 值
     * @return
     */
    public static String insertValues(EntityTable table, boolean skipId, boolean skipNullValue) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">");
        for (EntityColumn column : table.getColumns()) {
            if (!column.isInsertable()) {
                continue;
            }
            if (column.isPrimaryKey() && column.getGenerationType().equals(GenerationType.IDENTITY)) {
                sql.append("null");
            } else {
                sql.append(column.getColumnHolder());
            }
            sql.append(",");
        }
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * 插入列与值
     *
     * @param table         表
     * @param skipId        跳过 Id
     * @param skipNullValue 跳过 null 值
     * @return
     */
    public static String insert(EntityTable table, boolean skipId, boolean skipNullValue, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(getTableOrViewName(table, dbProvider));
        sql.append(" ");
        sql.append(insertColumns(table, skipId, skipNullValue, dbProvider));
        sql.append(insertValues(table, skipId, skipNullValue));
        return sql.toString();
    }

    /**
     * 更新列集合
     *
     * @param table         表
     * @param skipNullValue 跳过null值
     * @return
     */
    public static String updateSetColumns(EntityTable table, boolean skipNullValue, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"SET\" suffixOverrides=\",\">");
        for (EntityColumn column : table.getColumns()) {
            if (column.isPrimaryKey() || !column.isUpdatable()) {
                continue;
            }
            if (skipNullValue) {
                sql.append(MybatisXmlUtils.createIfTestValueNotNullNode(column, false,
                        column.getColumnEqualsHolder(dbProvider), false));
            } else {
                sql.append(column.getColumnEqualsHolder(dbProvider));
                sql.append(",");
            }
        }
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * 主键条件集合
     *
     * @param table 表
     * @return
     */
    public static String wherePrimaryKeys(EntityTable table, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        for (EntityColumn column : table.getColumns()) {
            if (column.isPrimaryKey()) {
                sql.append(" AND ");
                sql.append(column.getColumnEqualsHolder(dbProvider));
            }
        }
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 获取主键值
     *
     * @param table
     * @param keyParamName
     * @return
     */
    public static String wherePrimaryKeys(EntityTable table, String keyParamName, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        for (EntityColumn column : table.getColumns()) {
            if (column.isPrimaryKey()) {
                sql.append(" AND ");
                sql.append(String.format("%s", dbProvider.getSafeName(column.getColumnName())));
                sql.append("=");
                sql.append(String.format("#{%s}", keyParamName));
            }
        }
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 基于主键更新
     *
     * @param table         表
     * @param skipNullValue 跳过 null 值
     * @return
     */
    public static String updateByPrimaryKeys(EntityTable table, boolean skipNullValue, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(getTableOrViewName(table, dbProvider));
        sql.append(" ");
        sql.append(updateSetColumns(table, skipNullValue, dbProvider));
        sql.append(wherePrimaryKeys(table, dbProvider));
        return sql.toString();
    }

    /**
     * 截断表数据
     *
     * @param table 表
     * @return
     */
    public static String truncateByTable(EntityTable table, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("TRUNCATE ");
        sql.append(getTableOrViewName(table, dbProvider));
        return sql.toString();
    }

    /**
     * 删除所有
     *
     * @param table 表
     * @return
     */
    public static String deleteByAll(EntityTable table, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(getTableOrViewName(table, dbProvider));
        return sql.toString();
    }

    /**
     * 删除
     *
     * @param table 表
     * @return
     */
    public static String deleteByPrimaryKey(EntityTable table, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(getTableOrViewName(table, dbProvider));
        sql.append(" ");
        sql.append(wherePrimaryKeys(table, dbProvider));
        return sql.toString();
    }

    /**
     * 获取所有列
     *
     * @param table
     * @return
     */
    public static String getAllColumns(EntityTable table, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        int index = 0;
        for (EntityColumn column : table.getColumns()) {
            if (index > 0) {
                sql.append(",");
            }
            sql.append(dbProvider.getSafeName(column.getColumnName()));
            index++;
        }
        return sql.toString();
    }

    /**
     * 获取查询列
     *
     * @param dbProvider     提供程序
     * @param parmaName      参数名称
     * @param isSupportAlias 支持别名
     * @return
     */
    public static String getSelectColumns(DbProvider dbProvider, String parmaName, boolean isSupportAlias) {
        String columnClause = "column";
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefixOverrides=\",\">");
        sql.append("<foreach collection=\"" + parmaName + ".selectColumns\" item=\"" + columnClause
                + "\" separator=\",\">");
        String columnName = String.format("%s${%s.columnName}%s", dbProvider.getSafeNamePrefix(), columnClause,
                dbProvider.getSafeNameSuffix());
        sql.append("<choose>");
        for (PolymerizationEnum value : PolymerizationEnum.values()) {
            sql.append(String.format("<when test=\"%1$s.funName == '%2$s'\">", columnClause, value.getValue()));
            if (value.equals(PolymerizationEnum.COUNT)) {
                sql.append("COUNT(*)");
            } else {
                sql.append(value.getValue() + "(" + columnName + ")");
            }
            sql.append(getFunctionColumnAlias(dbProvider, columnClause, columnName, isSupportAlias));
            sql.append("</when>");
        }
        sql.append("<otherwise>");
        sql.append(columnName);
        if (isSupportAlias) {
            sql.append(String.format("<if test=\"%1$s.alias != null\">", columnClause));
            sql.append(String.format(" AS %s${%s.alias}%s", dbProvider.getSafeNamePrefix(), columnClause, dbProvider.getSafeNameSuffix()));
            sql.append("</if>");
        }
        sql.append("</otherwise>");
        sql.append("</choose>");
        sql.append("</foreach>");
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * 获取函数列别名
     *
     * @param dbProvider     提供程序
     * @param columnClause
     * @param columnName
     * @param isSupportAlias
     * @return
     */
    private static String getFunctionColumnAlias(DbProvider dbProvider, String columnClause, String columnName, boolean isSupportAlias) {
        StringBuilder sql = new StringBuilder();
        if (isSupportAlias) {
            sql.append("<choose>");
            sql.append(String.format("<when test=\"%1$s.alias != null\">", columnClause));
            sql.append(String.format(" AS %s${%s.alias}%s", dbProvider.getSafeNamePrefix(), columnClause,
                    dbProvider.getSafeNameSuffix()));
            sql.append("</when>");
            sql.append("<otherwise>");
            sql.append(" AS " + columnName);
            sql.append("</otherwise>");
            sql.append("</choose>");
        } else {
            sql.append(" AS " + columnName);
        }
        return sql.toString();
    }

    /**
     * 获取 Group By
     *
     * @param dbProvider 提供驱动
     * @param parmaName  参数名称
     * @return
     */
    public static String getGroupBy(DbProvider dbProvider, String parmaName) {
        String fullCollectionName = parmaName + ".groups";
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<if test=\"%1$s.size > 0\">", fullCollectionName));
        sql.append("<trim prefix=\"GROUP BY\" prefixOverrides=\",\">");
        sql.append("<foreach collection=\"" + fullCollectionName + "\" item=\"item\" separator=\",\">");
        sql.append(String.format("%s${item.columnName}%s", dbProvider.getSafeNamePrefix(), dbProvider.getSafeNameSuffix()));
        sql.append("</foreach>");
        sql.append("</trim>");
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 获取 Order By
     *
     * @param dbProvider 提供驱动
     * @param parmaName  参数名称
     * @return
     */
    public static String getOrderBy(DbProvider dbProvider, String parmaName) {
        String fullCollectionName = parmaName + ".orderClauses";
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<if test=\"%1$s.size > 0\">", fullCollectionName));
        sql.append("<trim prefix=\"ORDER BY\" prefixOverrides=\",\">");
        sql.append("<foreach collection=\"" + fullCollectionName + "\" item=\"item\" separator=\",\">");
        sql.append("<choose>");
        sql.append("<when test=\"item.funName == null\">");
        sql.append(String.format("%s${item.columnName}%s ${item.direction}", dbProvider.getSafeNamePrefix(), dbProvider.getSafeNameSuffix()));
        sql.append("</when>");
        sql.append("<when test=\"item.funName == 'COUNT'\">");
        sql.append("${item.funName}(*) ${item.direction}");
        sql.append("</when>");
        sql.append("<otherwise>");
        sql.append(String.format("${item.funName}(%s${item.columnName}%s) ${item.direction}", dbProvider.getSafeNamePrefix(), dbProvider.getSafeNameSuffix()));
        sql.append("</otherwise>");
        sql.append("</choose>");
        sql.append("</foreach>");
        sql.append("</trim>");
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 基于主键查询
     *
     * @param table
     * @param dbProvider
     * @return
     */
    public static String selectByPrimaryKey(EntityTable table, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getAllColumns(table, dbProvider));
        sql.append(" FROM ");
        sql.append(getTableOrViewName(table, dbProvider));
        sql.append(wherePrimaryKeys(table, dbProvider));
        return sql.toString();
    }

    /**
     * 全部查询
     *
     * @param table 表
     * @return
     */
    public static String selectAll(EntityTable table, DbProvider dbProvider) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getAllColumns(table, dbProvider));
        sql.append(" FROM ");
        sql.append(getTableOrViewName(table, dbProvider));
        if (!StringUtils.isNullOrBlank(table.getOrderByClause())) {
            sql.append(" ORDER BY ");
            sql.append(table.getOrderByClause());
        }
        return sql.toString();
    }

    /**
     * 全部查询记录数
     *
     * @param table 表
     * @return
     */
    public static String selectAllCount(EntityTable table, DbProvider dbProvider) {
        return "SELECT COUNT(*) FROM " + getTableOrViewName(table, dbProvider);
    }

    public static String selectColumns(EntityTable table, boolean skipId, boolean skipNullValue) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\"and\">");
        for (EntityColumn column : table.getColumns()) {
            if (skipNullValue) {
                sql.append(MybatisXmlUtils.createIfTestValueNotNullNode(column, false, column.getColumnName(), false));
            } else {
                sql.append(column.getColumnName());
                sql.append(",");
            }
        }
        sql.append("</trim>");

        return sql.toString();
    }

}
