package com.autumn.evaluator.configure;

import com.autumn.evaluator.Callable;
import com.autumn.evaluator.DefaultEvaluatorSession;
import com.autumn.evaluator.EvaluatorSession;
import com.autumn.evaluator.FunctionManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 解析注册函数配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-12 17:44
 */
@Configuration
public class AutumnEvaluatorConfiguration {

    /**
     * 解析会话
     *
     * @return
     */
    @Bean
    public EvaluatorSession autumnEvaluatorSession() {
        return new DefaultEvaluatorSession();
    }

    /**
     * 拦截
     *
     * @return
     */
    @Bean
    public BeanPostProcessor autumnEvaluatorBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof Callable) {
                    Callable callable = (Callable) bean;
                    FunctionManager.register(callable);
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
