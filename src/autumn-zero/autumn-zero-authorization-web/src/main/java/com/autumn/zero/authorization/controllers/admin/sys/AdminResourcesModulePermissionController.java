package com.autumn.zero.authorization.controllers.admin.sys;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.application.dto.input.DefaultAdvancedPageQueryInput;
import com.autumn.application.dto.input.DefaultAdvancedQueryInput;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.web.utils.ControllerUtils;
import com.autumn.zero.authorization.application.dto.modules.ResourcesInput;
import com.autumn.zero.authorization.application.dto.modules.ResourcesModulePermissionDto;
import com.autumn.zero.authorization.application.services.ResourcesModulePermissionAppService;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 资源模块权限管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 22:28
 **/
@RestController
@RequestMapping("/sys/res/permission")
@Api(tags = "资源模块权限管理")
@RequiresUser
public class AdminResourcesModulePermissionController {

    private final ResourcesModulePermissionAppService service;
    private final ResourcesService resourcesService;

    /**
     * 实例化
     *  @param service
     * @param resourcesService
     */
    public AdminResourcesModulePermissionController(ResourcesModulePermissionAppService service, ResourcesService resourcesService) {
        this.service = service;
        this.resourcesService = resourcesService;
    }

    /**
     * 添加
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加")
    public ResourcesModulePermissionDto add(@Valid @RequestBody ResourcesModulePermissionDto input) {
        return service.add(input);
    }

    /**
     * 添加默认查询权限
     *
     * @param input 输入
     */
    @PostMapping("/add/default/query")
    @ApiOperation(value = "添加默认查询权限")
    public void addDefaultQueryPermission(@Valid @RequestBody ResourcesInput input) {
        service.addDefaultQueryPermission(input);
    }

    /**
     * 添加默认编辑权限
     *
     * @param input 输入
     */
    @PostMapping("/add/default/edit")
    @ApiOperation(value = "添加默认编辑权限")
    public void addDefaultEditPermission(@Valid @RequestBody ResourcesInput input) {
        service.addDefaultEditPermission(input);
    }

    /**
     * 修改/更新
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改/更新")
    public ResourcesModulePermissionDto update(@Valid @RequestBody ResourcesModulePermissionDto input) {
        return service.update(input);
    }

    /**
     * 根据id删除
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation(value = "根据id删除")
    public void delete(@RequestBody DefaultEntityDto input) {
        ExceptionUtils.checkNotNull(input.getId(), "id");
        service.deleteById(input.getId());
    }

    /**
     * 根据id获取单条数据
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/query/single")
    @ApiOperation(value = "根据id获取单条数据")
    public ResourcesModulePermissionDto queryById(@Valid @RequestBody DefaultEntityDto input) {
        ExceptionUtils.checkNotNull(input.getId(), "id");
        return service.queryById(input.getId());
    }

    /**
     * 查询列表
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/query/list")
    @ApiOperation(value = "查询列表")
    public List<ResourcesModulePermissionDto> queryForList(@Valid @RequestBody DefaultAdvancedQueryInput input) {
        return service.queryForList(input);
    }

    /**
     * 分页查询
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/query/page")
    @ApiOperation(value = "分页查询")
    public PageResult<ResourcesModulePermissionDto> queryForPage(@Valid @RequestBody DefaultAdvancedPageQueryInput input) {
        return service.queryForPage(input);
    }

    /**
     * 下载Excel到响应体
     *
     * @param input 输入
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/download/excel/body")
    @ApiOperation(value = "下载Excel到响应体", produces = "application/octet-stream")
    @IgnoreApiResponseBody
    public StreamingResponseBody downloadByExcel(@Valid @RequestBody DefaultAdvancedQueryInput input,
                                                 HttpServletResponse response) throws UnsupportedEncodingException {
        return ControllerUtils.createExcelExportBody(service, input, response);
    }

    /**
     * 下载Excel到文件信息
     *
     * @param input 输入
     * @return
     * @throws Exception
     */
    @PostMapping("/download/excel/Info")
    @ApiOperation(value = "下载Excel到文件信息")
    public TemporaryFileInformationDto downloadForExcelInfo(@RequestBody DefaultAdvancedQueryInput input) throws Exception {
        return service.exportFileByExcel(input);
    }

}
