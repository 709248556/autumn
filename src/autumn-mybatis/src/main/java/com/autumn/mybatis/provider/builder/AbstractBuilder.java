package com.autumn.mybatis.provider.builder;

import com.autumn.mybatis.provider.Builder;
import com.autumn.mybatis.provider.DbProvider;

/**
 * 生成器抽象
 * 
 * @author 老码农
 *
 *         2017-10-19 08:28:41
 */
public abstract class AbstractBuilder implements Builder {

	private final DbProvider dbProvider;

	public AbstractBuilder(DbProvider dbProvider) {
		this.dbProvider = dbProvider;
	}

	@Override
	public final DbProvider getProvider() {
		return this.dbProvider;
	}	
}
