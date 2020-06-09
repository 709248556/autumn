package com.autumn.evaluator;

/**
 * 上下文接口
 */
public interface Context {

    /**
     * 是否存在变量
     *
     * @param name 变量名
     * @return
     */
    boolean hasVariable(String name);

    /**
     * 获取变量值
     *
     * @param name 名称
     * @return
     */
    Variant getVariable(String name);
    
    /**
     * 设置变量
     *
     * @param name  变量名
     * @param value 值
     */
    void setVariable(String name, Variant value);

    /**
     * 删除变量
     *
     * @param name 变量名
     * @return
     */
    boolean delVariable(String name);

    /**
     * 加锁
     */
    void lock();

    /**
     * 解锁
     */
    void unLock();

    /**
     * 获取上下文
     */
    Object getContext();

}

