package com.autumn.swagger.configuration;

import com.autumn.spring.boot.properties.AutumnWebProperties;
import com.autumn.swagger.json.SwaggerJsonSerializer;
import com.autumn.util.json.JsonUtils;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.PropertySourcedRequestMappingHandlerMapping;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.configuration.SwaggerCommonConfiguration;
import springfox.documentation.swagger2.configuration.Swagger2JacksonModule;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Swagger 配置
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-18 23:20:03
 */
@Configuration
@ConditionalOnExpression(AutumnWebProperties.WEB_PUBLISH_TYPE_DEBUG)
@Import({SpringfoxWebMvcConfiguration.class, SwaggerCommonConfiguration.class})
@ComponentScan(basePackages = {"springfox.documentation.swagger.readers.parameter",
        "springfox.documentation.swagger2.mappers"})
public class AutumnSwaggerConfiguration implements WebMvcConfigurer {

    /**
     * 日志
     */
    private final static Log logger = LogFactory.getLog(AutumnSwaggerConfiguration.class);

    public AutumnSwaggerConfiguration() {
        JsonUtils.registerSerialize(Json.class, SwaggerJsonSerializer.INSTANCE);
    }

    @Bean
    @ConditionalOnMissingBean(JacksonModuleRegistrar.class)
    public JacksonModuleRegistrar autumnSwaggerJacksonModuleRegistrar() {
        return new Swagger2JacksonModule();
    }

    @Bean
    public HandlerMapping autumnSwaggerHandlerMapping(Environment environment, DocumentationCache documentationCache,
                                                      ServiceModelToSwagger2Mapper mapper, JsonSerializer jsonSerializer) {
        return new PropertySourcedRequestMappingHandlerMapping(environment,
                new Swagger2Controller(environment, documentationCache, mapper, jsonSerializer));
    }

    @Bean
    public boolean createAutumnSwaggerDockets(ApplicationContext applicationContext, AutumnSwaggerApiInfo autumnSwaggerApiInfo) {
        // logger.info("开始注册 AutumnSwagger 文档...");
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
        for (AutumnSwaggerApiGroupInfo group : autumnSwaggerApiInfo.getGroups()) {
            //   logger.info("注册 AutumnSwagger 文档组[" + group.getGroupName() + "]");
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Docket.class);
            beanDefinitionBuilder.addConstructorArgValue(DocumentationType.SWAGGER_2);
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            beanFactory.registerBeanDefinition(group.getBeanId(), beanDefinition);
            loadDocket(autumnSwaggerApiInfo, group, applicationContext);
        }
        List<String> groups = autumnSwaggerApiInfo.getGroups().stream().map(s -> s.getGroupName()).collect(Collectors.toList());
        logger.info("注册 AutumnSwagger [" + String.join("、", groups) + "]");
        return true;
    }

    /**
     * 创建 Swagger 的 Docket 并注入 Spring 容器中
     *
     * @param api
     * @param group
     * @param context
     * @return
     */
    private void loadDocket(AutumnSwaggerApiInfo api, AutumnSwaggerApiGroupInfo group, ApplicationContext context) {
        Docket docket = context.getBean(group.getBeanId(), Docket.class);
        ApiSelectorBuilder builder = docket.groupName(group.getGroupName()).useDefaultResponseMessages(false)
                .forCodeGeneration(true).apiInfo(createApiInfo(api)).select();
        if (group.getAnnotation() != null) {
            builder = builder.apis(RequestHandlerSelectors.withClassAnnotation(group.getAnnotation()));
        } else if (group.getPackages() != null && group.getPackages().size() > 0) {
            Predicate<RequestHandler> apiRredicate = null;
            for (String pck : group.getPackages()) {
                Predicate<RequestHandler> predicate = RequestHandlerSelectors.basePackage(pck);
                if (apiRredicate == null) {
                    apiRredicate = predicate;
                } else {
                    apiRredicate = Predicates.or(apiRredicate, predicate);
                }
            }
            builder = builder.apis(apiRredicate);
        } else {
            builder = builder.apis(RequestHandlerSelectors.withClassAnnotation(Api.class));
        }
        docket = builder.paths(PathSelectors.any()).build();
        List<Parameter> operationParameters = new ArrayList<>();
        if (api.isEnableAuthorize()) {
            AutumnSwaggerApiHeaderParameterInfo headerParameterInfo = new AutumnSwaggerApiHeaderParameterInfo();
            headerParameterInfo.setDataType("string");
            headerParameterInfo.setDescription("授权信息");
            headerParameterInfo.setName(api.getAuthorizeHeader());
            headerParameterInfo.setRequired(false);
            Parameter parameter = createHeaderParameter(headerParameterInfo);
            operationParameters.add(parameter);
        }
        if (api.getHeaderParameters() != null) {
            for (AutumnSwaggerApiHeaderParameterInfo headerParameterInfo : api.getHeaderParameters()) {
                Parameter parameter = createHeaderParameter(headerParameterInfo);
                operationParameters.add(parameter);
            }
        }
        docket.globalOperationParameters(operationParameters);
    }

    /**
     * 创建 API 作者信息
     *
     * @param autumnApiInfo aip信息
     * @return 作者信息
     */
    private Contact createContactInfo(AutumnSwaggerApiInfo autumnApiInfo) {
        return new Contact(autumnApiInfo.getAuthorName(), autumnApiInfo.getAuthorUrl(), autumnApiInfo.getAuthorEmail());
    }

    /**
     * 创建 api 头参数
     *
     * @param headerParameter 请求头
     * @return
     */
    private Parameter createHeaderParameter(AutumnSwaggerApiHeaderParameterInfo headerParameter) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name(headerParameter.getName()).description(headerParameter.getDescription())
                .modelRef(new ModelRef(headerParameter.getDataType())).parameterType("header")
                .required(headerParameter.isRequired());
        return parameterBuilder.build();
    }

    /**
     * 创建 API 信息
     *
     * @param autumnApiInfo Api 的配置信息
     * @return API 信息
     */
    private ApiInfo createApiInfo(AutumnSwaggerApiInfo autumnApiInfo) {
        return new ApiInfoBuilder().title(autumnApiInfo.getTitle()).description(autumnApiInfo.getDescription())
                .version(autumnApiInfo.getVersion()).contact(createContactInfo(autumnApiInfo))
                .license(autumnApiInfo.getLicense()).licenseUrl(autumnApiInfo.getLicenseUrl()).build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
