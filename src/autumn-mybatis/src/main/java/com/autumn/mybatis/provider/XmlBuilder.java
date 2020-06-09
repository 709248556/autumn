package com.autumn.mybatis.provider;

import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;

/**
 * xml 生成器
 *
 * @author 老码农 2019-06-09 21:55:00
 */
public interface XmlBuilder extends Builder {

    /**
     * 获取条件的命令
     *
     * @param parmaName      参数名称
     * @return
     */
    String getWhereCommand(String parmaName);

    /**
     * 获取分组条件的命令
     *
     * @param parmaName      参数名称
     * @return
     */
    String getHavingCommand(String parmaName);

    /**
     * 获取条件运算符 Xml 内容
     *
     * @param operator     运算符 {@link com.autumn.mybatis.wrapper.CriteriaOperatorEnum}
     * @param criteriaName 条件名称
     * @return
     */
    String getCriteriaOperatorXmlContent(CriteriaOperatorEnum operator, String criteriaName);
}
