package com.autumn.web.handlers.impl;

import com.autumn.web.annotation.NoRepeatSubmit;
import com.autumn.web.handlers.BodyReaderHttpServletRequestWrapper;
import com.autumn.web.handlers.NoRepeatSubmitRequestMappingInfoHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 重复提交处理器实现
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-16 05:03
 **/
public class NoRepeatSubmitRequestMappingInfoHandlerImpl implements NoRepeatSubmitRequestMappingInfoHandler {

    /**
     * 防重复提交方法
     */
    private final Map<String, NoRepeatSubmit> noRepeatSubmitMethodMap = new ConcurrentHashMap<>(16);


    @Override
    public String getRepeatSubmitUrlKey(HttpServletRequest request) {
        return request.getRequestURI().toLowerCase() + ":" + request.getMethod().toLowerCase();
    }

    @Override
    public boolean isRepeatSubmitUrl(ServletRequest request) {
        return this.getNoRepeatSubmit(request) != null;
    }

    @Override
    public NoRepeatSubmit getNoRepeatSubmit(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            return this.noRepeatSubmitMethodMap.get(this.getRepeatSubmitUrlKey((HttpServletRequest) request));
        }
        return null;
    }

    @Override
    public BodyReaderHttpServletRequestWrapper getBodyReaderHttpServletRequest(ServletRequest request) {
        if (request instanceof HttpServletRequestWrapper) {
            HttpServletRequestWrapper requestWrapper = (HttpServletRequestWrapper) request;
            if (requestWrapper instanceof BodyReaderHttpServletRequestWrapper) {
                return (BodyReaderHttpServletRequestWrapper) requestWrapper;
            } else {
                if (requestWrapper.getRequest() instanceof BodyReaderHttpServletRequestWrapper) {
                    return (BodyReaderHttpServletRequestWrapper) requestWrapper.getRequest();
                }
            }
        }
        return null;
    }

    @Override
    public void mappingHandler(RequestMappingInfo mappingInfo, HandlerMethod handlerMethod) {
        NoRepeatSubmit noRepeatSubmit = this.findNoRepeatSubmit(handlerMethod);
        if (noRepeatSubmit != null) {
            Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
            Set<String> patterns = mappingInfo.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                for (RequestMethod mehhod : methods) {
                    noRepeatSubmitMethodMap.put(url.toLowerCase() + ":" + mehhod.toString().toLowerCase(), noRepeatSubmit);
                }
            }
        }
    }

    private NoRepeatSubmit findNoRepeatSubmit(HandlerMethod handlerMethod) {
        NoRepeatSubmit noRepeatSubmit = handlerMethod.getMethod().getAnnotation(NoRepeatSubmit.class);
        if (noRepeatSubmit != null) {
            return noRepeatSubmit;
        }
        Class<?> type = handlerMethod.getMethod().getDeclaringClass();
        do {
            noRepeatSubmit = type.getAnnotation(NoRepeatSubmit.class);
            if (noRepeatSubmit != null) {
                return noRepeatSubmit;
            }
            type = type.getSuperclass();
        } while (!type.equals(Object.class));
        return null;
    }
}
