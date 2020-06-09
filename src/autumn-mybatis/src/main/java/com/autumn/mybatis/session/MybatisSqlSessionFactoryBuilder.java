package com.autumn.mybatis.session;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 会话工厂生成器
 *
 * @author 老码农
 * <p>
 * 2017-10-19 12:41:07
 */
public class MybatisSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {

    /**
     * 生成会话工厂
     */
    @Override
    public SqlSessionFactory build(Configuration config) {
        return new MybatisSqlSessionFactory(config);
    }
}
