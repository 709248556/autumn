package com.autumn.mybatis.annotation;

import com.autumn.mybatis.provider.DbProvider;
import com.autumn.util.tuple.TupleTwo;

/**
 * 
 * @author 老码农
 *
 * 2018-01-05 16:54:04
 */
class MultipleDefinitionRegistrarInfo {

	private final DefinitionRegistrarInfo info;
	private final TupleTwo<String, Class<? extends DbProvider>> providerInfo;

	public MultipleDefinitionRegistrarInfo(DefinitionRegistrarInfo info,
			TupleTwo<String, Class<? extends DbProvider>> providerInfo) {
		super();
		this.info = info;
		this.providerInfo = providerInfo;
	}

	public DefinitionRegistrarInfo getInfo() {
		return info;
	}

	public TupleTwo<String, Class<? extends DbProvider>> getProviderInfo() {
		return providerInfo;
	}
}
