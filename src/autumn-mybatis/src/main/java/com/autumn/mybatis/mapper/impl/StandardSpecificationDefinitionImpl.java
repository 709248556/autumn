package com.autumn.mybatis.mapper.impl;

import com.autumn.mybatis.mapper.SpecificationDefinition;
import com.autumn.util.StringUtils;

/**
 * 标准的定义规范
 * 
 * @author 老码农
 *
 *         2017-12-04 11:24:24
 */
public class StandardSpecificationDefinitionImpl implements SpecificationDefinition {

	@Override
	public String defaultTableName(String entityName) {
		return StringUtils.dbStandardCapitalize(entityName);
	}

	@Override
	public String defaultColumnName(String memberName) {
		return StringUtils.dbStandardCapitalize(memberName);
	}

}
