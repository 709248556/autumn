package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.wrapper.commands.QueryWrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.exception.ExceptionUtils;

import java.util.function.Function;

/**
 * 查询包装抽象
 *
 * @param <TChildren>
 * @param <TNameFunc>
 */
abstract class AbstractQueryWrapper<TChildren extends AbstractQueryWrapper<TChildren, TNameFunc>, TNameFunc>
        extends AbstractWrapper<TChildren, TNameFunc> {

    /**
     * 实例化
     *
     * @param entityClass    实体类型
     * @param wrapperCommand 包装命令
     */
    public AbstractQueryWrapper(Class<?> entityClass, QueryWrapperCommand wrapperCommand) {
        super(entityClass, wrapperCommand);
    }

    /**
     * @return
     */
    @Override
    public QueryWrapperCommand getWrapperCommand() {
        return (QueryWrapperCommand) super.getWrapperCommand();
    }

    private SelectColumnSection<TChildren, TNameFunc> selectWrapper = null;

    /**
     * 查询列
     *
     * @return 返回列表达式
     */
    public SelectColumnSection<TChildren, TNameFunc> select() {
        if (this.selectWrapper == null) {
            this.selectWrapper = new SelectColumnSection<>(this.returnThis(), this.getWrapperCommand(), (n) -> {
                this.change();
                return this.createColumnExpression(n);
            });
        }
        return this.selectWrapper;
    }

    /**
     * 查询列
     *
     * @param func 函数
     * @return 返回列表达式
     */
    public TChildren select(Function<SelectColumnSection<TChildren, TNameFunc>, SelectColumnSection<TChildren, TNameFunc>> func) {
        ExceptionUtils.checkNotNull(func, "func");
        func.apply(this.select());
        return this.returnThis();
    }

    private CommonCriteria<TChildren, TNameFunc> havingCriteria = null;

    /**
     * HAVING 条件
     *
     * @return
     */
    public CommonCriteria<TChildren, TNameFunc> having() {
        if (this.havingCriteria == null) {
            this.havingCriteria = new CommonCriteria<>(this.returnThis(), this.getEntityTable(), this::createColumnExpression, (logicalOperater, expression) -> {
                this.getWrapperCommand().addHavingEexpression(logicalOperater, expression);
                this.change();
            });
        }
        return this.havingCriteria;
    }

    /**
     * HAVING 条件
     *
     * @param func 函数
     * @return
     */
    public TChildren having(Function<CommonCriteria<TChildren, TNameFunc>, CommonCriteria<TChildren, TNameFunc>> func) {
        ExceptionUtils.checkNotNull(func, "func");
        func.apply(this.having());
        return this.returnThis();
    }

    /**
     * 执行分组 Group By field
     *
     * @param nameFunc 名称或函数
     * @return
     */
    public TChildren groupBy(TNameFunc nameFunc) {
        ExceptionUtils.checkNotNull(nameFunc, "nameFunc");
        ColumnExpression col = this.createColumnExpression(nameFunc);
        this.getWrapperCommand().addGroupColumn(col.getColumnName());
        return this.returnThis();
    }

    /**
     * 跳过记录数(相当于调用 limit)
     *
     * @param count 数量
     * @return
     */
    public TChildren skip(int count) {
        this.getWrapperCommand().skip(count);
        this.change();
        return this.returnThis();
    }

    /**
     * 从开头返回记录数 (相当于调用 limit)
     *
     * @param count 数量
     * @return
     */
    public TChildren take(int count) {
        this.getWrapperCommand().take(count);
        this.change();
        return this.returnThis();
    }

    /**
     * 分页
     *
     * @param currentPage 当前页
     * @param pageSize    页大小
     * @return
     */
    public TChildren page(int currentPage, int pageSize) {
        if (currentPage < 1) {
            ExceptionUtils.throwArgumentOverflowException("currentPage", "currentPage 不能小于1。");
        }
        if (pageSize < 1) {
            ExceptionUtils.throwArgumentOverflowException("pageSize", "pageSize 不能小于1。");
        }
        int offset = (currentPage - 1) * pageSize;
        return limit(offset, pageSize);
    }

    /**
     * 分页查询
     *
     * @param offset (偏移)跳过记录
     * @param limit  限制记录数
     * @return
     */
    public TChildren limit(int offset, int limit) {
        this.getWrapperCommand().limit(offset, limit);
        this.change();
        return this.returnThis();
    }

    /**
     * 排序
     *
     * @param nameFunc  属性或列名称
     * @param direction 排序方向
     * @return
     */
    public TChildren order(TNameFunc nameFunc, OrderDirectionEnum direction) {
        ExceptionUtils.checkNotNull(nameFunc, "nameFunc");
        ExceptionUtils.checkNotNull(direction, "direction");
        ColumnExpression col = this.createColumnExpression(nameFunc);
        this.getWrapperCommand().addOrder(col, direction);
        this.change();
        return this.returnThis();
    }

    /**
     * 升序
     *
     * @param nameFunc 属性或列名称
     * @return
     */
    public TChildren orderBy(TNameFunc nameFunc) {
        return order(nameFunc, OrderDirectionEnum.ASC);
    }

    /**
     * 降序
     *
     * @param nameFunc 属性或列名称
     * @return
     */
    public TChildren orderByDescending(TNameFunc nameFunc) {
        return order(nameFunc, OrderDirectionEnum.DESC);
    }

    /**
     * 采用表默认排序(如果表配置有默认排序，无配置则采用主键)
     *
     * @return
     */
    public TChildren orderByDefault() {
        this.getWrapperCommand().orderByDefault();
        this.change();
        return this.returnThis();
    }

    /**
     * 获取排序列数
     *
     * @return
     */
    public int getOrderColumns() {
        return this.getWrapperCommand().getOrderColumns();
    }

    /**
     * 是否是分页
     *
     * @return
     */
    public boolean isPage() {
        return this.getWrapperCommand().getPageClause() != null;
    }

    /**
     * 获取限制大小
     *
     * @return 非分页时分返回 -1
     */
    public int getLimit() {
        if (this.isPage()) {
            return this.getWrapperCommand().getPageClause().getLimit();
        } else {
            return -1;
        }
    }

    /**
     * 获取偏移量
     *
     * @return 非分页时分返回 -1
     */
    public int getOffset() {
        if (this.isPage()) {
            return this.getWrapperCommand().getPageClause().getOffset();
        } else {
            return -1;
        }
    }

    /**
     * 获取当前页
     *
     * @return 非分页时分返回 -1
     */
    public int getCurrentPage() {
        if (this.isPage()) {
            int offset = this.getOffset();
            int limit = this.getLimit();
            int currentPage = (offset / limit) + 1;
            if (currentPage < 1) {
                currentPage = 1;
            }
            return currentPage;
        } else {
            return -1;
        }
    }

}
