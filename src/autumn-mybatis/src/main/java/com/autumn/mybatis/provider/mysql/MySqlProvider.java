package com.autumn.mybatis.provider.mysql;

import com.autumn.mybatis.provider.AbstractProvider;
import com.autumn.mybatis.provider.CommandBuilder;
import com.autumn.mybatis.provider.DefinitionBuilder;
import com.autumn.mybatis.provider.DeleteBuilder;
import com.autumn.mybatis.provider.InsertBuilder;
import com.autumn.mybatis.provider.QueryBuilder;
import com.autumn.mybatis.provider.UpdateBuilder;
import com.autumn.mybatis.provider.XmlBuilder;
import com.autumn.mybatis.provider.ProviderDriveType;
import com.autumn.mybatis.provider.annotation.ProviderDrive;
import com.autumn.mybatis.provider.mysql.builder.MySqlCommandBuilder;
import com.autumn.mybatis.provider.mysql.builder.MySqlDefinitionBuilder;
import com.autumn.mybatis.provider.mysql.builder.MySqlDeleteBuilder;
import com.autumn.mybatis.provider.mysql.builder.MySqlInsertBuilder;
import com.autumn.mybatis.provider.mysql.builder.MySqlQueryBuilder;
import com.autumn.mybatis.provider.mysql.builder.MySqlUpdateBuilder;
import com.autumn.mybatis.provider.mysql.builder.MySqlXmlBuilder;

/**
 * MySql提供者
 *
 * @author 老码农
 * <p>
 * 2017-10-19 08:25:07
 */
@ProviderDrive(ProviderDriveType.MY_SQL)
public class MySqlProvider extends AbstractProvider {

    /**
     * 驱动名称
     */
    private static final String DRIVE_NAME = "MySql";

    private static final String SAFE_NAME_PREFIX = "`";
    private static final String SAFE_NAME_SUFFIX = "`";

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
    public MySqlProvider() {
        this.insertBuilder = new MySqlInsertBuilder(this);
        this.updateBuilder = new MySqlUpdateBuilder(this);
        this.deleteBuilder = new MySqlDeleteBuilder(this);
        this.queryBuilder = new MySqlQueryBuilder(this);
        this.xmlBuilder = new MySqlXmlBuilder(this);
        this.definitionBuilder = new MySqlDefinitionBuilder(this);
        this.commandBuilder = new MySqlCommandBuilder(this);
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
