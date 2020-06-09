package com.autumn.mybatis.wrapper.commands;

import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;

import java.util.List;

/**
 * 条件段
 *
 * @author 老码农
 * <p>
 * 2017-10-26 19:18:27
 */
public interface CriteriaSection {

    /**
     * 条件集合
     */
    public static final String CONDITION_COLLECTION_CRITERIAS = "criterias";

    /**
     * 获取别名
     *
     * @return
     */
    String getAlias();

    /**
     * 获取条件
     *
     * @return
     */
    List<AbstractCriteriaClause> getCriterias();

    /**
     * 获取锁模式
     *
     * @return
     */
    LockModeEnum getLockMode();
}
