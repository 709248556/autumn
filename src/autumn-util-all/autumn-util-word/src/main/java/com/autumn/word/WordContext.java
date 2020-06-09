package com.autumn.word;


import com.autumn.evaluator.Context;
import com.autumn.evaluator.VariableDictionary;
import com.autumn.evaluator.Variant;

/**
 * Word 上下文
 */
public class WordContext implements Context {
    private VariableDictionary dic;
    private VariableDictionary backDic;
    private WordSession session;

    private boolean isBackup;

    /**
     * 实例化 WordContext 类新实例
     *
     * @param obj     对象
     * @param session 会话
     */
    public WordContext(Object obj, WordSession session) {
        this.isBackup = false;
        this.dic = new VariableDictionary();
        this.dic.load(obj);
        this.backDic = new VariableDictionary();
        this.session = session;
    }

    /**
     * 获取上下文
     */
    @Override
    public final Object getContext() {
        return this.session;
    }

    /**
     * 获取是否正存在变量
     *
     * @param name 名称
     * @return
     */
    @Override
    public final boolean hasVariable(String name) {
        return this.dic.containsKey(name);
    }

    /**
     * 获取变量
     *
     * @param name 名称
     * @return
     */
    @Override
    public final Variant getVariable(String name) {
        return this.dic.getItem(name);
    }

    /**
     * 设置变量
     *
     * @param name  名称
     * @param value 值
     */
    @Override
    public final void setVariable(String name, Variant value) {
        this.dic.setItem(name, value);
    }

    /**
     * 删除变量
     *
     * @param name 名称
     * @return
     */
    @Override
    public final boolean delVariable(String name) {
        return this.dic.remove(name);
    }

    /**
     * 加锁
     */
    @Override
    public final void lock() {
        this.dic.lock();
    }

    /**
     * 解锁
     */
    @Override
    public final void unLock() {
        this.dic.unLock();
    }

    /**
     * 备份
     */
    public final void backup() {
        try {
            this.dic.lock();
            if (!this.isBackup) {
                this.backDic.clear();
                for (String key : this.dic.getKeys()) {
                    this.backDic.setItem(key, this.dic.getItem(key));
                }
                this.isBackup = true;
            }
        } finally {
            this.dic.unLock();
        }
    }

    /**
     * 还原
     */
    public final void revert() {
        try {
            this.dic.lock();
            if (this.isBackup) {
                this.dic.clear();
                for (String key : this.backDic.getKeys()) {
                    this.dic.setItem(key, this.backDic.getItem(key));
                }
                this.backDic.clear();
                this.isBackup = false;
            }
        } finally {
            this.dic.unLock();
        }
    }

}