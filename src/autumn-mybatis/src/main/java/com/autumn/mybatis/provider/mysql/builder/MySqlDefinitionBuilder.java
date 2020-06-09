package com.autumn.mybatis.provider.mysql.builder;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityIndex;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.builder.AbstractDefinitionBuilder;
import com.autumn.mybatis.provider.mysql.MySqlProvider;
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
public class MySqlDefinitionBuilder extends AbstractDefinitionBuilder {

    /**
     * 默认存储引擎
     */
    public final static String DEFAULT_ENGINE = "InnoDB";

    /**
     * @param dbProvider
     */
    public MySqlDefinitionBuilder(MySqlProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public String getColumnType(EntityColumn column) {
        switch (column.getJdbcType()) {
            case BIT:
            case BOOLEAN:
                return "bit(1)";
            case TINYINT:
                return "tinyint(4)";
            case SMALLINT:
                return "smallint(6)";
            case INTEGER:
                return "int(11)";
            case BIGINT:
                return "bigint(20)";
            case FLOAT:
                return "float";
            case DOUBLE:
            case REAL:
                return "double";
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
                return String.format("decimal(%s,%s)", precision, scale);
            case CHAR:
            case NCHAR:
                if (column.getLength() > 0) {
                    return String.format("char(%s)", column.getLength());
                }
                return "char(255)";
            case VARCHAR:
            case NVARCHAR:
                if (column.getLength() > 0) {
                    return String.format("varchar(%s)", column.getLength());
                }
                return "varchar(255)";
            case LONGNVARCHAR:
            case LONGVARCHAR:
                return "longtext";
            case ARRAY:
            case BINARY:
            case CLOB:
                return "blob";
            case VARBINARY:
            case LONGVARBINARY:
                return "longblob";
            case DATE:
            case DATALINK:
                return "date";
            case TIMESTAMP:
                return "datetime";
            case TIME:
                return "time";
            case BLOB:
            case NCLOB:
            case SQLXML:
                return "text";
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
        if (column.isNullable()) {
            builder.append(" DEFAULT NULL");
        } else {
            builder.append(" NOT NULL");
        }
        if (column.isIdentityAssignKey()) {
            builder.append(" AUTO_INCREMENT");
        }
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
                "table_schema = '" + database.trim() + "'" +
                " AND table_name = '" + table.getName() + "'";
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
        if (table.getIndexs().size() > 0) {
            for (EntityIndex index : table.getIndexs()) {
                builder.append(",");
                builder.append(lineSeparator);
                if (index.isUnique()) {
                    builder.append(String.format("  UNIQUE KEY %s (",
                            this.getProvider().getSafeName(index.getAutoName("ix", tableName))));
                } else {
                    builder.append(String.format("  KEY %s (",
                            this.getProvider().getSafeName(index.getAutoName("ix", tableName))));
                }
                for (int i = 0; i < index.getColumns().size(); i++) {
                    if (i > 0) {
                        builder.append(",");
                    }
                    builder.append(this.getProvider().getSafeName(index.getColumns().get(i).getColumnName()));
                }
                builder.append(")");
            }
        }
        builder.append(lineSeparator);
        String engineName = table.getEngineName();
        if (StringUtils.isNullOrBlank(engineName)) {
            engineName = DEFAULT_ENGINE;
        }
        builder.append(String.format(") ENGINE=%s", engineName));
        if (table.isIdentityAssignKey()) {
            builder.append(" AUTO_INCREMENT=1");
        }
        builder.append(" DEFAULT CHARSET=utf8;");
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
