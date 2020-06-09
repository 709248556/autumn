package com.autumn.security.configure;

import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.redis.builder.JedisConnectionFactoryBuilder;
import com.autumn.runtime.task.TaskExecutorService;
import com.autumn.security.annotation.ExcludeTokenAutoLogin;
import com.autumn.security.credential.AutumnCredentialsMatcher;
import com.autumn.security.credential.AutumnUserCredentialsService;
import com.autumn.security.crypto.AutumnDeviceEncode;
import com.autumn.security.crypto.AutumnMd5PasswordEncode;
import com.autumn.security.crypto.AutumnPasswordEncode;
import com.autumn.security.crypto.DefaultAutumnDeviceEncode;
import com.autumn.security.filter.AutumnAnonymousFilter;
import com.autumn.security.filter.AutumnFilterContext;
import com.autumn.security.filter.AutumnFormAuthenticationFilter;
import com.autumn.security.filter.AutumnPermissionFilter;
import com.autumn.security.filter.exception.AuthenticationExceptionFilter;
import com.autumn.security.filter.impl.AutumnFilterContextImpl;
import com.autumn.security.realm.AutumnAuthorizingRealm;
import com.autumn.security.redis.AutumnShiroRedisManager;
import com.autumn.security.redis.impl.AutumnShiroRedisManagerImpl;
import com.autumn.security.session.*;
import com.autumn.security.session.impl.AutumnMemorySessionDAO;
import com.autumn.security.session.impl.AutumnRedisSessionDAO;
import com.autumn.security.session.impl.SessionListenerProxyImpl;
import com.autumn.security.session.impl.ShiroAutumnSessionManagerImpl;
import com.autumn.security.web.servlet.AutumnCookie;
import com.autumn.security.web.servlet.impl.SimpleAutumnCookie;
import com.autumn.spring.boot.properties.AutumnAuthProperties;
import com.autumn.spring.boot.properties.AutumnSecurityProperties;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 安全配置
 *
 * @author 老码农 2018-12-06 14:01:54
 */
@Configuration
@EnableConfigurationProperties({AutumnSecurityProperties.class, AutumnAuthProperties.class})
public class AutumnSecurityConfiguration {

    /**
     * 代理
     *
     * @param autumnSessionManager 会话管理
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionListenerProxy.class)
    public SessionListenerProxy autumnSessionListenerProxy(TaskExecutorService taskExecutorService) {
        return new SessionListenerProxyImpl(taskExecutorService);
    }

    /**
     * 会话管理
     *
     * @param sessionDAO
     * @return
     */
    @Bean
    @Primary
    public ShiroAutumnSessionManager shiroAutumnSessionManager(AutumnSessionDAO sessionDAO) {
        return new ShiroAutumnSessionManagerImpl(sessionDAO);
    }

    /**
     * 基于 Redis 的授权管理
     *
     * @param authProperties 授权属性
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnShiroRedisManager.class)
    @ConditionalOnProperty(prefix = AutumnAuthProperties.PREFIX, name = "cacheProvider", havingValue = "redis")
    public AutumnShiroRedisManager autumnShiroRedisManager(AutumnAuthProperties authProperties) throws Exception {
        JedisConnectionFactoryBuilder builder = new JedisConnectionFactoryBuilder(authProperties.getRedis());
        RedisConnectionFactory redisConnectionFactory = builder.createRedisConnectionFactory();
        if (redisConnectionFactory instanceof InitializingBean) {
            ((InitializingBean) redisConnectionFactory).afterPropertiesSet();
        }
        AutumnRedisTemplate template = new AutumnRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return new AutumnShiroRedisManagerImpl(template);
    }

    /**
     * 缓存管理
     *
     * @param authProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager shiroCacheManager(AutumnAuthProperties authProperties) throws Exception {
        if (authProperties.isRedisCacheProvider()) {
            RedisCacheManager redisCacheManager = new RedisCacheManager();
            redisCacheManager.setKeyPrefix(authProperties.getCacheKeyPrefix());
            redisCacheManager.setRedisManager(this.autumnShiroRedisManager(authProperties));
            if (authProperties.getExpire() > 0) {
                redisCacheManager.setExpire(authProperties.getExpire());
            } else {
                redisCacheManager.setExpire(-1);
            }
            return redisCacheManager;
        }
        MemoryConstrainedCacheManager memoryCacheManager = new MemoryConstrainedCacheManager();
        return memoryCacheManager;
    }

    /**
     * 会话id生成器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionIdGenerator.class)
    public SessionIdGenerator autumnSessionIdGenerator() {
        return new AutumnSessionIdGenerator();
    }

    /**
     * 会话Dao
     *
     * @param sessionIdGenerator
     * @param authProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnSessionDAO.class)
    public AutumnSessionDAO shiroSessionDAO(SessionIdGenerator sessionIdGenerator, AutumnAuthProperties authProperties) throws Exception {
        if (authProperties.isRedisCacheProvider()) {
            AutumnRedisSessionDAO redisSessionDAO = new AutumnRedisSessionDAO();
            redisSessionDAO.setKeyPrefix(authProperties.getSessionKeyPrefix());
            redisSessionDAO.setRedisManager(this.autumnShiroRedisManager(authProperties));
            if (authProperties.getExpire() > 0) {
                redisSessionDAO.setExpire(authProperties.getExpire());
            } else {
                redisSessionDAO.setExpire(-1);
            }
            redisSessionDAO.setSessionIdGenerator(sessionIdGenerator);
            return redisSessionDAO;
        }
        AutumnMemorySessionDAO memorySessionDAO = new AutumnMemorySessionDAO();
        memorySessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return memorySessionDAO;
    }


    /**
     * 权限异常过滤
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthenticationExceptionFilter.class)
    public AuthenticationExceptionFilter authenticationExceptionFilter() {
        return new AuthenticationExceptionFilter();
    }

    /**
     * 密码(编码)提供者，实现密码比较生成
     *
     * @param authProperties     授权属性
     * @param securityProperties 安全属性
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnPasswordEncode.class)
    public AutumnPasswordEncode autumnMd5PasswordProvider(AutumnAuthProperties authProperties,
                                                          AutumnSecurityProperties securityProperties) {
        return new AutumnMd5PasswordEncode(authProperties, securityProperties);
    }

    /**
     * 设备(编码)提供者，实现设备比较生成
     *
     * @param authProperties
     * @param securityProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnDeviceEncode.class)
    public AutumnDeviceEncode defaultAutumnDeviceProvider(AutumnAuthProperties authProperties,
                                                          AutumnSecurityProperties securityProperties) {
        return new DefaultAutumnDeviceEncode(authProperties, securityProperties);
    }

    /**
     * 授权  Realm
     *
     * @param passwordProvider
     * @param credentialsService
     * @param autumnCredentialsMatcher
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnAuthorizingRealm.class)
    public AutumnAuthorizingRealm autumnAuthorizingRealm(AutumnPasswordEncode passwordProvider,
                                                         AutumnUserCredentialsService credentialsService,
                                                         AutumnCredentialsMatcher autumnCredentialsMatcher) {
        AutumnAuthorizingRealm realm = new AutumnAuthorizingRealm(passwordProvider, credentialsService);
        realm.setCredentialsMatcher(autumnCredentialsMatcher);
        return realm;
    }

    /**
     * 认证比较器
     *
     * @param credentialsService
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnCredentialsMatcher.class)
    public AutumnCredentialsMatcher autumnCredentialsMatcher(AutumnUserCredentialsService credentialsService) {
        return new AutumnCredentialsMatcher(credentialsService);
    }

    /**
     * Cookie 管理
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnCookie.class)
    public AutumnCookie autumnDefaultSessionCookie() {
        //这个参数是cookie的名称
        SimpleAutumnCookie simpleCookie = new SimpleAutumnCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(SimpleCookie.DEFAULT_MAX_AGE);
        return simpleCookie;
    }

    /**
     * 会话管理
     *
     * @param executorService
     * @param sessionDAO
     * @param sessionListenerProxy
     * @param autumnCookie
     * @param autumnAuthProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionManager.class)
    public SessionManager shiroSessionManager(TaskExecutorService executorService,
                                              AutumnSessionDAO sessionDAO,
                                              SessionListenerProxy sessionListenerProxy,
                                              AutumnCookie autumnCookie,
                                              AutumnAuthProperties autumnAuthProperties) {
        AutumnWebSessionManager sessionManager = new AutumnWebSessionManager(executorService);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookie(autumnCookie);

        List<SessionListener> listeners = new ArrayList<>(16);
        listeners.add(sessionListenerProxy);
        sessionManager.setSessionListeners(listeners);
        if (autumnAuthProperties.getExpire() > 0) {
            sessionManager.setGlobalSessionTimeout(autumnAuthProperties.getExpire() * 1000);
        } else {
            sessionManager.setGlobalSessionTimeout(-1L);
        }
        return sessionManager;
    }

    /**
     * 会话管理
     *
     * @param autumnAuthorizingRealm
     * @param cacheManager
     * @param sessionManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(org.apache.shiro.mgt.SecurityManager.class)
    public org.apache.shiro.mgt.SecurityManager securityManager(AutumnAuthorizingRealm autumnAuthorizingRealm,
                                                                CacheManager cacheManager,
                                                                SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 设置realm.
        // securityManager.setAuthenticator(modularRealmAuthenticator());

        List<Realm> realms = new ArrayList<>();
        realms.add(autumnAuthorizingRealm);
        securityManager.setRealms(realms);

        // HashedCredentialsMatcher aa;

        securityManager.setCacheManager(cacheManager);
        securityManager.setSessionManager(sessionManager);
        // securityManager.setSubjectFactory(subjectFactory);

        // 注入记住我管理器;
        // securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * @param securityManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthorizationAttributeSourceAdvisor.class)
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 此注解解决注解授权
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }

    /**
     * 过滤上下文
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnFilterContext.class)
    public AutumnFilterContext autumnFilterContext() {
        return new AutumnFilterContextImpl();
    }

    /**
     * 过滤器工厂
     *
     * @param securityManager
     * @param credentialsService
     * @param excludeContext
     * @param autumnPermissionFilter
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ShiroFilterFactoryBean.class)
    public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager,
                                                         AutumnUserCredentialsService credentialsService,
                                                         AutumnFilterContext filterContext,
                                                         AutumnPermissionFilter autumnPermissionFilter) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = factoryBean.getFilters();
        //设备权限过滤
        filters.put(DefaultFilter.anon.name(), new AutumnAnonymousFilter(credentialsService, filterContext));
        filters.put(DefaultFilter.authc.name(), new AutumnFormAuthenticationFilter(credentialsService, filterContext));

        // filters.put("perms", new AutumnPermissionsAuthorizationFilter());

        autumnPermissionFilter.initFilter(factoryBean, securityManager);
        return factoryBean;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> filterRegistrationBean() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", corsConfiguration);
        CorsFilter corsFilter = new CorsFilter(configurationSource);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(corsFilter);
        bean.setOrder(0);
        return bean;
    }

    /**
     * 拦截
     *
     * @param listenerProxy
     * @param filterContext
     * @return
     */
    @Bean
    public BeanPostProcessor autumnSecurityBeanPostProcessor(SessionListenerProxy listenerProxy,
                                                             AutumnFilterContext filterContext) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof SessionListener && !(bean instanceof SessionListenerProxy)) {
                    SessionListener listener = (SessionListener) bean;
                    listenerProxy.registerListener(listener);
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof RequestMappingInfoHandlerMapping) {
                    RequestMappingInfoHandlerMapping handlerMapping = (RequestMappingInfoHandlerMapping) bean;
                    Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
                    for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
                        ExcludeTokenAutoLogin exclude = entry.getValue().getMethod().getAnnotation(ExcludeTokenAutoLogin.class);
                        if (exclude != null) {
                            Set<String> patterns = entry.getKey().getPatternsCondition().getPatterns();
                            for (String url : patterns) {
                                filterContext.addExcludeTokenLoginPath(url);
                            }
                        }
                    }
                }
                return bean;
            }
        };
    }

}
