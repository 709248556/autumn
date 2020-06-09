package com.autumn.zero.authorization.controllers.admin.sys;

import com.autumn.application.dto.StringEntityDto;
import com.autumn.application.dto.input.DefaultAdvancedPageQueryInput;
import com.autumn.application.dto.input.DefaultAdvancedQueryInput;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.web.utils.ControllerUtils;
import com.autumn.zero.authorization.application.dto.modules.ResourcesModuleTypeDto;
import com.autumn.zero.authorization.application.dto.modules.ResourcesTypeChildrenInput;
import com.autumn.zero.authorization.application.dto.modules.ResourcesTypeInput;
import com.autumn.zero.authorization.application.services.ResourcesModuleAppService;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.values.ResourcesModulePermissionTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleValue;
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
 * 资源模块管理端
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 22:01
 **/
@RestController
@RequestMapping("/sys/res/module")
@Api(tags = "资源模块管理")
@RequiresUser
public class AdminResourcesModuleController {

    private final ResourcesModuleAppService service;
    private final ResourcesService resourcesService;

    public AdminResourcesModuleController(ResourcesModuleAppService service, ResourcesService resourcesService) {
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
    public ResourcesModuleValue add(@RequestBody ResourcesModuleValue input) {
        return service.add(input);
    }

    /**
     * 添加默认系统模块
     *
     * @return
     */
    @PostMapping("/add/default")
    @ApiOperation(value = "添加默认系统模块")
    public boolean addDefaultSystemModule() {
        return service.addDefaultSystemModule();
    }

    /**
     * 修改/更新
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改/更新")
    public ResourcesModuleValue update(@RequestBody ResourcesModuleValue input) {
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
    public void deleteById(@RequestBody StringEntityDto input) {
        ExceptionUtils.checkNotNullOrBlank(input.getId(), "id");
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
    public ResourcesModuleValue queryById(@Valid @RequestBody StringEntityDto input) {
        ExceptionUtils.checkNotNullOrBlank(input.getId(), "id");
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
    public List<ResourcesModuleValue> queryForList(@Valid @RequestBody DefaultAdvancedQueryInput input) {
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
    public PageResult<ResourcesModuleValue> queryByPageList(@RequestBody DefaultAdvancedPageQueryInput input) {
        return service.queryForPage(input);
    }

    /**
     * 查询资源类型列表
     *
     * @return
     */
    @PostMapping("/query/list/type")
    @ApiOperation(value = "查询资源类型列表")
    public List<ResourcesModuleTypeDto> queryResourcesTypeList() {
        return service.queryResourcesTypeList();
    }

    /**
     * 查询模块所有树
     *
     * @return
     */
    @PostMapping("/query/tree/all")
    @ApiOperation(value = "查询模块所有树")
    public List<ResourcesModuleTreeValue> queryByAllTree() {
        return service.queryByTree();
    }

    /**
     * 查询指定资源模块树
     *
     * @return
     */
    @PostMapping("/query/tree")
    @ApiOperation(value = "查询指定资源模块树")
    public List<ResourcesModuleTreeValue> queryByTree(@Valid @RequestBody ResourcesTypeInput input) {
        return service.queryByTree(input.getResourcesType());
    }

    /**
     * 查询指定资源模块树子级
     *
     * @return
     */
    @PostMapping("/query/children")
    @ApiOperation(value = "查询指定资源模块树子级")
    public List<ResourcesModuleValue> queryChildren(@Valid @RequestBody ResourcesTypeChildrenInput input) {
        return resourcesService.queryChildren(input.getResourcesType(), input.getParentId());
    }

    /**
     * 查询指定资源菜单树
     *
     * @return
     */
    @PostMapping("/query/tree/menu")
    @ApiOperation(value = "查询指定资源菜单树")
    public List<ResourcesModuleTreeValue> queryByMenuTree(@Valid @RequestBody ResourcesTypeInput input) {
        return service.queryByMenuTree(input.getResourcesType());
    }

    /**
     * 查询模块所有权限树
     *
     * @return
     */
    @PostMapping("/query/tree/permission/all")
    @ApiOperation(value = "查询模块所有权限树")
    public List<ResourcesModulePermissionTreeValue> queryByAllPermissionTree() {
        return service.queryByPermissionTree();
    }

    /**
     * 查询指定资源权限树
     *
     * @return
     */
    @PostMapping("/query/tree/permission")
    @ApiOperation(value = "查询指定资源权限树")
    public List<ResourcesModulePermissionTreeValue> queryByPermissionTree(@Valid @RequestBody ResourcesTypeInput input) {
        return service.queryByPermissionTree(input.getResourcesType());
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
    public StreamingResponseBody downloadByExcel(@RequestBody DefaultAdvancedQueryInput input, HttpServletResponse response) throws UnsupportedEncodingException {
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
