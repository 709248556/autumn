package com.autumn.mybatis.provider.builder;

import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.XmlBuilder;
import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.commands.CriteriaSection;
import com.autumn.mybatis.wrapper.commands.QuerySection;
import com.autumn.util.StringUtils;

/**
 * Xml生成器抽象
 *
 * @author 老码农 2019-06-09 22:02:38
 */
public abstract class AbstractXmlBuilder extends AbstractBuilder implements XmlBuilder {

    /**
     * 条件关键字 WHERE
     */
    public static final String CONDITION_KEYWORD_WHERE = "WHERE";

    /**
     * 条件关键字 HAVING
     */
    public static final String CONDITION_KEYWORD_HAVING = "HAVING";

    /**
     * @param dbProvider
     */
    public AbstractXmlBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    /**
     * 获取条件的命令
     *
     * @param parmaName 参数名称
     * @return
     */
    @Override
    public String getWhereCommand(String parmaName) {
        return this.getConditionCommand(CONDITION_KEYWORD_WHERE, parmaName, CriteriaSection.CONDITION_COLLECTION_CRITERIAS);
    }

    /**
     * 获取分组条件的命令
     *
     * @param parmaName 参数名称
     * @return
     */
    @Override
    public String getHavingCommand(String parmaName) {
        return this.getConditionCommand(CONDITION_KEYWORD_HAVING, parmaName, QuerySection.CONDITION_COLLECTION_HAVING_CRITERIAS);
    }

    private String getConditionCommand(String keyword, String parmaName, String collectionName) {
        String itemName = "item";
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<if test=\"%1$s.%2$s.size > 0\">", parmaName, collectionName));
        sql.append(String.format("<trim prefix=\"%s\" prefixOverrides=\"AND|OR|NOT\">", keyword));
        sql.append("<foreach collection=\"").append(parmaName).append(".").append(collectionName).append("\" item=\"").append(itemName).append("\" >");
        sql.append("<choose>");
        for (CriteriaOperatorEnum operator : CriteriaOperatorEnum.values()) {
            String operatorXmlContent = this.getCriteriaOperatorXmlContent(operator, itemName);
            if (!StringUtils.isNullOrBlank(operatorXmlContent)) {
                sql.append(operatorXmlContent);
            }
        }
        sql.append(this.createCriteriaOtherWise(itemName));
        sql.append("</choose>");
        sql.append("</foreach>");
        sql.append("</trim>");
        sql.append("</if>");
        return sql.toString();
    }

    @Override
    public String getCriteriaOperatorXmlContent(CriteriaOperatorEnum operator, String criteriaName) {
        if (operator.equals(CriteriaOperatorEnum.LIKE)) {
            return this.createCriteriaWhenByLike(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.LEFT_LIKE)) {
            return this.createCriteriaWhenByLeftLike(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.RIGHT_LIKE)) {
            return this.createCriteriaWhenByRightLike(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.IS_NULL)) {
            return this.createCriteriaWhenByIsNull(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.IS_NOT_NULL)) {
            return this.createCriteriaWhenByIsNotNull(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.IN)) {
            return this.createCriteriaWhenByIn(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.NOT_IN)) {
            return this.createCriteriaWhenByNotIn(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.IN_SQL)) {
            return this.createCriteriaWhenByInSql(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.NOT_IN_SQL)) {
            return this.createCriteriaWhenByNotInSql(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.EXISTS)) {
            return this.createCriteriaWhenByExists(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.NOT_EXISTS)) {
            return this.createCriteriaWhenByNotExists(criteriaName);
        } else if (operator.equals(CriteriaOperatorEnum.BETWEEN)) {
            return this.createCriteriaWhenByBetween(criteriaName);
        }
        return null;
    }

    /**
     * 创建条件 When Is Null
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaWhenByIsNull(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'IS_NULL'\">", criteriaName));
        sql.append(createCriteriaContentItem(criteriaName, "IS NULL"));
        sql.append("</when>");
        return sql.toString();
    }

    /**
     * 创建条件 When Is Not Null
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaWhenByIsNotNull(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'IS_NOT_NULL'\">", criteriaName));
        sql.append(createCriteriaContentItem(criteriaName, "IS NOT NULL"));
        sql.append("</when>");
        return sql.toString();
    }

    /**
     * 创建条件 When in
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaWhenByIn(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'IN'\">", criteriaName));
        String content = "IN <foreach item=\"p\" collection=\"" + criteriaName
                + ".values\" open=\"(\" separator=\",\" close=\")\">#{p}</foreach>";
        sql.append(createCriteriaContentItem(criteriaName, content));
        sql.append("</when>");
        return sql.toString();
    }

    /**
     * 创建条件 When not In
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaWhenByNotIn(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'NOT_IN'\">", criteriaName));
        String content = "NOT IN <foreach item=\"p\" collection=\"" + criteriaName
                + ".values\" open=\"(\" separator=\",\" close=\")\">#{p}</foreach>";
        sql.append(createCriteriaContentItem(criteriaName, content));
        sql.append("</when>");
        return sql.toString();
    }

    /**
     * 创建条件 When In Sql
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaWhenByInSql(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'IN_SQL'\">", criteriaName));
        sql.append(createCriteriaContentItem(criteriaName, "IN (${" + criteriaName + ".value})"));
        sql.append("</when>");
        return sql.toString();
    }

    /**
     * 创建条件 When Not In Sql
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaWhenByNotInSql(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'NOT_IN_SQL'\">", criteriaName));
        sql.append(createCriteriaContentItem(criteriaName, "NOT IN (${" + criteriaName + ".value})"));
        sql.append("</when>");
        return sql.toString();
    }

    /**
     * 创建条件 When Exists
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaWhenByExists(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'EXISTS'\">", criteriaName));
        sql.append(createCriteriaContentExpression(criteriaName, "EXISTS"));
        sql.append("</when>");
        return sql.toString();
    }

    /**
     * 创建条件 When Not Exists
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaWhenByNotExists(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'NOT_EXISTS'\">", criteriaName));
        sql.append(createCriteriaContentExpression(criteriaName, "NOT EXISTS"));
        sql.append("</when>");
        return sql.toString();
    }

    /**
     * 创建条件 When Between
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaWhenByBetween(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'BETWEEN'\">", criteriaName));
        String content = "BETWEEN #{" + criteriaName + ".value} AND #{" + criteriaName + ".secondValue}";
        sql.append(createCriteriaContentItem(criteriaName, content));
        sql.append("</when>");
        return sql.toString();
    }

    /**
     * 创建其他条件 OtherWise
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected String createCriteriaOtherWise(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<otherwise>");
        String content = "${" + criteriaName + ".op} #{" + criteriaName + ".value}";
        sql.append(createCriteriaContentItem(criteriaName, content));
        sql.append("</otherwise>");
        return sql.toString();
    }

    /**
     * 创建条件 When Like
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected abstract String createCriteriaWhenByLike(String criteriaName);

    /**
     * 创建条件 When Left Like
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected abstract String createCriteriaWhenByLeftLike(String criteriaName);

    /**
     * 创建条件 When Right Like
     *
     * @param criteriaName 条件名称
     * @return
     */
    protected abstract String createCriteriaWhenByRightLike(String criteriaName);

    /**
     * 创建条件内容表达式
     *
     * @param criteriaName  条件名称
     * @param prefixKeyword 关键字
     * @return
     */
    protected String createCriteriaContentExpression(String criteriaName, String prefixKeyword) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format(" ${%s.logic} ${%s.leftBrackets}", criteriaName, criteriaName));
        sql.append(String.format("%s(${%s.expression})", prefixKeyword, criteriaName));
        sql.append(String.format("${%s.rigthBrackets}", criteriaName));
        return sql.toString();
    }

    /**
     * 创建条件内容项目
     *
     * @param criteriaName 条件名称
     * @param content      关键字
     * @return
     */
    protected String createCriteriaContentItem(String criteriaName, String content) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format(" ${%s.logic} ${%s.leftBrackets}", criteriaName, criteriaName));
        sql.append("<choose>");
        sql.append(String.format("<when test=\"%1$s.funName == null\">", criteriaName));
        sql.append(String.format("%s${%s.expression}%s %s", this.getProvider().getSafeNamePrefix(), criteriaName,
                this.getProvider().getSafeNameSuffix(), content));
        sql.append("</when>");
        sql.append(String.format("<when test=\"%1$s.funName == 'COUNT'\">", criteriaName));
        sql.append(String.format("${%s.funName}(*) %s", criteriaName, content));
        sql.append("</when>");
        sql.append("<otherwise>");
        sql.append(String.format("${%s.funName}(%s${%s.expression}%s) %s", criteriaName, this.getProvider().getSafeNamePrefix(), criteriaName,
                this.getProvider().getSafeNameSuffix(), content));
        sql.append("</otherwise>");
        sql.append("</choose>");
        sql.append(String.format("${%s.rigthBrackets}", criteriaName));
        return sql.toString();
    }

}
