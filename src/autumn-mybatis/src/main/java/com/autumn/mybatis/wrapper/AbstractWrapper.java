package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.wrapper.commands.CriteriaSection;
import com.autumn.mybatis.wrapper.commands.WrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.exception.ExceptionUtils;

import java.util.function.Function;

/**
 * 包装器抽象
 *
 * @param <TChildren> 子级类型
 * @param <TNameFunc> 名称函数类型
 */
public abstract class AbstractWrapper<TChildren extends AbstractWrapper<TChildren, TNameFunc>, TNameFunc>
        extends AbstractParser implements Wrapper {

    private final WrapperCommand wrapperCommand;

    /**
     * 获取包装器命令
     *
     * @return
     */
    public WrapperCommand getWrapperCommand() {
        return this.wrapperCommand;
    }

    /**
     * 无表实例化
     *
     * @param wrapperCommand
     */
    public AbstractWrapper(WrapperCommand wrapperCommand) {
        super();
        this.wrapperCommand = wrapperCommand;
    }

    /**
     * @param entityClass    实体类型
     * @param wrapperCommand 命令
     */
    public AbstractWrapper(Class<?> entityClass, WrapperCommand wrapperCommand) {
        super(entityClass);
        this.wrapperCommand = wrapperCommand;
    }

    /**
     * 返回自身 this
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    protected TChildren returnThis() {
        return (TChildren) this;
    }

    /**
     * 重置
     */
    public TChildren reset() {
        this.change();
        this.setWrapperStatus(null);
        this.wrapperCommand.reset();
        return this.returnThis();
    }

    private CommonCriteria<TChildren, TNameFunc> whereCriteria = null;

    /**
     * 创建条件表达
     *
     * @return
     */
    private CommonCriteria<TChildren, TNameFunc> createWhereCriteria() {
        return new CommonCriteria<>(this.returnThis(), this.getEntityTable(), this::createColumnExpression, (logicalOperater, expression) -> {
            this.wrapperCommand.addEexpression(logicalOperater, expression);
            this.change();
        });
    }

    /**
     * Where 条件
     *
     * @return
     */
    public CommonCriteria<TChildren, TNameFunc> where() {
        if (this.whereCriteria == null) {
            this.whereCriteria = this.createWhereCriteria();
        }
        return this.whereCriteria;
    }

    /**
     * Where 条件
     *
     * @param func 函数
     * @return
     */
    public TChildren where(Function<CommonCriteria<TChildren, TNameFunc>, CommonCriteria<TChildren, TNameFunc>> func) {
        ExceptionUtils.checkNotNull(func, "func");
        func.apply(this.where());
        return this.returnThis();
    }

    /**
     * 锁
     *
     * @param mode 模式
     * @return
     */
    public TChildren lock(LockModeEnum mode) {
        this.wrapperCommand.lock(mode);
        this.change();
        return this.returnThis();
    }

    /**
     * 更新锁
     *
     * @return
     */
    public TChildren lockByUpdate() {
        return this.lock(LockModeEnum.UPDATE);
    }

    /**
     * 共享锁
     *
     * @return
     */
    public TChildren lockByShare() {
        return this.lock(LockModeEnum.SHARE);
    }

    /**
     * 无锁
     *
     * @return
     */
    public TChildren lockByNone() {
        return this.lock(LockModeEnum.NONE);
    }


    /**
     * 创建列表达式
     *
     * @param name 名称或函数
     * @return
     */
    protected abstract ColumnExpression createColumnExpression(TNameFunc name);

    /**
     * 表别名
     *
     * @param alias 别名
     * @return
     */
    public TChildren tableAlias(String alias) {
        this.wrapperCommand.tableAlias(alias);
        this.change();
        return this.returnThis();
    }

    private Integer wrapperStatus;

    /**
     * 获取包装器状态
     * <p>
     * 用于特定的状态管理
     * </p>
     *
     * @return 状态
     */
    public Integer getWrapperStatus() {
        return this.wrapperStatus;
    }

    /**
     * 设置包装器状态
     * <p>
     * 用于特定的状态管理
     * </p>
     *
     * @param wrapperStatus 状态
     */
    public void setWrapperStatus(Integer wrapperStatus) {
        this.wrapperStatus = wrapperStatus;
    }

    /**
     * 变更
     */
    protected void change() {
        this.section = null;
    }

    private CriteriaSection section = null;

    /**
     * 获取段
     *
     * @return
     */
    @Override
    public final CriteriaSection getSection() {
        if (this.section != null) {
            return this.section;
        }
        this.section = this.wrapperCommand.createSection();
        return this.section;
    }

    @Override
    public String toString() {
        return this.getSection().toString();
    }
}
