package com.autumn.spring.boot.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

/**
 * Bean 定义注册抽象
 *
 * @author 老码农
 * <p>
 * 2017-11-27 17:52:52
 */
public abstract class AbstractImportBeanRegistrar
        implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware, BeanFactoryAware {

    /**
     * 日志
     */
    public final Log logger = LogFactory.getLog(this.getClass());

    private Environment environment;
    private ResourceLoader resourceLoader;
    private BeanFactory beanFactory;


    /**
     * 获取环境
     *
     * @return
     */
    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 取获资源适配器
     *
     * @return
     */
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 取获Bean 工厂
     *
     * @return
     */
    public BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

}
