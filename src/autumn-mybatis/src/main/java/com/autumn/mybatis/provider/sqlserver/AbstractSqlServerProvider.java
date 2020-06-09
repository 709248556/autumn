package com.autumn.mybatis.provider.sqlserver;

import com.autumn.mybatis.provider.*;
import com.autumn.mybatis.provider.sqlserver.builder.*;

/**
 * Sql抽象驱动
 * 
 * @author 老码农
 *
 *         2017-12-06 13:24:36
 */
public abstract class AbstractSqlServerProvider extends AbstractProvider {

	/**
	 * 驱动名称
	 */
	private static final String DRIVE_NAME = "SqlServer";

	private static final String SAFE_NAME_PREFIX = "[";
	private static final String SAFE_NAME_SUFFIX = "]";

	protected InsertBuilder insertBuilder;
	protected UpdateBuilder updateBuilder;
	protected DeleteBuilder deleteBuilder;
	protected XmlBuilder xmlBuilder;
	protected DefinitionBuilder definitionBuilder;
	protected CommandBuilder commandBuilder;

	public AbstractSqlServerProvider() {
		this.insertBuilder = new SqlServerInsertBuilder(this);
		this.updateBuilder = new SqlServerUpdateBuilder(this);
		this.deleteBuilder = new SqlServerDeleteBuilder(this);
		this.xmlBuilder = new SqlServerXmlBuilder(this);
		this.definitionBuilder = new SqlServerDefinitionBuilder(this);
		this.commandBuilder = new SqlServerCommandBuilder(this);
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
	public final String getSafeNamePrefix() {
		return SAFE_NAME_PREFIX;
	}

	@Override
	public final String getSafeNameSuffix() {
		return SAFE_NAME_SUFFIX;
	}

}
