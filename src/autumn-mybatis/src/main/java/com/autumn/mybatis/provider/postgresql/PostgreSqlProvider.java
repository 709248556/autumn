package com.autumn.mybatis.provider.postgresql;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.*;
import com.autumn.mybatis.provider.annotation.ProviderDrive;
import com.autumn.mybatis.provider.postgresql.builder.*;
import com.autumn.util.StringUtils;

/**
 * PostgreSql 提供者
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-05-06 16:16
 **/
@ProviderDrive(ProviderDriveType.POSTGRE_SQL)
public class PostgreSqlProvider extends AbstractProvider {

    /**
     * 驱动名称
     */
    private static final String DRIVE_NAME = "PostgreSql";

    private static final String SAFE_NAME_PREFIX = "\"";
    private static final String SAFE_NAME_SUFFIX = "\"";

    private InsertBuilder insertBuilder;
    private UpdateBuilder updateBuilder;
    private DeleteBuilder deleteBuilder;
    private QueryBuilder queryBuilder;
    private XmlBuilder xmlBuilder;
    private DefinitionBuilder definitionBuilder;
    private CommandBuilder commandBuilder;

    /**
     *
     */
    public PostgreSqlProvider() {
        this.insertBuilder = new PostgreSqlInsertBuilder(this);
        this.updateBuilder = new PostgreSqlUpdateBuilder(this);
        this.deleteBuilder = new PostgreSqlDeleteBuilder(this);
        this.queryBuilder = new PostgreSqlQueryBuilder(this);
        this.xmlBuilder = new PostgreSqlXmlBuilder(this);
        this.definitionBuilder = new PostgreSqlDefinitionBuilder(this);
        this.commandBuilder = new PostgreSqlCommandBuilder(this);
    }

    @Override
    public String getDriveName() {
        return DRIVE_NAME;
    }

    @Override
    public InsertBuilder getInsertBuilder() {
        return this.insertBuilder;
    }

    @Override
    public UpdateBuilder getUpdateBuilder() {
        return this.updateBuilder;
    }

    @Override
    public DeleteBuilder getDeleteBuilder() {
        return this.deleteBuilder;
    }

    @Override
    public QueryBuilder getQueryBuilder() {
        return this.queryBuilder;
    }

    @Override
    public XmlBuilder getXmlBuilder() {
        return this.xmlBuilder;
    }

    @Override
    public DefinitionBuilder getDefinitionBuilder() {
        return this.definitionBuilder;
    }

    @Override
    public CommandBuilder getCommandBuilder() {
        return this.commandBuilder;
    }

    @Override
    public String getSafeTableName(EntityTable table) {
        return this.getSafeTableName(this.getFullTableName(table));
    }

    @Override
    public String getSafeNamePrefix() {
        return SAFE_NAME_PREFIX;
    }

    @Override
    public String getSafeNameSuffix() {
        return SAFE_NAME_SUFFIX;
    }

    /**
     * 获取完整表名
     *
     * @param table
     * @return
     */
    public static String getFullTableName(EntityTable table) {
        return getTableSchema(table) + "." + table.getName();
    }

    /**
     * 默认表架构
     */
    private static final String DEFAULT_TABLE_SCHEMA = "public";

    /**
     * 获取表架构名称
     *
     * @param table
     * @return
     */
    public static String getTableSchema(EntityTable table) {
        if (StringUtils.isNotNullOrBlank(table.getSchema())) {
            return table.getSchema();
        }
        return DEFAULT_TABLE_SCHEMA;
    }
}
