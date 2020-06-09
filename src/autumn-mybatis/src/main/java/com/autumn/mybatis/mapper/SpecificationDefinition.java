package com.autumn.mybatis.mapper;

import com.autumn.mybatis.mapper.impl.FirstUpperCaseSpecificationDefinitionImpl;
import com.autumn.mybatis.mapper.impl.OriginalSpecificationDefinitionImpl;
import com.autumn.mybatis.mapper.impl.StandardSpecificationDefinitionImpl;

/**
 * 规范定义
 * 
 * @author 老码农
 *
 *         2017-12-04 11:21:51
 */
public interface SpecificationDefinition {

	/**
	 * 默认标准定义
	 */
	public final static SpecificationDefinition DEFAULT_DEFINITION = new StandardSpecificationDefinitionImpl();

	/**
	 * 原始
	 */
	public final static SpecificationDefinition ORIGINAL_DEFINITION = new OriginalSpecificationDefinitionImpl();

	/**
	 * 首字母大写
	 */
	public final static SpecificationDefinition FIRST_UPPERCASE_DEFINITION = new FirstUpperCaseSpecificationDefinitionImpl();

	/**
	 * 默认表名称
	 * 
	 * @param entityName
	 *            实体名称
	 * @return
	 */
	String defaultTableName(String entityName);

	/**
	 * 默认列名称
	 * 
	 * @param memberName
	 *            成员名称
	 * @return
	 */
	String defaultColumnName(String memberName);
}
