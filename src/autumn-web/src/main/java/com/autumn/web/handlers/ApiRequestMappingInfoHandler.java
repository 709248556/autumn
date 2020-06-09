package com.autumn.web.handlers;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Api请求处理器
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-16 06:09
 **/
public interface ApiRequestMappingInfoHandler extends RequestMappingInfoHandler {

    /**
     * 是否为 Api 请求
     *
     * @param request
     * @return
     */
    boolean isApiRequest(HttpServletRequest request);

    /**
     * 是否为 Api 方法
     *
     * @param method
     * @return
     */
    boolean isApiMethod(Method method);

}
