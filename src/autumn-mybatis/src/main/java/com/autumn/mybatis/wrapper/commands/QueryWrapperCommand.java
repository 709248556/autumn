package com.autumn.mybatis.wrapper.commands;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityColumnOrder;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;
import com.autumn.mybatis.wrapper.OrderDirectionEnum;
import com.autumn.mybatis.wrapper.PolymerizationEnum;
import com.autumn.mybatis.wrapper.clauses.*;
import com.autumn.mybatis.wrapper.commands.impl.QuerySectionImpl;
import com.autumn.mybatis.wrapper.expressions.AbstractCriteriaExpression;
import com.autumn.mybatis.wrapper.expressions.AbstractExpression;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 查询包装器命令
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-08 22:07
 */
public class QueryWrapperCommand extends WrapperCommand {

    /**
     *
     */
    private static final long serialVersionUID = -457170954183536304L;

    /**
     * 默认别名
     */
    public static final String DEFAULT_ALIAS = "m";

    /**
     * 创建包查询装器
     *
     * @param table
     * @return
     */
    public static QueryWrapperCommand createQueryWrapper(EntityTable table) {
        return new QueryWrapperCommand(table);
    }

    private PageClause pageClause = null;
    private List<OrderClause> orderClauses = new ArrayList<>();
    private List<SelectColumnClause> selectColumns = new ArrayList<>();
    private List<ColumnClause> groups = new ArrayList<>();
    private AbstractCriteriaExpression havingExpression = null;
    private final EntityTable table;

    /**
     * @param table
     */
    QueryWrapperCommand(EntityTable table) {
        this.table = table;
    }

    /**
     * 获取分页
     *
     * @return
     */
    public final PageClause getPageClause() {
        return this.pageClause;
    }

    /**
     * 添加 Having 表达式
     *
     * @param logicalOperater 逻辑表运算符
     * @param expression      表达式
     */
    public final void addHavingEexpression(LogicalOperatorEnum logicalOperater, AbstractCriteriaExpression expression) {
        ExceptionUtils.checkNotNull(logicalOperater, "logicalOperater");
        ExceptionUtils.checkNotNull(expression, "expression");
        if (this.havingExpression == null) {
            this.havingExpression = expression;
        } else {
            this.havingExpression = AbstractExpression.logical(logicalOperater, this.havingExpression, expression);
        }
    }

    /**
     * 创建分组条件集合
     *
     * @return
     */
    protected final List<AbstractCriteriaClause> createHavingCriteriaSections() {
        if (this.havingExpression != null) {
            return havingExpression.createSections();
        }
        return new ArrayList<>();
    }

    /**
     *
     */
    private void initPageClause() {
        if (this.pageClause == null) {
            this.pageClause = new PageClause();
        }
    }


    /**
     * 跳过记录数(相当于调用 limit)
     *
     * @param count 数量
     * @return
     */
    public void skip(int count) {
        this.initPageClause();
        this.pageClause.setOffset(count);
    }

    /**
     * 从开头返回记录数 (相当于调用 limit)
     *
     * @param count 数量
     * @return
     */
    public void take(int count) {
        this.initPageClause();
        this.pageClause.setLimit(count);
    }

    /**
     * 分页
     *
     * @param currentPage 当前页
     * @param pageSize    页大小
     * @return
     */
    public void page(int currentPage, int pageSize) {
        if (currentPage < 1) {
            ExceptionUtils.throwArgumentOverflowException("currentPage", "currentPage 不能小于1。");
        }
        if (pageSize < 1) {
            ExceptionUtils.throwArgumentOverflowException("pageSize", "pageSize 不能小于1。");
        }
        int offset = (currentPage - 1) * pageSize;
        this.limit(offset, pageSize);
    }

    /**
     * 分页查询
     *
     * @param offset
     * @param limit
     * @return
     */
    public void limit(int offset, int limit) {
        if (offset < 0) {
            offset = 0;
        }
        if (limit < 1) {
            ExceptionUtils.throwValidationException("limit 不能小于0。");
        }
        this.initPageClause();
        this.pageClause.setOffset(offset);
        this.pageClause.setLimit(limit);
    }

    /**
     * @return the orderClauses
     */
    public final List<OrderClause> getOrderClauses() {
        return this.orderClauses;
    }

    /**
     * 移除列
     *
     * @param columnName 列名称
     * @param columns    列集合
     * @param <TColumn>  列类型
     */
    private <TColumn extends ColumnClause> void removeColumn(String columnName, List<TColumn> columns) {
        TColumn column = findColumn(columnName, columns);
        if (column != null) {
            columns.remove(column);
        }
    }

    /**
     * 查找列
     *
     * @param columnName 列名称
     * @param columns    列集合
     * @param <TColumn>  列类型
     */
    private <TColumn extends ColumnClause> TColumn findColumn(String columnName, List<TColumn> columns) {
        if (columns.size() > 0) {
            for (TColumn column : columns) {
                if (column.getColumnName().equalsIgnoreCase(columnName)) {
                    return column;
                }
            }
        }
        return null;
    }

    /**
     * 移除排序
     *
     * @param columnName 列名称
     */
    public void removeOrder(String columnName) {
        this.removeColumn(columnName, this.orderClauses);
    }

    /**
     * 添加排序
     *
     * @param clause 排序
     */
    public void addOrder(OrderClause clause) {
        ExceptionUtils.checkNotNull(clause, "clause");
        removeOrder(clause.getColumnName());
        this.orderClauses.add(clause);
    }

    /**
     * 添加排序
     *
     * @param columnExpression 列表达式
     * @param direction        排序方向
     * @return
     */
    public void addOrder(ColumnExpression columnExpression, OrderDirectionEnum direction) {
        ExceptionUtils.checkNotNull(columnExpression, "columnExpression");
        ExceptionUtils.checkNotNull(direction, "direction");
        removeOrder(columnExpression.getColumnName());
        OrderClause clause = new OrderClause(columnExpression.getColumnName(),
                direction.getValue().toUpperCase(Locale.ENGLISH));
        this.orderClauses.add(clause);
    }

    /**
     * 获取排序列数
     *
     * @return
     */
    public int getOrderColumns() {
        return this.orderClauses.size();
    }

    /**
     * 移除选择
     *
     * @param columnName 列名称
     */
    public void removeSelect(String columnName) {
        this.removeColumn(columnName, this.selectColumns);
    }

    /**
     * 添加列
     *
     * @param columnName 列名称
     * @param fun        函数
     * @param alias      别名
     */
    public void addSelectColumn(String columnName, PolymerizationEnum fun, String alias) {
        SelectColumnClause columnClause = new SelectColumnClause(columnName);
        if (StringUtils.isNullOrBlank(alias)) {
            alias = null;
        }
        if (fun != null) {
            columnClause.setFunName(fun.getValue());
        } else {
            columnClause.setFunName(null);
        }
        if (!StringUtils.isNullOrBlank(columnName)) {
            if (columnName.equals(alias)) {
                columnClause.setAlias(null);
            } else {
                columnClause.setAlias(alias);
            }
            if (fun == null || fun.equals(PolymerizationEnum.COUNT)) {
                this.removeSelect(columnName);
            }
        }
        this.selectColumns.add(columnClause);
    }

    /**
     * 添加分组列
     *
     * @param columnName 列名称
     */
    public void addGroupColumn(String columnName) {
        ColumnClause columnClause = this.findColumn(columnName, this.groups);
        if (columnClause == null) {
            columnClause = new ColumnClause(columnName);
            SelectColumnClause selectColumn = this.findColumn(columnName, this.selectColumns);
            if (selectColumn == null) {
                selectColumn = new SelectColumnClause(columnName);
                selectColumn.setFunName(null);
                this.selectColumns.add(selectColumn);
            } else {
                selectColumn.setFunName(null);
            }
            this.groups.add(columnClause);
        }
    }

    /**
     * 采用表默认排序(如果表配置有默认排序)
     *
     * @return
     */
    private void orderByDefault(List<OrderClause> orderClauses) {
        if (this.table != null && this.table.getOrderColumns() != null
                && this.table.getOrderColumns().size() > 0) {
            for (EntityColumnOrder orderClause : this.table.getOrderColumns()) {
                OrderClause clause = new OrderClause(orderClause.getColumn().getColumnName(),
                        orderClause.getDirection().getValue());
                orderClauses.add(clause);
            }
            if (orderClauses.size() == 0 && this.table.getKeyColumns().size() > 0) {
                for (EntityColumn keyColumn : this.table.getKeyColumns()) {
                    OrderClause clause = new OrderClause(keyColumn.getColumnName(),
                            OrderDirectionEnum.ASC.getValue());
                    orderClauses.add(clause);
                }
            }
        }
    }

    /**
     * 采用表默认排序(如果表配置有默认排序)
     *
     * @return
     */
    public void orderByDefault() {
        this.orderByDefault(this.getOrderClauses());
    }

    @Override
    public void reset() {
        super.reset();
        this.pageClause = null;
        this.havingExpression = null;
        this.orderClauses.clear();
        this.selectColumns.clear();
        this.groups.clear();
    }

    /**
     * 创建段
     *
     * @return
     */
    @Override
    public QuerySection createSection() {
        QuerySectionImpl qs = new QuerySectionImpl(this.table != null ? this.table.getName() : "", this.getLockMode());
        // 条件表达式
        qs.getCriterias().addAll(this.createCriteriaSections());
        // 分组条件表达式
        qs.getHavingCriterias().addAll(this.createHavingCriteriaSections());
        if (StringUtils.isNullOrBlank(this.getAlias())) {
            boolean isSetCriteria = false;
            for (AbstractCriteriaClause criteria : qs.getCriterias()) {
                if (criteria.getOp().equalsIgnoreCase(CriteriaOperatorEnum.IN_SQL.getOperator())
                        || criteria.getOp().equalsIgnoreCase(CriteriaOperatorEnum.NOT_IN_SQL.getOperator())
                        || criteria.getOp().equalsIgnoreCase(CriteriaOperatorEnum.EXISTS.getOperator())) {
                    isSetCriteria = true;
                    break;
                }
            }
            if (isSetCriteria) {
                qs.setAlias(DEFAULT_ALIAS);
            }
        } else {
            qs.setAlias(this.getAlias());
        }

        // 排序
        if (this.orderClauses.size() > 0) {
            qs.getOrderClauses().addAll(this.orderClauses);
        } else {
            if (this.getPageClause() != null) {
                this.orderByDefault(qs.getOrderClauses());
            }
        }
        if (this.selectColumns.size() > 0) {
            qs.getSelectColumns().addAll(this.selectColumns);
            //分组
            if (this.groups.size() > 0) {
                qs.getGroups().addAll(this.groups);
            }
            this.checkSelectOrGroup(qs);
        } else {
            this.defaultSelectColumns(qs);
        }
        // 分页
        if (this.pageClause != null) {
            qs.getPageClause().setOffset(this.pageClause.getOffset());
            qs.getPageClause().setLimit(this.pageClause.getLimit());
        }
        return qs;
    }

    /**
     * 检查 select 与 group
     *
     * @param qs
     */
    private void checkSelectOrGroup(QuerySectionImpl qs) {
        if (qs.getGroups().size() > 0) {
            for (OrderClause orderClause : qs.getOrderClauses()) {
                SelectColumnClause columnClause = this.findColumn(orderClause.getColumnName(), qs.getSelectColumns());
                if (columnClause == null) {
                    throw ExceptionUtils.throwSystemException("排序列[" + orderClause.getColumnName() + "] 未在 SELECT 中。");
                }
                orderClause.setFunName(columnClause.getFunName());
            }
            for (SelectColumnClause selectColumn : qs.getSelectColumns()) {
                if (StringUtils.isNullOrBlank(selectColumn.getFunName())) {
                    selectColumn.setFunName(null);
                    ColumnClause columnClause = this.findColumn(selectColumn.getColumnName(), qs.getGroups());
                    if (columnClause == null) {
                        throw ExceptionUtils.throwSystemException("列[" + selectColumn.getColumnName() + "] 未在 GROUP BY 中。");
                    }
                }
            }
            for (ColumnClause group : qs.getGroups()) {
                SelectColumnClause columnClause = this.findColumn(group.getColumnName(), qs.getSelectColumns());
                if (columnClause == null) {
                    throw ExceptionUtils.throwSystemException("列[" + group.getColumnName() + "]参与GROUP BY 时，未在 SELECT 中。");
                }
                if (!StringUtils.isNullOrBlank(columnClause.getFunName())) {
                    throw ExceptionUtils.throwSystemException("列[" + group.getColumnName() + "]参与GROUP BY 时，在 SELECT 列表不能为聚合函数。");
                }
            }
        }
        for (AbstractCriteriaClause havingCriteria : qs.getHavingCriterias()) {
            if (!StringUtils.isNullOrBlank(havingCriteria.getExpression())
                    && !(havingCriteria.getOp().equalsIgnoreCase(CriteriaOperatorEnum.EXISTS.getOperator()) ||
                    havingCriteria.getOp().equalsIgnoreCase(CriteriaOperatorEnum.NOT_EXISTS.getOperator()))) {
                SelectColumnClause columnClause = this.findColumn(havingCriteria.getExpression(), qs.getSelectColumns());
                if (columnClause == null) {
                    throw ExceptionUtils.throwSystemException("列[" + havingCriteria.getExpression() + "] 作为 HAVING 条件时，未在 SELECT 中。");
                }
                havingCriteria.setFunName(columnClause.getFunName());
            } else {
                havingCriteria.setFunName(null);
            }
        }
    }

    /**
     * 默认查询列集合
     *
     * @param qs
     */
    private void defaultSelectColumns(QuerySectionImpl qs) {
        if (this.table != null) {
            if (this.groups.size() > 0) {
                ExceptionUtils.throwSystemException("未指定 selectBy 时，不能调用 group by。");
            }
            for (EntityColumn column : this.table.getColumns()) {
                SelectColumnClause columnClause = new SelectColumnClause(column.getColumnName());
                if (!column.getColumnName().equals(column.getPropertyName())) {
                    columnClause.setAlias(column.getPropertyName());
                }
                qs.getSelectColumns().add(columnClause);
            }
        } else {
            ExceptionUtils.throwSystemException("至少需要指定 Select 一列以上。");
        }
    }


}
