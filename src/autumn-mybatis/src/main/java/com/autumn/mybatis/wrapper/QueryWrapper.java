package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.commands.QueryWrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;

/**
 * 查询包装器
 *
 * @author 老码农
 * <p>
 * 2017-10-26 14:41:49
 */
public class QueryWrapper extends AbstractQueryWrapper<QueryWrapper, String> {

    /**
     * 默认页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 50;

    /**
     * 无表实例化
     */
    public QueryWrapper() {
        super(null, QueryWrapperCommand.createQueryWrapper(null));
    }

    /**
     * 转入 entityClass 实例化
     *
     * @param entityClass 实体类型
     */
    public QueryWrapper(Class<?> entityClass) {
        super(entityClass, QueryWrapperCommand.createQueryWrapper(EntityTable.getTable(entityClass)));
    }

    @Override
    protected ColumnExpression createColumnExpression(String name) {
        return this.column(name);
    }
}
