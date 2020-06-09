package com.autumn.web.handlers;

import com.autumn.annotation.FriendlyApi;
import com.autumn.web.vo.AbstractApiResponse;
import com.autumn.web.vo.ApiObjectResponse;
import com.autumn.web.vo.ErrorInfoResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Aip 响应体处理器
 *
 * @author 老码农
 * <p>
 * 2018-01-14 21:24:50
 */
@Controller
@ControllerAdvice
@FriendlyApi(value = "Api 响应处理器", explain = "处理统一Api响应格式")
@ResponseBody
public class ApiResponseBodyHandler extends AbstractErrorController implements ResponseBodyAdvice<Object> {

    private final static String SWAGGER_BEGIN = "springfox.";

    /**
     * api 根 url
     */
    public final static String ERROR_RUL = "/api/error";

    /**
     * 日志
     */
    protected final Log logger = LogFactory.getLog(this.getClass());

    protected final ErrorInfoResultHandler errorInfoResultHandler;
    protected final HttpStatus[] errorHttpStatusArray;
    protected final ApiRequestMappingInfoHandler apiRequestMappingInfoHandler;


    /**
     * 实例化
     *
     * @param errorInfoResultHandler
     * @param apiRequestMappingInfoHandler
     * @param errorHttpStatusArray
     */
    public ApiResponseBodyHandler(ErrorInfoResultHandler errorInfoResultHandler,
                                  ApiRequestMappingInfoHandler apiRequestMappingInfoHandler,
                                  HttpStatus[] errorHttpStatusArray) {
        super(new DefaultErrorAttributes());
        this.errorInfoResultHandler = errorInfoResultHandler;
        this.apiRequestMappingInfoHandler = apiRequestMappingInfoHandler;
        this.errorHttpStatusArray = errorHttpStatusArray;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return this.apiRequestMappingInfoHandler.isApiMethod(methodParameter.getMethod());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof AbstractApiResponse) {
            return body;
        }
        return ApiObjectResponse.success(body);
    }

    @SuppressWarnings("unused")
    private boolean isJsonRequest(HttpServletRequest request) {
        // 暂时不用，无法取得 Content-Type
        String value = request.getHeader("Content-Type");
        return value != null && value.toLowerCase().contains("application/json");
    }

    /**
     * 获取错误方法
     *
     * @param handler 处理对象
     * @return
     */
    private Method getErrorMethod(HandlerMethod handler) {
        if (handler != null) {
            return handler.getMethod();
        }
        return null;
    }

    /**
     * 异常处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理
     * @param e        异常
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public Object exceptionResponse(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Throwable e) {
        ErrorInfoResult result = this.errorInfoResultHandler.getErrorInfo(request, e);
        if (result.isWriteLog()) {
            logger.error(e.getMessage(), e);
        }
        Method method = this.getErrorMethod(handler);
        boolean isApiMethod = method != null && this.apiRequestMappingInfoHandler.isApiMethod(method);
        if (isApiMethod || this.apiRequestMappingInfoHandler.isApiRequest(request)) {
            return ApiObjectResponse.error(result.getErrorInfo().getCode(), result.getErrorInfo().getMessage(),
                    result.getErrorInfo().getDetails());
        }
        HttpStatus status = this.getStatus(request);
        response.setStatus(status.value());
        Map<String, Object> body = this.getErrorAttributes(request, false);
        body.put("message", e.getMessage());
        return body;
    }

    /**
     * 获取错误地址
     *
     * @param request
     * @return 2017-12-07 11:18:13
     */
    @FriendlyApi(value = "错误处理器", explain = "所有通用错误处理器")
    @GetMapping(ERROR_RUL)
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> body = this.getErrorAttributes(request, false);
        HttpStatus status = getStatus(request);
        if (!this.apiRequestMappingInfoHandler.isApiRequest(request)) {
            response.setStatus(status.value());
            return body;
        }
        String message = "未知异常";
        String details = "";
        if (body != null) {
            Object value;
            value = body.get("message");
            if (value != null) {
                message = value.toString();
            }
            value = body.get("path");
            if (value != null) {
                details = value.toString();
            }
        }
        return ApiObjectResponse.error(status.value(), message, details);
    }

    @Override
    public String getErrorPath() {
        return ERROR_RUL;
    }

    /**
     * 重定向
     *
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> autumnWebServerFactoryCustomizer() {
        final HttpStatus[] httpStatus = this.errorHttpStatusArray;
        return factory -> {
            if (httpStatus != null) {
                for (HttpStatus status : httpStatus) {
                    ErrorPage errorPage = new ErrorPage(status, ERROR_RUL);
                    factory.addErrorPages(errorPage);
                }
            }
        };
    }
}
