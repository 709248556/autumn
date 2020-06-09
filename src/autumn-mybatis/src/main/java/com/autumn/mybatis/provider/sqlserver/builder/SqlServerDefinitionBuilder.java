package com.autumn.mybatis.provider.sqlserver.builder;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityIndex;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.builder.AbstractDefinitionBuilder;
import com.autumn.util.EnvironmentConstants;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

/**
 * sql 定义生成器
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2018-01-20 11:58:23
 */
public class SqlServerDefinitionBuilder extends AbstractDefinitionBuilder {

    /**
     * @param dbProvider
     */
    public SqlServerDefinitionBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public String getColumnType(EntityColumn column) {
        switch (column.getJdbcType()) {
            case BIT:
            case BOOLEAN:
                return "[bit]";
            case TINYINT:
                return "[tinyint]";
            case SMALLINT:
                return "[smallint]";
            case INTEGER:
                return "[int]";
            case BIGINT:
                return "[bigint]";
            case FLOAT:
                return "[float]";
            case DOUBLE:
            case REAL:
                return "[real]";
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
                return String.format("[decimal](%s,%s)", precision, scale);
            case CHAR:
                if (column.getLength() > 0) {
                    return String.format("[char](%s)", column.getLength());
                }
                return "[char](50)";
            case NCHAR:
                if (column.getLength() > 0) {
                    return String.format("[nchar](%s)", column.getLength());
                }
                return "[nchar](50)";
            case VARCHAR:
                if (column.getLength() > 0) {
                    return String.format("[varchar](%s)", column.getLength());
                }
                return "[varchar](50)";
            case NVARCHAR:
                if (column.getLength() > 0) {
                    return String.format("[nvarchar](%s)", column.getLength());
                }
                return "[nvarchar](50)";
            case LONGVARCHAR:
                return "[text]";
            case ARRAY:
            case BINARY:
            case CLOB:
            case VARBINARY:
            case LONGVARBINARY:
                return "[image]";
            case DATE:
            case DATALINK:
                return "[date]";
            case TIMESTAMP:
                return "[datetime]";
            case TIME:
                return "[time]";
            case BLOB:
            case SQLXML:
                return "[varchar](max)";
            case NCLOB:
                return "[nvarchar](max)";
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
        if (column.isIdentityAssignKey()) {
            builder.append(" IDENTITY(1,1)");
        }
        if (column.isNullable()) {
            builder.append(" NULL");
        } else {
            builder.append(" NOT NULL");
        }
        return builder.toString();
    }

    @Override
    public String existTableSqlCommand(String database, EntityTable table) {
        return "SELECT COUNT(*) FROM [sysObjects] WHERE [Id]=OBJECT_ID('" + table.getName() + "') AND [type]='U'";
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
        if (table.isIdentityAssignKey()) {
            if (table.getKeyColumns().size() > 0) {
                builder.append(",");
            }
            builder.append(lineSeparator);
            builder.append(String.format("  CONSTRAINT [PK_%s_%s] PRIMARY KEY (", tableName,
                    table.getKeyColumns().get(0).getColumnName()));
            for (int i = 0; i < table.getKeyColumns().size(); i++) {
                EntityColumn column = table.getKeyColumns().get(i);
                if (i > 0) {
                    builder.append(",");
                }
                builder.append(this.getProvider().getSafeName(column.getColumnName()));
            }
            builder.append(")");
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
    protected String getScriptRowSeparator() {
        return "GO";
    }

    @Override
    protected String createAnnotation(String[] texts) {
        if (texts.length == 1) {
            return "-- " + texts[0];
        }
        StringBuilder builder = new StringBuilder();
        builder.append("/*");
        builder.append(EnvironmentConstants.LINE_SEPARATOR);
        for (int i = 0; i < texts.length; i++) {
            if (i > 0) {
                builder.append(EnvironmentConstants.LINE_SEPARATOR);
            }
            builder.append("  " + texts[i]);
        }
        builder.append(EnvironmentConstants.LINE_SEPARATOR);
        builder.append("*/");
        return builder.toString();
    }

}
