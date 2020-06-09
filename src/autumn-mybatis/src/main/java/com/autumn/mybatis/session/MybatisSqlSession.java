package com.autumn.mybatis.session;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

/**
 * 上下文会话
 *
 * @author 老码农
 * <p>
 * 2017-10-19 12:27:36
 */
public class MybatisSqlSession extends DefaultSqlSession {

    /**
     * 实例化 ContextSession 新实例
     *
     * @param configuration 配置
     * @param executor      执行器
     */
    public MybatisSqlSession(Configuration configuration, Executor executor) {
        this(configuration, executor, false);
    }

    /**
     * 实例化 ContextSession 新实例
     *
     * @param configuration 配置
     * @param executor      执行器
     * @param autoCommit    自动提交
     */
    public MybatisSqlSession(Configuration configuration, Executor executor, boolean autoCommit) {
        super(configuration, executor, autoCommit);
    }
}
