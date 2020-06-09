package com.autumn.mybatis.mapper.impl;

import com.autumn.mybatis.mapper.SpecificationDefinition;

/**
 * 原始，不做任何变动
 * 
 * @author 老码农
 *
 *         2017-12-04 11:27:35
 */
public class OriginalSpecificationDefinitionImpl implements SpecificationDefinition {

	@Override
	public String defaultTableName(String entityName) {
		return entityName;
	}

	@Override
	public String defaultColumnName(String memberName) {
		return memberName;
	}

}
