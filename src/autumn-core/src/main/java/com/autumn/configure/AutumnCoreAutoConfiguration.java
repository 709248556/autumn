package com.autumn.configure;

import com.autumn.audited.*;
import com.autumn.domain.repositories.plugins.DeleteAuditingInterceptor;
import com.autumn.domain.repositories.plugins.InsertAuditingInterceptor;
import com.autumn.domain.repositories.plugins.SelectAuditingInterceptor;
import com.autumn.domain.repositories.plugins.UpdateAuditingInterceptor;
import com.autumn.events.EntityEventListener;
import com.autumn.events.EventBus;
import com.autumn.events.TransactionEventListener;
import com.autumn.events.impl.EventBusImpl;
import com.autumn.events.plugins.DeleteEntityEventListenerInterceptor;
import com.autumn.events.plugins.InsertEntityEventListenerInterceptor;
import com.autumn.events.plugins.UpdateEntityEventListenerInterceptor;
import com.autumn.runtime.cache.DataCache;
import com.autumn.runtime.cache.DataCacheManager;
import com.autumn.runtime.cache.ProxyCacheManager;
import com.autumn.runtime.cache.impl.DataCacheManagerImpl;
import com.autumn.runtime.cache.impl.ProxyCacheManagerImpl;
import com.autumn.runtime.cache.interceptor.MethodParamKeyGenerator;
import com.autumn.runtime.cache.interceptor.SimpleParamKeyGenerator;
import com.autumn.runtime.cache.interceptor.TargetTypeMethodParamKeyGenerator;
import com.autumn.runtime.deserializer.json.InputObjectDeserializerGenerator;
import com.autumn.runtime.env.AutumnEnvironment;
import com.autumn.runtime.env.DefaultAutumnEnvironment;
import com.autumn.runtime.exception.filter.*;
import com.autumn.runtime.session.AutumnSession;
import com.autumn.runtime.session.AutumnSessionManager;
import com.autumn.runtime.session.DefaultAutumnSessionManager;
import com.autumn.runtime.session.NullAutumnSession;
import com.autumn.runtime.task.DefaultTaskExecutorService;
import com.autumn.runtime.task.TaskExecutorService;
import com.autumn.timing.Clock;
import com.autumn.timing.ClockProvider;
import com.autumn.timing.LocalClockProvider;
import com.autumn.util.json.JsonObjectDeserializerGenerator;
import com.autumn.util.json.JsonUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 核心自动配置
 *
 * @author 老码农 2018-12-07 01:42:29
 */
@Configuration
public class AutumnCoreAutoConfiguration {

    /**
     * 环境
     *
     * @param environment
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnEnvironment.class)
    public AutumnEnvironment autumnEnvironment(ConfigurableEnvironment environment) {
        return new DefaultAutumnEnvironment(environment);
    }

    /**
     * 空会话
     *
     * @return
     */
    @Bean("nullAutumnSession")
    @ConditionalOnMissingBean(AutumnSession.class)
    public AutumnSession autumnNullAutumnSession() {
        return new NullAutumnSession();
    }

    /**
     * 默认会话管理，此会话将无任何实现，根据具体使用哪个安全框架进行代替
     *
     * @return
     */
    @Bean("defaultAutumnSessionManager")
    @ConditionalOnMissingBean(AutumnSessionManager.class)
    public AutumnSessionManager defaultAutumnSessionManager() {
        return new DefaultAutumnSessionManager();
    }

    /**
     * 默认任务队列服务
     *
     * @return
     */
    @Bean("defaultAutumnTaskExecutorService")
    @ConditionalOnMissingBean(TaskExecutorService.class)
    public TaskExecutorService defaultAutumnTaskExecutorService() {
        return new DefaultTaskExecutorService();
    }

    /**
     * 客户端驱动
     *
     * @return
     */
    @Bean("emptyClientInfoProvider")
    @ConditionalOnMissingBean(ClientInfoProvider.class)
    public ClientInfoProvider autumnEmptyClientInfoProvider() {
        return new EmptyClientInfoProvider();
    }

    /**
     * 审计日志存储
     *
     * @return
     */
    @Bean("emptyAuditedLogStorage")
    @ConditionalOnMissingBean(AuditedLogStorage.class)
    public AuditedLogStorage autumnEmptyAuditedLogStorage() {
        return new EmptyAuditedLogStorage();
    }

    /**
     * 本地时钟提供程序
     *
     * @return
     */
    @Bean("localClockProvider")
    @ConditionalOnMissingBean(ClockProvider.class)
    public ClockProvider autumnLocalClockProvider() {
        return new LocalClockProvider();
    }

    /**
     * 代理缓存
     *
     * @return
     */
    @Bean("autumnProxyCacheManager")
    @Primary
    @ConditionalOnMissingBean(ProxyCacheManager.class)
    public ProxyCacheManager autumnProxyCacheManager() {
        return new ProxyCacheManagerImpl();
    }

    /**
     * 操作日志审计
     *
     * @param session
     * @param clientInfoProvider
     * @param auditedLogStorage
     * @return
     */
    @Bean("defaultOperationAuditedLog")
    @ConditionalOnMissingBean(OperationAuditedLog.class)
    public OperationAuditedLog autumnDefaultOperationAuditedLog(AutumnSession session, ClientInfoProvider clientInfoProvider, AuditedLogStorage auditedLogStorage) {
        return new DefaultOperationAuditedLog(session, clientInfoProvider, auditedLogStorage);
    }

    /**
     * 登录日志审计
     *
     * @param clientInfoProvider
     * @param auditedLogStorage
     * @return
     */
    @Bean("defaultLoginAuditedLog")
    @ConditionalOnMissingBean(LoginAuditedLog.class)
    public LoginAuditedLog autumnDefaultLoginAuditedLog(ClientInfoProvider clientInfoProvider, AuditedLogStorage auditedLogStorage) {
        return new DefaultLoginAuditedLog(clientInfoProvider, auditedLogStorage);
    }

    /**
     * 简单参数键生成器
     *
     * @return
     */
    @Bean(SimpleParamKeyGenerator.BEAN_NAME)
    @Primary
    @ConditionalOnMissingBean(SimpleParamKeyGenerator.class)
    public SimpleParamKeyGenerator autumnSimpleParamKeyGenerator() {
        return new SimpleParamKeyGenerator();
    }

    /**
     * 方法与参数键生成器
     *
     * @return
     */
    @Bean(MethodParamKeyGenerator.BEAN_NAME)
    @Primary
    @ConditionalOnMissingBean(MethodParamKeyGenerator.class)
    public MethodParamKeyGenerator autumnMethodParamKeyGenerator() {
        return new MethodParamKeyGenerator();
    }

    /**
     * 目标类型与方法与参数键生成器
     *
     * @return
     */
    @Bean(TargetTypeMethodParamKeyGenerator.BEAN_NAME)
    @Primary
    @ConditionalOnMissingBean(TargetTypeMethodParamKeyGenerator.class)
    public TargetTypeMethodParamKeyGenerator autumnTargetTypeMethodParamKeyGenerator() {
        return new TargetTypeMethodParamKeyGenerator();
    }

    /**
     * 异常过滤上下文
     *
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(ExceptionFilterContext.class)
    public ExceptionFilterContext autumnExceptionFilterContext() {
        return new DefaultExceptionFilterContext();
    }

    /**
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnExceptionFilter.class)
    public AutumnExceptionFilter autumnExceptionFilter() {
        return new AutumnExceptionFilter();
    }

    /**
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SQLExceptionFilter.class)
    public SQLExceptionFilter autumnSqlExceptionFilter() {
        return new SQLExceptionFilter();
    }

    /**
     * 验证绑定异常过滤
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ValidationBindExceptionFilter.class)
    public ValidationBindExceptionFilter autumnValidationBindExceptionFilter() {
        return new ValidationBindExceptionFilter();
    }

    /**
     * 空指针异常过滤
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(NullPointerExceptionFilter.class)
    public NullPointerExceptionFilter autumnNullPointerExceptionFilter() {
        return new NullPointerExceptionFilter();
    }

    /**
     * 默认最后异常过滤
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(DefaultLastExceptionFilter.class)
    public DefaultLastExceptionFilter autumnDefaultLastExceptionFilter() {
        return new DefaultLastExceptionFilter();
    }

    /**
     * 注册输入对象反序列化
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InputObjectDeserializerGenerator.class)
    public InputObjectDeserializerGenerator autumnInputObjectDeserializerGenerator() {
        return new InputObjectDeserializerGenerator();
    }

    /**
     * 插入审计插件
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InsertAuditingInterceptor.class)
    public InsertAuditingInterceptor autumnInsertAuditingInterceptor() {
        return new InsertAuditingInterceptor();
    }

    /**
     * 更新审计插件
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UpdateAuditingInterceptor.class)
    public UpdateAuditingInterceptor autumnUpdateAuditingInterceptor() {
        return new UpdateAuditingInterceptor();
    }

    /**
     * 删除审计插件
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(DeleteAuditingInterceptor.class)
    public DeleteAuditingInterceptor autumnDeleteAuditingInterceptor() {
        return new DeleteAuditingInterceptor();
    }

    /**
     * 查询审计插件
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SelectAuditingInterceptor.class)
    public SelectAuditingInterceptor autumnSelectAuditingInterceptor() {
        return new SelectAuditingInterceptor();
    }

    /**
     * 事件总线
     *
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(EventBus.class)
    public EventBus autumnEventBus() {
        return new EventBusImpl();
    }

    /**
     * 事务事件监听
     *
     * @param autumnEventBus 事件总线
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(TransactionEventListener.class)
    public TransactionEventListener autumnTransactionEventListener(EventBus autumnEventBus) {
        return new TransactionEventListener(autumnEventBus);
    }


    /**
     * 插入实体事件监听
     *
     * @param autumnEventBus 事件总线
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InsertEntityEventListenerInterceptor.class)
    public InsertEntityEventListenerInterceptor autumnInsertEntityEventListenerInterceptor(EventBus autumnEventBus) {
        return new InsertEntityEventListenerInterceptor(autumnEventBus);
    }

    /**
     * 更新实体事件监听
     *
     * @param autumnEventBus 事件总线
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UpdateEntityEventListenerInterceptor.class)
    public UpdateEntityEventListenerInterceptor autumnUpdateEntityEventListenerInterceptor(EventBus autumnEventBus) {
        return new UpdateEntityEventListenerInterceptor(autumnEventBus);
    }

    /**
     * 删除实体事件监听
     *
     * @param autumnEventBus 事件总线
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(DeleteEntityEventListenerInterceptor.class)
    public DeleteEntityEventListenerInterceptor autumnDeleteEntityEventListenerInterceptor(EventBus autumnEventBus) {
        return new DeleteEntityEventListenerInterceptor(autumnEventBus);
    }

    /**
     * 缓数据管理
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(DataCacheManager.class)
    public DataCacheManager autumnDataCacheManager() {
        return new DataCacheManagerImpl();
    }

    /**
     * 拦截
     *
     * @param exceptionFilterContext
     * @param autumnEventBus
     * @param dataCacheManager
     * @return
     */
    @Bean
    public BeanPostProcessor autumnCoreBeanPostProcessor(ExceptionFilterContext exceptionFilterContext, EventBus autumnEventBus, DataCacheManager dataCacheManager) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof ExceptionFilter) {
                    ExceptionFilter filter = (ExceptionFilter) bean;
                    exceptionFilterContext.addFilter(filter);
                }
                if (bean instanceof JsonObjectDeserializerGenerator) {
                    JsonUtils.registerObjectDeserializer((JsonObjectDeserializerGenerator) bean);
                }
                if (bean instanceof ClockProvider) {
                    if (!(bean instanceof LocalClockProvider)) {
                        Clock.setClockProvider((ClockProvider) bean);
                    }
                }
                if (bean instanceof EntityEventListener) {
                    autumnEventBus.registerEntityEventListener((EntityEventListener) bean);
                }
                if (bean instanceof DataCache) {
                    dataCacheManager.registerDataCache((DataCache) bean);
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
