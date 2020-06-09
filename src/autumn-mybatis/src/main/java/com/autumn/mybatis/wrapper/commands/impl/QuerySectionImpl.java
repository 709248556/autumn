package com.autumn.mybatis.wrapper.commands.impl;

import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.mybatis.wrapper.clauses.*;
import com.autumn.mybatis.wrapper.commands.QuerySection;
import com.autumn.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件
 *
 * @author 老码农
 * <p>
 * 2017-10-26 12:56:00
 */
public class QuerySectionImpl extends CriteriaSectionImpl implements QuerySection {

    /**
     * 获取排序集合
     *
     * @return
     */
    private final List<OrderClause> orderClauses;

    private final List<SelectColumnClause> selectColumns;

    private final List<ColumnClause> groups;

    private final List<AbstractCriteriaClause> havingCriterias;

    private final String tableName;

    /**
     * 分页
     */
    private final PageClause pageClause;

    /**
     * @param tableName
     */
    public QuerySectionImpl(String tableName) {
        this(tableName, null);
    }

    /**
     * @param tableName
     * @param lockMode
     */
    public QuerySectionImpl(String tableName, LockModeEnum lockMode) {
        super(lockMode);
        this.tableName = tableName;
        this.pageClause = new PageClause();
        this.orderClauses = new ArrayList<>();
        this.selectColumns = new ArrayList<>();
        this.havingCriterias = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    @Override
    public PageClause getPageClause() {
        return this.pageClause;
    }

    @Override
    public List<OrderClause> getOrderClauses() {
        return this.orderClauses;
    }

    @Override
    public List<AbstractCriteriaClause> getHavingCriterias() {
        return this.havingCriterias;
    }

    @Override
    public List<SelectColumnClause> getSelectColumns() {
        return this.selectColumns;
    }

    @Override
    public List<ColumnClause> getGroups() {
        return this.groups;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        int index = 0;
        for (SelectColumnClause col : this.getSelectColumns()) {
            if (index > 0) {
                builder.append(",");
            }
            if (StringUtils.isNullOrBlank(col.getFunName())) {
                builder.append(col.getColumnName());
            } else {
                if (col.getFunName().equalsIgnoreCase("count")) {
                    builder.append("COUNT(*)");
                } else {
                    builder.append(col.getFunName()).append("(").append(col.getColumnName()).append(")");
                }
            }
            if (!StringUtils.isNullOrBlank(col.getAlias())) {
                builder.append(" AS ").append(col.getAlias());
            }
            index++;
        }
        builder.append(" FROM ").append(tableName);
        if (!StringUtils.isNullOrBlank(this.getAlias())) {
            builder.append(" AS ").append(this.getAlias());
        }
        if (this.getCriterias().size() > 0) {
            builder.append(" ").append(this.toWhereSqlCode());
        }
        if (this.getGroups().size() > 0) {
            builder.append(" GROUP BY ");
            index = 0;
            for (ColumnClause group : this.getGroups()) {
                if (index > 0) {
                    builder.append(",");
                }
                builder.append(group.getColumnName());
                index++;
            }
        }
        if (this.getHavingCriterias().size() > 0) {
            builder.append(" ").append(this.toCriteriasSqlCode(this.getHavingCriterias(), "HAVING"));
        }
        if (this.getOrderClauses().size() > 0) {
            builder.append(" ORDER BY ");
            index = 0;
            for (OrderClause orderClause : this.getOrderClauses()) {
                if (index > 0) {
                    builder.append(",");
                }
                if (StringUtils.isNullOrBlank(orderClause.getFunName())) {
                    builder.append(orderClause.getColumnName());
                } else {
                    if (orderClause.getFunName().equalsIgnoreCase("count")) {
                        builder.append("COUNT(*)");
                    } else {
                        builder.append(orderClause.getFunName()).append("(").append(orderClause.getColumnName()).append(")");
                    }
                }
                builder.append(" ").append(orderClause.getDirection());
                index++;
            }
        }
        return builder.toString();
    }
}
