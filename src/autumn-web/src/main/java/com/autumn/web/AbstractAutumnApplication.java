package com.autumn.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.autumn.audited.ClientInfoProvider;
import com.autumn.spring.boot.properties.AutumnSecurityProperties;
import com.autumn.spring.boot.properties.AutumnWebProperties;
import com.autumn.swagger.annotation.AutumnSwaggerScan;
import com.autumn.util.json.JsonUtils;
import com.autumn.util.json.ToStringSerializer;
import com.autumn.web.annotation.ApiResponseBodyScan;
import com.autumn.web.auditing.WebClientInfoProvider;
import com.autumn.web.controllers.SecurityController;
import com.autumn.web.exception.filter.WebExceptionFilter;
import com.autumn.web.handlers.UrlRequestMappingInfoHandler;
import com.autumn.web.handlers.impl.UrlRequestMappingInfoHandlerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Web AutumnApplication
 *
 * @author 老码农
 * <p>
 * 2017-11-22 17:49:57
 */
@Configuration
@Order(100)
@ApiResponseBodyScan({SecurityController.SECURITY_PACKAGE_PATH})
@AutumnSwaggerScan(groupName = "系统", order = 1000, packages = {SecurityController.SECURITY_PACKAGE_PATH})
@EnableConfigurationProperties({AutumnWebProperties.class, AutumnSecurityProperties.class})
public abstract class AbstractAutumnApplication implements WebMvcConfigurer {

    /**
     * 日志
     */
    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     *
     */
    public AbstractAutumnApplication() {
        logger.info("正在初始化 " + this.getClass());
        JsonUtils.initialize();
        if (this.isJsonCastLongToString()) {
            JsonUtils.registerSerialize(Long.class, ToStringSerializer.INSTANCE);
            JsonUtils.registerSerialize(Long.TYPE, ToStringSerializer.INSTANCE);
        }
        JSON.DEFFAULT_DATE_FORMAT = this.getJsonDateDefaultFormat();
    }

    /**
     * 运行 Spring Boot
     *
     * @param applicationClass 应用类型
     * @param args             参数
     */
    public static void run(Class<? extends AbstractAutumnApplication> applicationClass, String[] args) {
        run(applicationClass, Banner.Mode.OFF, args);
    }

    /**
     * 运行 Spring Boot
     *
     * @param applicationClass 应用类型
     * @param mode             模式
     * @param args             参数集合
     */
    public static void run(Class<? extends AbstractAutumnApplication> applicationClass, Banner.Mode mode,
                           String[] args) {
        if (mode == null) {
            mode = Banner.Mode.CONSOLE;
        }
        SpringApplicationBuilder app = new SpringApplicationBuilder(applicationClass);
        app.bannerMode(mode).run(args);
    }

    /**
     * 获取日期默认格式
     *
     * @return
     */
    public String getJsonDateDefaultFormat() {
        return JsonUtils.JSON_DEFAULT_FORMAT;
    }

    @Autowired
    private AutumnWebProperties webConfigurationProperties;

    /**
     * 获取 Web 配置属性
     *
     * @return
     */
    public final AutumnWebProperties getWebConfigurationProperties() {
        return webConfigurationProperties;
    }

    /**
     * 是否是发布
     *
     * @return
     */
    public boolean isRelease() {
        return webConfigurationProperties != null && webConfigurationProperties.isRelease();
    }

    /**
     * json 转换时，是否将Long 转为 String 类型
     *
     * @return
     */
    protected boolean isJsonCastLongToString() {
        return false;
    }

    /**
     * 获取序列化格式
     *
     * @return
     */
    public SerializerFeature[] getSerializerFeatures() {
        if (isRelease()) {
            return JsonUtils.SERIALIZER_FEATURE_STANDARD;
        }
        return JsonUtils.SERIALIZER_FEATURE_PRETTYFORMAT;
    }

    /**
     * 客户端信息驱动
     *
     * @return
     */
    @Bean
    @Primary
    public ClientInfoProvider clientInfoProvider() {
        return new WebClientInfoProvider();
    }

    /**
     * 安全控制器
     *
     * @param properties 属性
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SecurityController.class)
    public SecurityController securityController(AutumnSecurityProperties properties) {
        return new SecurityController(properties);
    }

    /**
     * Url 请求信息处理器
     *
     * @return
     */
    @Bean
    @Primary
    public UrlRequestMappingInfoHandler urlRequestMappingInfoHandler() {
        return new UrlRequestMappingInfoHandlerImpl();
    }

    /**
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(WebExceptionFilter.class)
    public WebExceptionFilter webExceptionFilter() {
        return new WebExceptionFilter();
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * 跨域
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    @Primary
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    @Bean
    @Primary
    public HttpMessageConverter<Object> fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(getSerializerFeatures());
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(mediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(this.stringHttpMessageConverter());
        converters.add(this.fastJsonHttpMessageConverter());
    }

    /**
     * 不区分大小写
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}
