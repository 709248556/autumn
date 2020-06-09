package com.autumn.mybatis.provider.builder;

import com.autumn.mybatis.provider.CommandBuilder;
import com.autumn.mybatis.provider.DbProvider;

/**
 * 
 * 命令生成器抽象
 * @author 老码农 2018-04-01 00:30:34
 */
public abstract class AbstractCommandBuilder extends AbstractBuilder implements CommandBuilder {

	/**
	 * 
	 * @param dbProvider
	 */
	public AbstractCommandBuilder(DbProvider dbProvider) {
		super(dbProvider);
	}

	@Override
	public String getExecuteCommand(String commandParmaName) {
		return String.format("${%s}", commandParmaName);
	}

}
