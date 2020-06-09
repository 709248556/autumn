package com.autumn.web.handlers;

import com.autumn.web.vo.UrlRequestMethodMappingInfo;

import java.util.List;

/**
 * Url请求信息处理器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2019-12-31 19:28
 **/
public interface UrlRequestMappingInfoHandler extends RequestMappingInfoHandler {

    /**
     * 查询 Url 请求信息
     *
     * @return
     */
    List<UrlRequestMethodMappingInfo> queryUrlRequestMappingInfos();
}
