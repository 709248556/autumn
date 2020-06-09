package com.autumn.mybatis.provider.postgresql.builder;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityIndex;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.builder.AbstractDefinitionBuilder;
import com.autumn.mybatis.provider.postgresql.PostgreSqlProvider;
import com.autumn.util.EnvironmentConstants;
import com.autumn.util.StringUtils;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-05-06 17:34
 **/
public class PostgreSqlDefinitionBuilder extends AbstractDefinitionBuilder {

    /**
     * @param dbProvider
     */
    public PostgreSqlDefinitionBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public String getColumnType(EntityColumn column) {
        switch (column.getJdbcType()) {
            case BIT:
            case BOOLEAN:
                return "boolean";
            case SMALLINT:
            case TINYINT:
                return "smallint";
            case INTEGER:
                return "integer";
            case BIGINT:
                return "bigint";
            case FLOAT:
                return "real";
            case DOUBLE:
            case REAL:
                return "double precision";
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
                return String.format("numeric(%s,%s)", precision, scale);
            case CHAR:
            case NCHAR:
                if (column.getLength() > 0) {
                    return String.format("character(%s)", column.getLength());
                }
                return "character(255)";
            case VARCHAR:
            case NVARCHAR:
                if (column.getLength() > 0) {
                    return String.format("character varying(%s)", column.getLength());
                }
                return "character varying(255)";
            case ARRAY:
            case BINARY:
            case CLOB:
            case VARBINARY:
            case LONGVARBINARY:
                return "bytea";
            case DATE:
            case DATALINK:
                return "date";
            case TIME:
                return "time";
            case TIMESTAMP:
                return "timestamp without time zone";
            case LONGNVARCHAR:
            case LONGVARCHAR:
            case BLOB:
            case NCLOB:
            case SQLXML:
                return "text";
            default:
                throw ExceptionUtils.throwNotSupportException("不支持 " + column.getJdbcType() + " 的数据库映射。");
        }
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

    @Override
    public String existTableSqlCommand(String database, EntityTable table) {
        if (StringUtils.isNullOrBlank(database)) {
            ExceptionUtils.throwConfigureException("数据库名[database]为空，" +
                    "无法判断当前数据库是否存在表[" + table.getName() + "]," +
                    "驱动[" + this.getProvider().getDriveName() + "]。");
        }
        return "SELECT COUNT(*) FROM information_schema.tables WHERE " +
                "table_catalog = '" + database.trim() + "'" +
                " AND table_schema = '" + PostgreSqlProvider.getTableSchema(table) + "'" +
                " AND table_name = '" + table.getName() + "'";
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
        builder.append(" ");
        if (column.isIdentityAssignKey()) {
            builder.append("bigserial NOT NULL");
        } else {
            builder.append(this.getColumnType(column));
            if (column.isNullable()) {
                builder.append(" DEFAULT NULL");
            } else {
                builder.append(" NOT NULL");
            }
        }
        return builder.toString();
    }

    @Override
    public String createTableScript(EntityTable table, String tableName) {
        if (table.isView()) {
            ExceptionUtils.throwNotSupportException("对象 " + table.getEntityClass().getName() + " 为视图对象，不支持生成表脚本。");
        }
        if (StringUtils.isNullOrBlank(tableName)) {
            tableName = table.getName();
        }
        String lineSeparator = EnvironmentConstants.LINE_SEPARATOR;
        StringBuilder builder = new StringBuilder();
        String fullName = PostgreSqlProvider.getTableSchema(table) + "." + tableName;
        builder.append(String.format("CREATE TABLE %s (", this.getProvider().getSafeTableName(fullName)));
        for (int i = 0; i < table.getColumns().size(); i++) {
            EntityColumn column = table.getColumns().get(i);
            if (i > 0) {
                builder.append(",");
            }
            builder.append(lineSeparator);
            builder.append("  ");
            builder.append(this.createColumnScript(column));
        }
        if (table.getKeyColumns().size() > 0) {
            builder.append(",");
            builder.append(lineSeparator);
            builder.append("  PRIMARY KEY (");
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
        builder.append(");");
        if (table.getIndexs().size() > 0) {
            for (EntityIndex index : table.getIndexs()) {
                builder.append(lineSeparator);
                if (index.isUnique()) {
                    builder.append(String.format("CREATE UNIQUE INDEX %s ON %s",
                            this.getProvider().getSafeName(index.getAutoName("IX", tableName)),
                            this.getProvider().getSafeTableName(fullName)));
                } else {
                    builder.append(String.format("CREATE INDEX %s ON %s",
                            this.getProvider().getSafeName(index.getAutoName("IX", tableName)),
                            this.getProvider().getSafeTableName(fullName)));
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
                builder.append(");");
            }
        }
        return builder.toString();
    }
}
