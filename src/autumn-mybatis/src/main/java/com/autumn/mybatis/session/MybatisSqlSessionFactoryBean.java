package com.autumn.mybatis.session;

import org.mybatis.spring.SqlSessionFactoryBean;

/**
 * 会话工厂
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 0:26
 */
public class MybatisSqlSessionFactoryBean extends SqlSessionFactoryBean {

    /**
     *
     */
    public MybatisSqlSessionFactoryBean() {
        this.setSqlSessionFactoryBuilder(new MybatisSqlSessionFactoryBuilder());
    }
}
