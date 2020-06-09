package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.commands.UpdateWrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;

/**
 * 指定更新
 *
 * @author 老码农
 * <p>
 * 2017-10-30 18:37:10
 */
public class UpdateWrapper extends AbstractUpdateWrapper<UpdateWrapper, String> {

    /**
     * 无表实例化
     */
    public UpdateWrapper() {
        super(null, UpdateWrapperCommand.createUpdateWrapper(null));
    }

    /**
     * 转入 entityClass 实例化
     *
     * @param entityClass 实体类型
     */
    public UpdateWrapper(Class<?> entityClass) {
        super(entityClass, UpdateWrapperCommand.createUpdateWrapper(EntityTable.getTable(entityClass)));
    }

    @Override
    protected ColumnExpression createColumnExpression(String name) {
        return this.column(name);
    }

}
