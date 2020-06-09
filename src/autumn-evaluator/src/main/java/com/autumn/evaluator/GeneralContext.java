package com.autumn.evaluator;

/**
 * 提供通用上下文
 */
public class GeneralContext implements Context {

    private VariableDictionary variables;

    /**
     * 获取变更集合
     */
    public final VariableDictionary getVariables() {
        return this.variables;
    }

    /**
     * 实例化 GeneralContext 类新实例
     */
    public GeneralContext() {
        this.variables = new VariableDictionary();
    }

    /**
     * 实例化 GeneralContext 类新实例
     *
     * @param context 上下文
     */
    public GeneralContext(Object context) {
        this();
        this.setContext(context);
    }

    /**
     * 是否存在变量
     *
     * @param name 变量名
     * @return
     */
    @Override
    public boolean hasVariable(String name) {
        return this.variables.containsKey(name);
    }

    /**
     * 获取变量值
     *
     * @param name
     * @return
     */
    @Override
    public Variant getVariable(String name) {
        return this.variables.getItem(name);
    }   

    /**
     * 设置变量
     *
     * @param name  名称
     * @param value 值
     */
    @Override
    public void setVariable(String name, Variant value) {
        this.variables.setItem(name, value);
    }

    /**
     * 删除变量
     *
     * @param name 名称
     * @return
     */
    @Override
    public boolean delVariable(String name) {
        return this.variables.remove(name);
    }

    /**
     * 获取上下文
     */
    private Object context;

    @Override
    public Object getContext() {
        return context;
    }

    private void setContext(Object value) {
        context = value;
    }

    /**
     * 加锁
     */
    @Override
    public final void lock() {
        this.variables.lock();
    }

    /**
     * 解锁
     */
    @Override
    public final void unLock() {
        this.variables.unLock();
    }
}