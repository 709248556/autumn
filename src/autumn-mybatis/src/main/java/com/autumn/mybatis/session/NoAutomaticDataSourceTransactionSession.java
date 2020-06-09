package com.autumn.mybatis.session;


import com.autumn.util.function.FunctionAction;
import com.autumn.util.function.FunctionResult;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * 非自动数据源事务会话
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-18 19:40
 **/
public interface NoAutomaticDataSourceTransactionSession {

    /**
     * 获取默认事务
     *
     * @return
     */
    DataSourceTransactionManager getDefaultTransactionManager();

    /**
     * 获取事务管理
     *
     * @param beanName bean名称
     * @return
     */
    DataSourceTransactionManager getTransactionManager(String beanName);

    /**
     * 获取多数源事务管理
     *
     * @param name 数据源名称
     * @return
     */
    DataSourceTransactionManager getMultipleDataSourceTransactionManager(String name);

    /**
     * 执行默认事务
     *
     * @param action 执行
     */
    void executeActionByDefault(FunctionAction action);

    /**
     * 执行默认事务
     *
     * @param action                执行
     * @param TransactionDefinition 事务定义
     */
    void executeActionByDefault(FunctionAction action, TransactionDefinition definition);

    /**
     * 执行默认事务，并返回值
     *
     * @param resultCall 返回调用
     * @param <TResult>  返回
     * @return
     */
    <TResult> TResult executeResultByDefault(FunctionResult<TResult> resultCall);

    /**
     * 执行默认事务，并返回值
     *
     * @param resultCall 返回调用
     * @param definition 事务定义
     * @param <TResult>  返回
     * @return
     */
    <TResult> TResult executeResultByDefault(FunctionResult<TResult> resultCall, TransactionDefinition definition);

    /**
     * 根据 BeanName 执行事务
     *
     * @param beanName bean名称
     * @param action   执行
     */
    void executeActionByBeanName(String beanName, FunctionAction action);

    /**
     * 根据 BeanName 执行事务
     *
     * @param beanName              bean名称
     * @param action                执行
     * @param TransactionDefinition 事务定义
     */
    void executeActionByBeanName(String beanName, FunctionAction action, TransactionDefinition definition);

    /**
     * 根据 BeanName 执行事务，并返回值
     *
     * @param beanName   bean名称
     * @param resultCall 返回调用
     * @param <TResult>  返回
     * @return
     */
    <TResult> TResult executeResultByBeanName(String beanName, FunctionResult<TResult> resultCall);

    /**
     * 根据 BeanName 执行事务，并返回值
     *
     * @param beanName   bean名称
     * @param resultCall 返回调用
     * @param definition 事务定义
     * @param <TResult>  返回
     * @return
     */
    <TResult> TResult executeResultByBeanName(String beanName, FunctionResult<TResult> resultCall, TransactionDefinition definition);

    /**
     * 根据 BeanName 执行事务
     *
     * @param name   数据源名称
     * @param action 执行
     */
    void executeActionByMultipleDataSource(String name, FunctionAction action);

    /**
     * 根据 BeanName 执行事务
     *
     * @param name                  数据源名称
     * @param action                执行
     * @param TransactionDefinition 事务定义
     */
    void executeActionByMultipleDataSource(String name, FunctionAction action, TransactionDefinition definition);

    /**
     * 根据 BeanName 执行事务，并返回值
     *
     * @param name       数据源名称
     * @param resultCall 返回调用
     * @param <TResult>  返回
     * @return
     */
    <TResult> TResult executeResultByMultipleDataSource(String name, FunctionResult<TResult> resultCall);

    /**
     * 根据 BeanName 执行事务，并返回值
     *
     * @param name       数据源名称
     * @param resultCall 返回调用
     * @param definition 事务定义
     * @param <TResult>  返回
     * @return
     */
    <TResult> TResult executeResultByMultipleDataSource(String name, FunctionResult<TResult> resultCall, TransactionDefinition definition);

}
