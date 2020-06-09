package com.autumn.mybatis.plugins;

import com.autumn.exception.ConfigureException;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.MapperUtils;
import com.autumn.spring.boot.context.AutumnApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 执行器抽象
 *
 * @author 老码农 2019-06-11 02:42:07
 */
public abstract class AbstractInterceptor
        implements AutumnMybatisInterceptor,
        ApplicationContextAware, Ordered {

    /**
     * 日志
     */
    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * 获取内置插件，此类插件不能注册，由系统自动注册
     *
     * @return
     */
    public static Collection<Class<? extends Interceptor>> getBuiltInPlugins() {
        Set<Class<? extends Interceptor>> plugins = new LinkedHashSet<>();
        plugins.add(KeyAssignerInterceptor.class);
        plugins.add(UpdateRelationInterceptor.class);
        plugins.add(QueryRelationInterceptor.class);
        plugins.add(PageInterceptor.class);
        return plugins;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        if (applicationContext != null) {
            MapperUtils.setApplicationContext(applicationContext);
        }
    }

    /**
     * 获取应用上下文
     *
     * @return
     */
    protected ApplicationContext getAppContext() {
        if (this.applicationContext != null) {
            return this.applicationContext;
        }
        this.applicationContext = AutumnApplicationContext.getContext();
        if (this.applicationContext == null) {
            ExceptionUtils.throwSystemException(
                    "AutumnApplicationContext 的 context 为null，是否引用 autumn-spring-boot-starter 。");
        }
        return this.applicationContext;
    }

    /**
     * 获取 Bean
     *
     * @param requiredType
     * @param <T>
     * @return
     * @throws BeansException
     */
    protected final <T> T getBean(Class<T> requiredType) {
        try {
            return this.getAppContext().getBean(requiredType);
        } catch (BeansException e) {
            throw new ConfigureException("未实现 " + requiredType.getName() + " 接口或无默认实现。" + e.getMessage(), e);
        }
    }

    /**
     * 读取目标
     *
     * @param target
     * @param <T>
     * @return
     */
    protected Object realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        } else {
            return target;
        }
    }
}
