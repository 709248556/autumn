package com.autumn.mybatis.mapper;

import com.autumn.exception.ExceptionUtils;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-03 15:29:07
 */
public class AutumnMybatisClassPathMapperScanner extends ClassPathMapperScanner {

    private String mapperRegisterBeanName;

    public AutumnMybatisClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
        this.setMapperFactoryBean(new AutumnMapperFactoryBean<>());
    }

    public void setMapperRegisterBeanName(String mapperRegisterBeanName) {
        this.mapperRegisterBeanName = mapperRegisterBeanName;
    }

    @Override
    public void setMapperFactoryBean(MapperFactoryBean<?> mapperFactoryBean) {
        if (mapperFactoryBean == null) {
            super.setMapperFactoryBean(new AutumnMapperFactoryBean<>());
        } else {
            if (mapperFactoryBean instanceof AutumnMapperFactoryBean) {
                super.setMapperFactoryBean(mapperFactoryBean);
            } else {
                ExceptionUtils.throwConfigureException(
                        mapperFactoryBean + " 必须是 " + AutumnMapperFactoryBean.class + " 的实例。");
            }
        }
    }

    /**
     * Calls the parent search that will search and register all the candidates.
     * Then the registered objects are post processed to set them as
     * MapperFactoryBeans
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        processBeanDefinitions(beanDefinitions);
        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        if (StringUtils.hasText(this.mapperRegisterBeanName)) {
            for (BeanDefinitionHolder holder : beanDefinitions) {
                definition = (GenericBeanDefinition) holder.getBeanDefinition();
                definition.getPropertyValues().add("mapperRegister", new RuntimeBeanReference(this.mapperRegisterBeanName));
            }
        }
    }
}
