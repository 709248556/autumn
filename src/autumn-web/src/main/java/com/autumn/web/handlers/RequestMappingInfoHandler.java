package com.autumn.web.handlers;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

/**
 * 请求信息处理器
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-16 04:37
 **/
public interface RequestMappingInfoHandler {

    /**
     * 映射处理
     *
     * @param mappingInfo   映射信息
     * @param handlerMethod 处理方法
     */
    void mappingHandler(RequestMappingInfo mappingInfo, HandlerMethod handlerMethod);

}
