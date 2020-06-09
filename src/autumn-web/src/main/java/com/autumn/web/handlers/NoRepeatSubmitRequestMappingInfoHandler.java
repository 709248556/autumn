package com.autumn.web.handlers;

import com.autumn.web.annotation.NoRepeatSubmit;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 重复提交处理器
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-16 05:00
 **/
public interface NoRepeatSubmitRequestMappingInfoHandler extends RequestMappingInfoHandler {

    /**
     * 获取防重复提交key
     * @param request 请求
     * @return
     */
    String getRepeatSubmitUrlKey(HttpServletRequest request);

    /**
     * 获取是否重复提交
     *
     * @param request 请求
     * @return
     */
    boolean isRepeatSubmitUrl(ServletRequest request);

    /**
     * 获取重复提交注解
     *
     * @param request 请求
     * @return
     */
    NoRepeatSubmit getNoRepeatSubmit(ServletRequest request);

    /**
     * 获取请求内容
     *
     * @param request 请求
     * @return
     */
    BodyReaderHttpServletRequestWrapper getBodyReaderHttpServletRequest(ServletRequest request);
}
