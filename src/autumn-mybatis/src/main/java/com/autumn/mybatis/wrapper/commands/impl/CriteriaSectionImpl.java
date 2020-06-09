package com.autumn.mybatis.wrapper.commands.impl;

import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;
import com.autumn.mybatis.wrapper.commands.CriteriaSection;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件段
 *
 * @author 老码农
 * <p>
 * 2017-10-26 19:20:12
 */
public class CriteriaSectionImpl implements CriteriaSection {
    private final List<AbstractCriteriaClause> criterias;

    private LockModeEnum lockMode;

    private String alias;

    /**
     *
     */
    public CriteriaSectionImpl() {
        this(null);
    }

    /**
     * 实例化
     *
     * @param lockMode
     */
    public CriteriaSectionImpl(LockModeEnum lockMode) {
        this.criterias = new ArrayList<>();
        this.setLockMode(lockMode);
    }

    /**
     * 获取锁模式
     *
     * @return
     */
    @Override
    public LockModeEnum getLockMode() {
        return this.lockMode;
    }

    /**
     * 设置锁
     *
     * @param lockMode
     */
    public void setLockMode(LockModeEnum lockMode) {
        if (lockMode != null) {
            this.lockMode = lockMode;
        } else {
            this.lockMode = LockModeEnum.NONE;
        }
    }

    @Override
    public final List<AbstractCriteriaClause> getCriterias() {
        return this.criterias;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    /**
     * 设置别名
     *
     * @param alias 别名
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        if (this.getCriterias().size() > 0) {
            return this.toWhereSqlCode();
        }
        return "WHERE";
    }

    protected String toWhereSqlCode() {
        return this.toCriteriasSqlCode(this.getCriterias(), "WHERE");
    }

    /**
     * @param criterias
     * @param criteriaKey
     * @return
     */
    protected String toCriteriasSqlCode(List<AbstractCriteriaClause> criterias, String criteriaKey) {
        StringBuilder builder = new StringBuilder();
        if (criterias != null && criterias.size() > 0) {
            builder.append(criteriaKey).append(" ");
            for (int i = 0; i < this.getCriterias().size(); i++) {
                AbstractCriteriaClause item = this.getCriterias().get(i);
                builder.append(item.toString(i > 0));
            }
        }
        return builder.toString();
    }


}
