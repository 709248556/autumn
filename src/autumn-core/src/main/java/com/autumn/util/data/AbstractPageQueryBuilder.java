package com.autumn.util.data;

import java.util.ArrayList;
import java.util.List;

import com.autumn.application.dto.input.DefaultPageQueryInput;
import com.autumn.application.dto.input.PageQueryInput;
import com.autumn.mybatis.mapper.DefaultPageResult;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.function.FunctionOneResult;
import com.autumn.util.function.FunctionTwoAction;

/**
 * 页查询抽象
 *
 * @author 老码农
 * <p>
 * 2018-04-11 10:26:40
 */
public abstract class AbstractPageQueryBuilder<TQueryBuilder extends AbstractPageQueryBuilder<TQueryBuilder>>
        extends AbstractQueryBuilder<TQueryBuilder> {

    private PageQueryInput input = null;

    /**
     *
     */
    public AbstractPageQueryBuilder() {

    }

    /**
     * 页
     *
     * @param input       输入
     * @param maxPageSize 最大页大小
     * @return
     */
    public TQueryBuilder page(PageQueryInput input, int maxPageSize) {
        if (input == null) {
            input = new DefaultPageQueryInput();
        }
        if (input.getCurrentPage() < 1) {
            input.setCurrentPage(1);
        }
        if (input.getPageSize() < 1) {
            input.setPageSize(1);
        }
        if (maxPageSize > 0) {
            if (input.getPageSize() > maxPageSize) {
                input.setPageSize(maxPageSize);
            }
        }
        this.criteriaByItem(input.getCriterias());
        this.input = input;
        return this.returnThis();
    }

    /**
     * 页
     *
     * @param input 输入
     * @return
     */
    public TQueryBuilder page(PageQueryInput input) {
        return this.page(input, -1);
    }

    /**
     * 页
     *
     * @param currentPage 当前页
     * @param pageSize    页大小
     * @return
     */
    public TQueryBuilder page(int currentPage, int pageSize) {
        if (this.input == null) {
            this.input = new DefaultPageQueryInput();
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        this.input.setCurrentPage(currentPage);
        this.input.setPageSize(pageSize);
        return this.returnThis();
    }

    /**
     * 重置,清除所有条件
     *
     * @return
     */
    @Override
    public TQueryBuilder reset() {
        super.reset();
        this.input = null;
        return this.returnThis();
    }

    /**
     * 记录数量
     *
     * @param countDelegate 统计委托
     * @return
     */
    protected int toCount(FunctionOneResult<QueryWrapper, Integer> countDelegate) {
        if (this.getQuery().getOrderColumns() == 0) {
            ExceptionUtils.throwValidationException("无排序列,无法分页。");
        }
        if (this.input == null) {
            ExceptionUtils.throwValidationException("未设置页信息。");
        }
        return countDelegate.apply(this.getQuery());
    }

    /**
     * 输出结果
     *
     * @param countDelegate 统计委托
     * @param queryDelegate 查询委托
     * @return
     */
    protected <TResult> PageResult<TResult> toPageResult(FunctionOneResult<QueryWrapper, Integer> countDelegate,
                                                         FunctionOneResult<QueryWrapper, List<TResult>> queryDelegate) {
        int count = toCount(countDelegate);//执行了查询动作
        DefaultPageResult<TResult> page = new DefaultPageResult<>(this.input.getCurrentPage(),
                this.input.getPageSize(), count);
        this.getQuery().page(page.getCurrentPage(), page.getPageSize());
        if (count > 0) {
            List<TResult> entitys = queryDelegate.apply(this.getQuery());//执行了查询动作
            page.setItems(entitys);
        } else {
            page.setItems(new ArrayList<>());
        }
        return page;
    }

    /**
     * 输出结果
     *
     * @param countDelegate 统计委托
     * @param queryDelegate 查询委托
     * @param resultClass   结果类型
     * @return
     */
    protected <TEntity, TResult> PageResult<TResult> toPageResult(
            FunctionOneResult<QueryWrapper, Integer> countDelegate,
            FunctionOneResult<QueryWrapper, List<TEntity>> queryDelegate, Class<TResult> resultClass) {
        return this.toPageResult(countDelegate, queryDelegate, resultClass, null);
    }

    /**
     * 输出结果
     *
     * @param countDelegate   统计委托
     * @param queryDelegate   查询委托
     * @param resultClass     结果类型
     * @param convertDelegate 转换委托
     * @return
     */
    protected <TEntity, TResult> PageResult<TResult> toPageResult(
            FunctionOneResult<QueryWrapper, Integer> countDelegate,
            FunctionOneResult<QueryWrapper, List<TEntity>> queryDelegate, Class<TResult> resultClass,
            FunctionTwoAction<TEntity, TResult> convertDelegate) {
        int count = toCount(countDelegate);
        DefaultPageResult<TResult> page = new DefaultPageResult<TResult>(this.input.getCurrentPage(),
                this.input.getPageSize(), count);
        this.getQuery().page(page.getCurrentPage(), page.getPageSize());
        if (count > 0) {
            List<TEntity> entitys = queryDelegate.apply(this.getQuery());
            List<TResult> items = AutoMapUtils.mapForList(entitys, resultClass, convertDelegate);
            page.setItems(items);
        } else {
            page.setItems(new ArrayList<>());
        }
        return page;
    }
}
