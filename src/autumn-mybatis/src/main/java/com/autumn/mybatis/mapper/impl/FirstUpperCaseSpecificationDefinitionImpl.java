package com.autumn.mybatis.mapper.impl;

import com.autumn.mybatis.mapper.SpecificationDefinition;
import com.autumn.util.StringUtils;

/**
 * 帕斯卡命名
 *
 * @author 老码农
 * <p>
 * 2017-12-04 11:31:45
 */
public class FirstUpperCaseSpecificationDefinitionImpl implements SpecificationDefinition {

    @Override
    public String defaultTableName(String entityName) {
        return StringUtils.upperCaseCapitalize(entityName);
    }

    @Override
    public String defaultColumnName(String memberName) {
        return StringUtils.upperCaseCapitalize(memberName);
    }

}
