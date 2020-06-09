package com.autumn.mybatis.session.impl;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.BeanConstant;
import com.autumn.mybatis.session.NoAutomaticDataSourceTransactionSession;
import com.autumn.util.function.FunctionAction;
import com.autumn.util.function.FunctionResult;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 非自动数据源事务会话实现
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-18 19:40
 **/
public class NoAutomaticDataSourceTransactionSessionImpl implements NoAutomaticDataSourceTransactionSession {

    private final ApplicationContext applicationContext;
    private final TransactionDefinition transactionDefinition;

    /**
     * 默认事务管理
     */
    private DataSourceTransactionManager defaultTransactionManager = null;
    private final Map<String, DataSourceTransactionManager> transactionManagerMap = new ConcurrentHashMap<>(10);

    private static final Object DEFAULT_TRANSACTIONLOCK = new Object();

    /**
     * 实例化
     *
     * @param applicationContext 应用上下文
     * @param definition         事务定义
     */
    public NoAutomaticDataSourceTransactionSessionImpl(ApplicationContext applicationContext,
                                                       TransactionDefinition definition) {
        this.applicationContext = applicationContext;
        if (definition != null) {
            this.transactionDefinition = definition;
        } else {
            this.transactionDefinition = new DefaultTransactionDefinition();
        }
    }

    /**
     * 获取默认事务
     *
     * @return
     */
    @Override
    public DataSourceTransactionManager getDefaultTransactionManager() {
        if (this.defaultTransactionManager != null) {
            return this.defaultTransactionManager;
        }
        synchronized (DEFAULT_TRANSACTIONLOCK) {
            if (this.defaultTransactionManager != null) {
                return this.defaultTransactionManager;
            }
            try {
                this.defaultTransactionManager = this.applicationContext.getBean(DataSourceTransactionManager.class);
                return this.defaultTransactionManager;
            } catch (BeansException err) {
                throw ExceptionUtils.throwSystemException("未配置默认数据源事务 DataSourceTransactionManager 。");
            }
        }
    }

    @Override
    public DataSourceTransactionManager getTransactionManager(String beanName) {
        return transactionManagerMap.computeIfAbsent(beanName, key -> {
            ExceptionUtils.checkNotNullOrBlank(key, "beanName");
            try {
                return this.applicationContext.getBean(key, DataSourceTransactionManager.class);
            } catch (BeansException err) {
                throw ExceptionUtils.throwSystemException("未配置数据源事 BeanName 为[" + key + "] 的 DataSourceTransactionManager 对象。");
            }
        });
    }

    /**
     * 获取多数据 Bean名称
     *
     * @param name 数据源名称
     * @return
     */
    private String getMultipleDataSourceBeanName(String name) {
        return BeanConstant.getTransactionManagerBeanName(name);
    }

    @Override
    public DataSourceTransactionManager getMultipleDataSourceTransactionManager(String name) {
        return this.getTransactionManager(this.getMultipleDataSourceBeanName(name));
    }

    /**
     * @param transactionManager
     * @param action
     * @param definition
     */
    private void executeTransaction(DataSourceTransactionManager transactionManager, FunctionAction action, TransactionDefinition definition) {
        ExceptionUtils.checkNotNull(action, "action");
        if (definition == null) {
            definition = this.transactionDefinition;
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = transactionManager.getTransaction(definition);
            action.apply();
            transactionManager.commit(transactionStatus);
        } catch (Exception err) {
            if (transactionStatus != null) {
                transactionManager.rollback(transactionStatus);
            }
            throw err;
        }
    }

    /**
     * 执行事务
     *
     * @param transactionManager
     * @param resultCall
     * @param definition
     * @param <TResult>
     * @return
     */
    private <TResult> TResult executeResultTransaction(DataSourceTransactionManager transactionManager, FunctionResult<TResult> resultCall, TransactionDefinition definition) {
        ExceptionUtils.checkNotNull(resultCall, "resultCall");
        if (definition == null) {
            definition = this.transactionDefinition;
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = transactionManager.getTransaction(definition);
            TResult result = resultCall.apply();
            transactionManager.commit(transactionStatus);
            return result;
        } catch (Exception err) {
            if (transactionStatus != null) {
                transactionManager.rollback(transactionStatus);
            }
            throw err;
        }
    }

    @Override
    public void executeActionByDefault(FunctionAction action) {
        this.executeActionByDefault(action, this.transactionDefinition);
    }

    @Override
    public void executeActionByDefault(FunctionAction action, TransactionDefinition definition) {
        DataSourceTransactionManager transactionManager = this.getDefaultTransactionManager();
        this.executeTransaction(transactionManager, action, definition);
    }

    @Override
    public <TResult> TResult executeResultByDefault(FunctionResult<TResult> resultCall) {
        return this.executeResultByDefault(resultCall, this.transactionDefinition);
    }

    @Override
    public <TResult> TResult executeResultByDefault(FunctionResult<TResult> resultCall, TransactionDefinition definition) {
        DataSourceTransactionManager transactionManager = this.getDefaultTransactionManager();
        return this.executeResultTransaction(transactionManager, resultCall, definition);
    }

    @Override
    public void executeActionByBeanName(String beanName, FunctionAction action) {
        this.executeActionByBeanName(beanName, action, this.transactionDefinition);
    }

    @Override
    public void executeActionByBeanName(String beanName, FunctionAction action, TransactionDefinition definition) {
        DataSourceTransactionManager transactionManager = this.getTransactionManager(beanName);
        this.executeTransaction(transactionManager, action, definition);
    }

    @Override
    public <TResult> TResult executeResultByBeanName(String beanName, FunctionResult<TResult> resultCall) {
        return this.executeResultByBeanName(beanName, resultCall, this.transactionDefinition);
    }

    @Override
    public <TResult> TResult executeResultByBeanName(String beanName, FunctionResult<TResult> resultCall, TransactionDefinition definition) {
        DataSourceTransactionManager transactionManager = this.getTransactionManager(beanName);
        return this.executeResultTransaction(transactionManager, resultCall, definition);
    }

    @Override
    public void executeActionByMultipleDataSource(String name, FunctionAction action) {
        this.executeActionByMultipleDataSource(name, action, this.transactionDefinition);
    }

    @Override
    public void executeActionByMultipleDataSource(String name, FunctionAction action, TransactionDefinition definition) {
        this.executeActionByBeanName(this.getMultipleDataSourceBeanName(name), action, definition);
    }

    @Override
    public <TResult> TResult executeResultByMultipleDataSource(String name, FunctionResult<TResult> resultCall) {
        return this.executeResultByMultipleDataSource(name, resultCall, this.transactionDefinition);
    }

    @Override
    public <TResult> TResult executeResultByMultipleDataSource(String name, FunctionResult<TResult> resultCall, TransactionDefinition definition) {
        return this.executeResultByBeanName(this.getMultipleDataSourceBeanName(name), resultCall, definition);
    }

}
