package com.autumn.mybatis.provider.sqlite.builder;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityIndex;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.builder.AbstractDefinitionBuilder;
import com.autumn.mybatis.provider.sqlite.SqliteProvider;
import com.autumn.util.EnvironmentConstants;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

/**
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2018-01-20 11:56:13
 */
public class SqliteDefinitionBuilder extends AbstractDefinitionBuilder {

    /**
     * @param dbProvider
     */
    public SqliteDefinitionBuilder(SqliteProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public String getColumnType(EntityColumn column) {
        switch (column.getJdbcType()) {
            case BIT:
            case BOOLEAN:
            case SMALLINT:
            case TINYINT:
            case INTEGER:
            case BIGINT:
                return "INTEGER";
            case FLOAT:
            case DOUBLE:
            case REAL:
                return "REAL";
            case NUMERIC:
            case DECIMAL:
                int precision = column.getPrecision();
                if (precision <= 0) {
                    precision = 38;
                }
                int scale = column.getScale();
                if (scale <= 0) {
                    scale = 2;
                }
                return String.format("DECIMAL(%s,%s)", precision, scale);
            case CHAR:
            case NCHAR:
                if (column.getLength() > 0) {
                    return String.format("CHAR(%s)", column.getLength());
                }
                return "CHAR(255)";
            case VARCHAR:
            case NVARCHAR:
                if (column.getLength() > 0) {
                    return String.format("varchar(%s)", column.getLength());
                }
                return "VARCHAR(255)";
            case LONGNVARCHAR:
            case LONGVARCHAR:
            case BLOB:
            case NCLOB:
            case SQLXML:
                return "TEXT";
            case ARRAY:
            case BINARY:
            case CLOB:
            case VARBINARY:
            case LONGVARBINARY:
                return "BLOB";
            case DATE:
            case DATALINK:
            case TIMESTAMP:
            case TIME:
                return "DATETIME";
            default:
                throw ExceptionUtils.throwNotSupportException("不支持 " + column.getJdbcType() + " 的数据库映射。");
        }
    }

    /**
     * 创建列脚本
     *
     * @param column 列
     * @return
     */
    private String createColumnScript(EntityColumn column) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getProvider().getSafeName(column.getColumnName()));
        builder.append(" ").append(this.getColumnType(column));
        if (column.isPrimaryKey()) {
            builder.append(" PRIMARY KEY");
            if (column.isIdentityAssignKey()) {
                builder.append(" autoincrement");
            }
        } else {
            if (column.isNullable()) {
                builder.append(" NULL");
            } else {
                builder.append(" NOT NULL");
            }
        }
        return builder.toString();
    }

    @Override
    public String existTableSqlCommand(String database, EntityTable table) {
        return "SELECT count(*) FROM sqlite_master WHERE type = 'table' and name = '" + table.getName() + "'";
    }

    @Override
    public String createTableScript(EntityTable table, String tableName) {
        if (table.isView()) {
            ExceptionUtils.throwNotSupportException("对象 " + table.getEntityClass().getName() + " 为视图对象，不支持生成表脚本。");
        }
        if (StringUtils.isNullOrBlank(tableName)) {
            tableName = table.getName();
        }
        StringBuilder builder = new StringBuilder();
        String lineSeparator = EnvironmentConstants.LINE_SEPARATOR;
        builder.append(String.format("CREATE TABLE %s (", this.getProvider().getSafeTableName(tableName)));
        for (int i = 0; i < table.getColumns().size(); i++) {
            EntityColumn column = table.getColumns().get(i);
            if (i > 0) {
                builder.append(",");
            }
            builder.append(lineSeparator);
            builder.append("  ");
            builder.append(this.createColumnScript(column));
        }
        builder.append(lineSeparator);
        builder.append(")");
        if (table.getIndexs().size() > 0) {
            for (EntityIndex index : table.getIndexs()) {
                builder.append(lineSeparator);
                builder.append(lineSeparator);
                builder.append(this.getScriptRowSeparator());
                builder.append(lineSeparator);
                builder.append(this.createAnnotation(this.getAnnotationLineSeparator()));
                builder.append(lineSeparator);
                builder.append(this.createAnnotation(
                        "index structure for " + index.getAutoName("IX", tableName) + " to table " + tableName));
                builder.append(lineSeparator);
                builder.append(this.createAnnotation(this.getAnnotationLineSeparator()));
                builder.append(lineSeparator);
                if (index.isUnique()) {
                    builder.append(String.format("CREATE UNIQUE INDEX %s ON %s",
                            this.getProvider().getSafeName(index.getAutoName("IX", tableName)),
                            this.getProvider().getSafeTableName(tableName)));
                } else {
                    builder.append(String.format("CREATE INDEX %s ON %s",
                            this.getProvider().getSafeName(index.getAutoName("IX", tableName)),
                            this.getProvider().getSafeTableName(tableName)));
                }
                builder.append(lineSeparator);
                builder.append("(");
                builder.append(lineSeparator);
                builder.append("  ");
                for (int i = 0; i < index.getColumns().size(); i++) {
                    if (i > 0) {
                        builder.append(",");
                        builder.append(lineSeparator);
                        builder.append("  ");
                    }
                    builder.append(this.getProvider().getSafeName(index.getColumns().get(i).getColumnName()));
                }
                builder.append(lineSeparator);
                builder.append(")");
            }
        }
        return builder.toString();
    }


    @Override
    protected String createAnnotation(String[] texts) {
        if (texts.length == 1) {
            return "-- " + texts[0];
        }
        StringBuilder builder = new StringBuilder();
        builder.append("/*");
        String lineSeparator = EnvironmentConstants.LINE_SEPARATOR;
        builder.append(lineSeparator);
        for (int i = 0; i < texts.length; i++) {
            if (i > 0) {
                builder.append(lineSeparator);
            }
            builder.append("  " + texts[i]);
        }
        builder.append(lineSeparator);
        builder.append("*/");
        return builder.toString();
    }

}
