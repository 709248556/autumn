package com.autumn.zero.authorization.controllers.admin.sys;

import com.autumn.web.handlers.UrlRequestMappingInfoHandler;
import com.autumn.web.vo.UrlRequestMappingGroup;
import com.autumn.web.vo.UrlRequestMethodMappingInfo;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.values.ResourcesModuleUrlRequestPermissionMappingInfoValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后端资源url管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 23:28
 **/
@RestController
@RequestMapping("/sys/res/url")
@Api(tags = "资源url管理")
@RequiresUser
public class AdminResourcesUrlController {

    private final ResourcesService resourcesService;
    private final UrlRequestMappingInfoHandler requestMappingInfoHandler;

    public AdminResourcesUrlController(ResourcesService resourcesService, UrlRequestMappingInfoHandler requestMappingInfoHandler) {
        this.resourcesService = resourcesService;
        this.requestMappingInfoHandler = requestMappingInfoHandler;
    }

    /**
     * 查询Url映射组
     *
     * @return
     */
    @PostMapping("/query/mapping/groups")
    @ApiOperation(value = "查询Url映射组列表")
    public List<UrlRequestMappingGroup<ResourcesModuleUrlRequestPermissionMappingInfoValue>> queryUrlRequestMappingGroups() {
        List<UrlRequestMethodMappingInfo> urlRequestMethodMappingInfos = requestMappingInfoHandler.queryUrlRequestMappingInfos();
        return this.resourcesService.createUrlPermissionRequestMappingGroups(urlRequestMethodMappingInfos);
    }

    /**
     * 查询Url映射信息列表
     *
     * @return
     */
    @PostMapping("/query/mapping/infos")
    @ApiOperation(value = "查询Url映射信息列表")
    public List<ResourcesModuleUrlRequestPermissionMappingInfoValue> queryUrlRequestMappingInfos() {
        List<UrlRequestMethodMappingInfo> urlRequestMethodMappingInfos = requestMappingInfoHandler.queryUrlRequestMappingInfos();
        return this.resourcesService.createUrlPermissionRequestMappingInfos(urlRequestMethodMappingInfos);
    }


}
