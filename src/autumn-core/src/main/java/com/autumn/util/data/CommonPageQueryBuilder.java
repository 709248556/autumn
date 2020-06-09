package com.autumn.util.data;

import java.util.List;

import com.autumn.mybatis.mapper.DefaultPageResult;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.util.function.FunctionOneResult;
import com.autumn.util.function.FunctionTwoAction;

/**
 * 公共页查询生成器
 *
 * @author 老码农
 * <p>
 * 2018-04-11 11:28:25
 */
public class CommonPageQueryBuilder extends AbstractPageQueryBuilder<CommonPageQueryBuilder> {

    private final QueryWrapper query;

    /**
     *
     */
    public CommonPageQueryBuilder() {
        this.query = new QueryWrapper();
    }

    @Override
    public QueryWrapper getQuery() {
        return this.query;
    }

    @Override
    public <TResult> PageResult<TResult> toPageResult(FunctionOneResult<QueryWrapper, Integer> countDelegate,
                                                      FunctionOneResult<QueryWrapper, List<TResult>> queryDelegate) {
        return super.toPageResult(countDelegate, queryDelegate);
    }

    @Override
    public <TEntity, TResult> PageResult<TResult> toPageResult(
            FunctionOneResult<QueryWrapper, Integer> countDelegate,
            FunctionOneResult<QueryWrapper, List<TEntity>> queryDelegate, Class<TResult> resultClass) {
        return super.toPageResult(countDelegate, queryDelegate, resultClass);
    }

    @Override
    public <TEntity, TResult> PageResult<TResult> toPageResult(
            FunctionOneResult<QueryWrapper, Integer> countDelegate,
            FunctionOneResult<QueryWrapper, List<TEntity>> queryDelegate, Class<TResult> resultClass,
            FunctionTwoAction<TEntity, TResult> convertDelegate) {
        return super.toPageResult(countDelegate, queryDelegate, resultClass, convertDelegate);
    }

}
