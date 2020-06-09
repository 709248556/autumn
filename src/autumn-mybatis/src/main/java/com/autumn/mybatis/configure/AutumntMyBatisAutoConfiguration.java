package com.autumn.mybatis.configure;

import com.autumn.mybatis.event.DataSourceListener;
import com.autumn.mybatis.event.DataSourceListenerContext;
import com.autumn.mybatis.session.MybatisSqlSessionFactoryBean;
import com.autumn.mybatis.session.NoAutomaticDataSourceTransactionSession;
import com.autumn.mybatis.session.impl.NoAutomaticDataSourceTransactionSessionImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.TransactionDefinition;

/**
 * 自动配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-24 20:17
 */
@Configuration
public class AutumntMyBatisAutoConfiguration {

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(AutumntMyBatisAutoConfiguration.class);

    /**
     * 非自动数据源事务会话
     *
     * @param applicationContext 应用上下文
     * @param definition         事务定义
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(NoAutomaticDataSourceTransactionSession.class)
    public NoAutomaticDataSourceTransactionSession noAutomaticDataSourceTransactionSession(ApplicationContext applicationContext, TransactionDefinition definition) {
        return new NoAutomaticDataSourceTransactionSessionImpl(applicationContext, definition);
    }

    /**
     * 拦截
     *
     * @return
     */
    @Bean
    public BeanPostProcessor autumntMyBatisBeanPostProcessor(MybatisSqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
        SqlSessionFactory sessionFactory = sqlSessionFactoryBean.getObject();
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof Interceptor) {
                    Interceptor interceptor = (Interceptor) bean;
                    sessionFactory.getConfiguration().addInterceptor(interceptor);
                    LOGGER.info("注册 Mybatis Interceptor:" + interceptor);
                }
                if (bean instanceof DataSourceListener) {
                    DataSourceListenerContext.registerEvent((DataSourceListener) bean);
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }
}
