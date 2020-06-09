package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.wrapper.commands.UpdateWrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;

/**
 * 更新包装抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-09 1:19
 */
abstract class AbstractUpdateWrapper<TChildren extends AbstractUpdateWrapper<TChildren, TNameFunc>, TNameFunc>
        extends AbstractWrapper<TChildren, TNameFunc> {

    /**
     * 实例化
     *
     * @param entityClass    实体类型
     * @param wrapperCommand 包装命令
     */
    public AbstractUpdateWrapper(Class<?> entityClass, UpdateWrapperCommand wrapperCommand) {
        super(entityClass, wrapperCommand);
    }

    /**
     * @return
     */
    @Override
    public UpdateWrapperCommand getWrapperCommand() {
        return (UpdateWrapperCommand) super.getWrapperCommand();
    }

    /**
     * 设置更新
     *
     * @param nameFunc 属性或列名称
     * @param value    值
     * @return
     */
    public TChildren set(TNameFunc nameFunc, Object value) {
        ColumnExpression exp = this.createColumnExpression(nameFunc);
        value = this.constantValue(exp, value);
        this.getWrapperCommand().set(exp, value);
        this.change();
        return this.returnThis();
    }
}
