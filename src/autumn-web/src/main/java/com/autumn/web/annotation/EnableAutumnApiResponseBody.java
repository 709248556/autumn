package com.autumn.web.annotation;

import com.autumn.annotation.EnableAutumnCoreAtuoConfiguration;
import com.autumn.web.configure.ApiResponseBodyBeanDefinitionRegister;
import com.autumn.web.configure.AutumnWebAutoConfiguration;
import com.autumn.web.handlers.DefaultErrorInfoResultHandler;
import com.autumn.web.handlers.ErrorInfoResultHandler;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;

import java.lang.annotation.*;

/**
 * 启用响应体按 Api 格式输出
 *
 * @author 老码农
 * <p>
 * 2018-01-15 09:15:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnCoreAtuoConfiguration
@EnableConfigurationProperties({ServerProperties.class})
@Import({ApiResponseBodyBeanDefinitionRegister.class, AutumnWebAutoConfiguration.class})
public @interface EnableAutumnApiResponseBody {

    /**
     * 异常处理结果信息属性
     */
    public final static String ERROR_INFO_RESULT_HANDLER_CLASS_ATTRIBUTE_NAME = "errorInfoResultHandlerClass";

    /**
     * Api 控制器包集合属性
     */
    public final static String API_CONTROLLER_PACKAGES_ATTRIBUTE_NAME = "apiControllerPackages";

    /**
     * 发生异常统一输出的处理状态集合集合属性
     */
    public final static String ERROR_RESPONSE_STATUS_ATTRIBUTE_NAME = "errorResponseStatus";

    /**
     * Api 控制器的包集合,未指定时默认拦截所有 url(除自动识别 url外)
     *
     * @return
     */
    @AliasFor("apiControllerPackages")
    String[] value() default {};

    /**
     * Api 控制器的包集合,未指定时默认拦截所有 url(除自动识别 url外)
     *
     * @return
     */
    @AliasFor("value")
    String[] apiControllerPackages() default {};

    /**
     * 错误信息结果处理器类型
     *
     * @return
     */
    Class<? extends ErrorInfoResultHandler> errorInfoResultHandlerClass() default DefaultErrorInfoResultHandler.class;

    /**
     * 发生异常统一输出的处理状态集合
     * <p>
     * 注：指 apiControllerPackages 包以外发生的异常处理
     * </p>
     *
     * @return
     */
    HttpStatus[] errorResponseStatus() default {HttpStatus.NOT_FOUND};
}
