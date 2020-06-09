package com.autumn.mybatis.wrapper.commands.impl;

import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;
import com.autumn.mybatis.wrapper.clauses.UpdateClause;
import com.autumn.mybatis.wrapper.commands.UpdateSection;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件更新段
 *
 * @author 老码农
 * <p>
 * 2017-10-30 18:37:50
 */
public class UpdateSectionImpl extends CriteriaSectionImpl implements UpdateSection {

    /**
     * 获取排序集合
     *
     * @return
     */
    private final List<UpdateClause> updateClauses;

    private final String tableName;

    /**
     * @param tableName
     */
    public UpdateSectionImpl(String tableName) {
        this(tableName, null);
    }

    /**
     * @param tableName
     * @param lockMode
     */
    public UpdateSectionImpl(String tableName, LockModeEnum lockMode) {
        super(lockMode);
        this.tableName = tableName;
        this.updateClauses = new ArrayList<>();
    }

    @Override
    public List<UpdateClause> getUpdateClauses() {
        return this.updateClauses;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE FROM ").append(this.tableName).append(" SET ");
        int index = 0;
        for (UpdateClause updateClause : this.getUpdateClauses()) {
            if (index > 0) {
                builder.append(",");
            }
            builder.append(updateClause.getColumnName()).append(" = ");
            builder.append(AbstractCriteriaClause.toSqlValue(updateClause.getValue()));
            index++;
        }
        if (this.getCriterias().size() > 0) {
            builder.append(" ").append(this.toWhereSqlCode());
        }
        return builder.toString();
    }
}
