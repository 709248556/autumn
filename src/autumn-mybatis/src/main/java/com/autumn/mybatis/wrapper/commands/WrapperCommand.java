package com.autumn.mybatis.wrapper.commands;

import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;
import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;
import com.autumn.mybatis.wrapper.expressions.AbstractCriteriaExpression;
import com.autumn.mybatis.wrapper.expressions.AbstractExpression;
import com.autumn.mybatis.wrapper.commands.impl.CriteriaSectionImpl;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 包装器命令
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-08 21:48
 */
public class WrapperCommand implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2925038001543793003L;

    /**
     * 创建包装器
     *
     * @return
     */
    public static WrapperCommand createWrapper() {
        return new WrapperCommand();
    }

    private AbstractCriteriaExpression expression;
    private LockModeEnum lockMode;
    private String alias;

    /**
     * 实例化
     */
    WrapperCommand() {
        this.expression = null;
        this.lockMode = LockModeEnum.NONE;
        this.alias = null;
    }

    /**
     * 获取完整表达式
     *
     * @return
     */
    public final AbstractCriteriaExpression getEexpression() {
        return this.expression;
    }

    /**
     * 添加表达式
     *
     * @param logicalOperater 逻辑表运算符
     * @param expression      表达式
     */
    public final void addEexpression(LogicalOperatorEnum logicalOperater, AbstractCriteriaExpression expression) {
        ExceptionUtils.checkNotNull(logicalOperater, "logicalOperater");
        ExceptionUtils.checkNotNull(expression, "expression");
        if (this.expression == null) {
            this.expression = expression;
        } else {
            this.expression = AbstractExpression.logical(logicalOperater, this.expression, expression);
        }
    }

    /**
     * 获取锁模式
     *
     * @return
     */
    public final LockModeEnum getLockMode() {
        return this.lockMode;
    }

    /**
     * 锁模式
     *
     * @param lockMode 锁模式
     */
    public final void lock(LockModeEnum lockMode) {
        if (lockMode != null) {
            this.lockMode = lockMode;
        } else {
            this.lockMode = LockModeEnum.NONE;
        }
    }

    /**
     * 获取表别名
     *
     * @return
     */
    public final String getAlias() {
        return this.alias;
    }

    /**
     * 表别名
     *
     * @param alias 别名
     */
    public final void tableAlias(String alias) {
        if (StringUtils.isNullOrBlank(alias)) {
            this.alias = null;
        } else {
            this.alias = alias.trim();
        }
    }

    /**
     * 重置
     */
    public void reset() {
        this.alias = null;
        this.lockMode = LockModeEnum.NONE;
        this.expression = null;
    }

    /**
     * 创建条件集合
     *
     * @return
     */
    protected final List<AbstractCriteriaClause> createCriteriaSections() {
        if (this.expression != null) {
            return expression.createSections();
        }
        return new ArrayList<>();
    }

    /**
     * 创建查询段
     *
     * @return 返回 CriteriaSection
     */
    public CriteriaSection createSection() {
        CriteriaSectionImpl qs = new CriteriaSectionImpl(this.getLockMode());
        qs.setAlias(this.getAlias());
        qs.getCriterias().addAll(this.createCriteriaSections());
        return qs;
    }

}
