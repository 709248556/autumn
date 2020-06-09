package com.autumn.mybatis.provider.postgresql.builder;

import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.builder.AbstractXmlBuilder;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-05-06 17:35
 **/
public class PostgreSqlXmlBuilder extends AbstractXmlBuilder {

    /**
     * @param dbProvider
     */
    public PostgreSqlXmlBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    protected String createCriteriaWhenByLike(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'LIKE'\">", criteriaName));
        String content = "ILIKE CONCAT('%',#{" + criteriaName + ".value},'%')";
        sql.append(this.createCriteriaContentItem(criteriaName, content));
        sql.append("</when>");
        return sql.toString();
    }

    @Override
    protected String createCriteriaWhenByLeftLike(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'LEFT_LIKE'\">", criteriaName));
        String content = "ILIKE CONCAT(#{" + criteriaName + ".value},'%')";
        sql.append(this.createCriteriaContentItem(criteriaName, content));
        sql.append("</when>");
        return sql.toString();
    }

    @Override
    protected String createCriteriaWhenByRightLike(String criteriaName) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("<when test=\"%1$s.op == 'RIGHT_LIKE'\">", criteriaName));
        String content = "ILIKE CONCAT('%',#{" + criteriaName + ".value})";
        sql.append(this.createCriteriaContentItem(criteriaName, content));
        sql.append("</when>");
        return sql.toString();
    }
}
