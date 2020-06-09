package com.autumn.mybatis.wrapper.commands;

import com.autumn.mybatis.wrapper.clauses.*;

import java.util.List;

/**
 * 查询条件段
 *
 * @author 老码农
 * <p>
 * 2017-10-26 12:56:32
 */
public interface QuerySection extends CriteriaSection {

    /**
     * HAVING 条件集合
     */
    public static final String CONDITION_COLLECTION_HAVING_CRITERIAS = "havingCriterias";

    /**
     * 获取分页
     *
     * @return
     */
    PageClause getPageClause();

    /**
     * 获取排序集合
     *
     * @return
     */
    List<OrderClause> getOrderClauses();

    /**
     * 获取分组条件
     *
     * @return
     */
    List<AbstractCriteriaClause> getHavingCriterias();

    /**
     * 获取查询列集合
     *
     * @return
     */
    List<SelectColumnClause> getSelectColumns();

    /**
     * 分组
     *
     * @return
     */
    List<ColumnClause> getGroups();
}
