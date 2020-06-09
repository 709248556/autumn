package com.autumn.web.handlers.impl;

import com.autumn.annotation.FriendlyApi;
import com.autumn.util.StringUtils;
import com.autumn.web.handlers.UrlRequestMappingInfoHandler;
import com.autumn.web.vo.UrlRequestMethodMappingInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import springfox.documentation.swagger.web.ApiResourceController;

import java.util.*;

/**
 * Url请求信息处理器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2019-12-31 19:30
 **/
public class UrlRequestMappingInfoHandlerImpl implements UrlRequestMappingInfoHandler, DisposableBean {

    private final static Map<String, String> CONTROLLER_EXPLAIN_MAP = new HashMap<>(16);
    private final static Map<String, String> URL_OPERATION_MAP = new HashMap<>(32);

    static {
        CONTROLLER_EXPLAIN_MAP.put(ApiResourceController.class.getName(), "swagger Api 处理器");
        URL_OPERATION_MAP.put("/swagger-resources/configuration/security", "swagger 安全配置");
        URL_OPERATION_MAP.put("/swagger-resources/configuration/ui", "swagger UI展示");
        URL_OPERATION_MAP.put("/swagger-resources", "swagger 资源访问");

        CONTROLLER_EXPLAIN_MAP.put("org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping", "spring 键康处理器");
        URL_OPERATION_MAP.put("/actuator", "返回各端点访问信息");
        URL_OPERATION_MAP.put("/actuator/health", "应用健康状态");
        URL_OPERATION_MAP.put("/actuator/info", "应用基本信息");
        URL_OPERATION_MAP.put("/actuator/auditevents", "应用暴露的审计事件");
        URL_OPERATION_MAP.put("/actuator/metrics", "应用多样的度量信息");
        URL_OPERATION_MAP.put("/actuator/loggers", "应用的日志配置");
        URL_OPERATION_MAP.put("/actuator/logfile", "应用的日志文件");
        URL_OPERATION_MAP.put("/actuator/httptrace", "应用HTTP跟踪");
        URL_OPERATION_MAP.put("/actuator/env", "应用当前的环境");
        URL_OPERATION_MAP.put("/actuator/flyway", "数据库迁移路径的详细信息");
        URL_OPERATION_MAP.put("/actuator/liquidbase", "Liquibase 数据库迁移的详细信息");
        URL_OPERATION_MAP.put("/actuator/shutdown", "关闭服务");
        URL_OPERATION_MAP.put("/actuator/mappings", "所有Url地址信息");
        URL_OPERATION_MAP.put("/actuator/scheduledtasks", "调度任务");
        URL_OPERATION_MAP.put("/actuator/threaddump", "执行一个线程dump");
        URL_OPERATION_MAP.put("/actuator/heapdump", "返回一个GZip压缩的JVM堆dump");
    }

    private List<UrlRequestMethodMappingInfo> urlRequestMappingInfos = new ArrayList<>(500);

    @Override
    public List<UrlRequestMethodMappingInfo> queryUrlRequestMappingInfos() {
        return Collections.unmodifiableList(this.urlRequestMappingInfos);
    }

    @Override
    public void mappingHandler(RequestMappingInfo mappingInfo, HandlerMethod handlerMethod) {
        UrlRequestMethodMappingInfo apiUrlInfo = new UrlRequestMethodMappingInfo();
        Set<String> patterns = mappingInfo.getPatternsCondition().getPatterns();
        Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
        for (String url : patterns) {
            apiUrlInfo.setUrl(url);
            if (methods.size() > 0) {
                Set<String> methodSet = new LinkedHashSet<>(methods.size());
                for (RequestMethod method : methods) {
                    methodSet.add(method.toString().toUpperCase());
                }
                apiUrlInfo.setMethods("[" + String.join(",", methodSet) + "]");
            } else {
                apiUrlInfo.setMethods("[ALL]");
            }
        }
        FriendlyApi friendlyApi = handlerMethod.getMethod().getAnnotation(FriendlyApi.class);
        if (friendlyApi != null) {
            apiUrlInfo.setOperation(friendlyApi.value());
            apiUrlInfo.setExplain(friendlyApi.explain());
        } else {
            ApiOperation apiOperation = handlerMethod.getMethod().getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                apiUrlInfo.setOperation(apiOperation.value());
                if (apiOperation.tags() != null) {
                    apiUrlInfo.setExplain(String.join(",", apiOperation.tags()));
                }
            }
        }
        if (StringUtils.isNullOrBlank(apiUrlInfo.getOperation()) && apiUrlInfo.getUrl() != null) {
            String mapUrlExplain = URL_OPERATION_MAP.get(apiUrlInfo.getUrl().toLowerCase());
            if (mapUrlExplain != null) {
                apiUrlInfo.setOperation(mapUrlExplain);
            }
        }
        Class<?> beanType = handlerMethod.getBeanType();
        apiUrlInfo.setControllerName(beanType.getSimpleName());
        friendlyApi = beanType.getAnnotation(FriendlyApi.class);
        if (friendlyApi != null) {
            apiUrlInfo.setControllerExplain(friendlyApi.value());
        } else {
            Api api = beanType.getAnnotation(Api.class);
            if (api != null) {
                if (api.tags() != null) {
                    apiUrlInfo.setControllerExplain(String.join(",", api.tags()));
                } else if (api.value() != null) {
                    apiUrlInfo.setControllerExplain(api.value());
                }
            }
        }
        if (StringUtils.isNullOrBlank(apiUrlInfo.getControllerExplain())) {
            String mapControllerExplain = CONTROLLER_EXPLAIN_MAP.get(beanType.getName());
            if (mapControllerExplain != null) {
                apiUrlInfo.setControllerExplain(mapControllerExplain);
            }
        }
        apiUrlInfo.setMethod(handlerMethod.getMethod());
        this.urlRequestMappingInfos.add(apiUrlInfo);
    }

    @Override
    public void destroy() throws Exception {
        this.urlRequestMappingInfos.clear();
    }
}
