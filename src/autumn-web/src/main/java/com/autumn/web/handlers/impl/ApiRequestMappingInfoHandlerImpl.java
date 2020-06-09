package com.autumn.web.handlers.impl;

import com.autumn.spring.boot.context.AutumnContextEnvironment;
import com.autumn.util.PackageUtils;
import com.autumn.util.UrlUtils;
import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.web.handlers.ApiRequestMappingInfoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Api请求处理器实现
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-16 06:10
 **/
public class ApiRequestMappingInfoHandlerImpl implements ApiRequestMappingInfoHandler {
    private final static String SWAGGER_BEGIN = "springfox.";

    protected final Set<String> apiControllerPackageSet;

    /**
     * value == true 时，表示忽略
     */
    private Map<String, Boolean> apiUrlMap = new ConcurrentHashMap<>(500);
    /**
     * value == true 时，表示忽略
     */
    private Map<Method, Boolean> apiMethodMap = new ConcurrentHashMap<>(500);

    /**
     * 服务器属性
     */
    @Autowired
    protected ServerProperties serverProperties;

    /**
     * 实例化
     *
     * @param apiControllerPackages api控制器包集合
     */
    public ApiRequestMappingInfoHandlerImpl(String[] apiControllerPackages) {
        String[] pcks = PackageUtils.toPackages(apiControllerPackages);
        this.apiControllerPackageSet = new HashSet<>(pcks.length);
        for (String pck : pcks) {
            this.apiControllerPackageSet.add(pck);
        }
    }

    @Override
    public void mappingHandler(RequestMappingInfo mappingInfo, HandlerMethod handlerMethod) {
        if (this.isContainPackage(handlerMethod)) {
            boolean imgore = isIgnore(handlerMethod);
            apiMethodMap.put(handlerMethod.getMethod(), imgore);
            Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
            Set<String> patterns = mappingInfo.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                String accessUrl = UrlUtils.getLowerCaseRequestUrl(this.serverProperties.getServlet().getContextPath(), url);
                for (RequestMethod method : methods) {
                    apiUrlMap.put(accessUrl + ":" + method.toString().toLowerCase(), imgore);
                }
            }
        }
    }

    /**
     * 是否包含特定包
     *
     * @param handlerMethod 函数
     * @return
     */
    private boolean isContainPackage(HandlerMethod handlerMethod) {
        if (this.apiControllerPackageSet.size() == 0) {
            return true;
        }
        String name = handlerMethod.getBeanType().getPackage().getName();
        if (this.apiControllerPackageSet.contains(name)) {
            return true;
        }
        for (String pckName : this.apiControllerPackageSet) {
            if (name.startsWith(pckName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否忽略
     *
     * @param handlerMethod 函数处理器
     * @return
     */
    private boolean isIgnore(HandlerMethod handlerMethod) {
        IgnoreApiResponseBody ignoreBody = handlerMethod.getMethod().getAnnotation(IgnoreApiResponseBody.class);
        if (ignoreBody != null) {
            return true;
        }
        if (AutumnContextEnvironment.INSTANCE.isEnableSwagger()) {
            String packageName = handlerMethod.getMethod().getDeclaringClass().getPackage().getName();
            if (packageName.startsWith(SWAGGER_BEGIN)) {
                return true;
            }
        }
        Class<?> type = handlerMethod.getMethod().getDeclaringClass();
        do {
            ignoreBody = type.getAnnotation(IgnoreApiResponseBody.class);
            if (ignoreBody != null) {
                return true;
            }
            type = type.getSuperclass();
        } while (!type.equals(Object.class));
        return false;
    }

    @Override
    public boolean isApiRequest(HttpServletRequest request) {
        String url = UrlUtils.getLowerCaseRequestUrl(request.getRequestURI());
        Boolean value = apiUrlMap.get(url + ":" + request.getMethod().toLowerCase());
        return value != null && !value;
    }

    @Override
    public boolean isApiMethod(Method method) {
        Boolean value = apiMethodMap.get(method);
        return value != null && !value;
    }
}
