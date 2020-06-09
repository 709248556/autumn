package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.wrapper.commands.WrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;

/**
 * 条件包装
 *
 * @author 老码农
 * <p>
 * 2017-10-26 14:51:37
 */
public class CriteriaWrapper extends AbstractWrapper<CriteriaWrapper, String> {  

    /**
     * 无表实例化
     */
    public CriteriaWrapper() {
        super(WrapperCommand.createWrapper());
    }

    /**
     * 转入 entityClass 实例化
     *
     * @param entityClass 实体类型
     */
    public CriteriaWrapper(Class<?> entityClass) {
        super(entityClass, WrapperCommand.createWrapper());
    }

    /**
     * @param name
     * @return
     */
    @Override
    protected ColumnExpression createColumnExpression(String name) {
        return this.column(name);
    }
}
