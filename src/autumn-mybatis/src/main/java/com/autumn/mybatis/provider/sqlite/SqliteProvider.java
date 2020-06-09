package com.autumn.mybatis.provider.sqlite;

import com.autumn.mybatis.provider.*;
import com.autumn.mybatis.provider.annotation.ProviderDrive;
import com.autumn.mybatis.provider.sqlite.builder.*;

/**
 * Sqlite 提供程序
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-22 19:31
 */
@ProviderDrive(ProviderDriveType.SQLITE)
public class SqliteProvider extends AbstractProvider {

    /**
     * 驱动名称
     */
    private static final String DRIVE_NAME = "Sqlite";

    private static final String SAFE_NAME_PREFIX = "[";
    private static final String SAFE_NAME_SUFFIX = "]";

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
    public SqliteProvider() {
        this.insertBuilder = new SqliteInsertBuilder(this);
        this.updateBuilder = new SqliteUpdateBuilder(this);
        this.deleteBuilder = new SqliteDeleteBuilder(this);
        this.queryBuilder = new SqliteQueryBuilder(this);
        this.xmlBuilder = new SqliteXmlBuilder(this);
        this.definitionBuilder = new SqliteDefinitionBuilder(this);
        this.commandBuilder = new SqliteCommandBuilder(this);
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
    public String getSafeNamePrefix() {
        return SAFE_NAME_PREFIX;
    }

    @Override
    public String getSafeNameSuffix() {
        return SAFE_NAME_SUFFIX;
    }
}
