package com.autumn.zero.authorization.controllers.admin.sys;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.application.dto.input.DefaultAdvancedPageQueryInput;
import com.autumn.application.dto.input.DefaultAdvancedQueryInput;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.runtime.session.AutumnSession;
import com.autumn.util.AutoMapUtils;
import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.web.utils.ControllerUtils;
import com.autumn.zero.authorization.application.dto.CallbackPermissionGrantedInput;
import com.autumn.zero.authorization.application.dto.PermissionDto;
import com.autumn.zero.authorization.application.dto.PermissionGrantedInput;
import com.autumn.zero.authorization.application.dto.users.*;
import com.autumn.zero.authorization.application.services.UserAppServiceBase;
import com.autumn.zero.authorization.application.services.callback.AuthCallback;
import com.autumn.zero.authorization.values.ResourcesModulePermissionTreeValue;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 用户管理
 * <p>
 * </p>
 *
 * @param <TAddInput>      添加输入类型
 * @param <TUpdateInput>   更新输入类型
 * @param <TOutputItem>    输出项目类型
 * @param <TOutputDetails> 输出详情类型
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 09:47
 **/
@RestController
@RequestMapping("/sys/user")
@Api(tags = "用户管理")
@RequiresUser
public class AdminUserManagerController<TAddInput extends UserAddInput, TUpdateInput extends UserInput, TOutputItem extends UserOutput, TOutputDetails extends UserDetailsOutput> {

    /**
     * 会话
     */
    @Autowired
    protected AutumnSession session;
    private final AuthCallback authCallback;
    private final UserAppServiceBase<TAddInput, TUpdateInput, TOutputItem, TOutputDetails> service;

    /**
     * 实例化
     * @param service
     * @param authCallback
     */
    public AdminUserManagerController(UserAppServiceBase<TAddInput, TUpdateInput, TOutputItem, TOutputDetails> service, AuthCallback authCallback) {
        this.service = service;
        this.authCallback = authCallback;
    }

    /**
     * 添加
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加")
    public TOutputDetails add(@Valid @RequestBody TAddInput input) {
        return service.add(input);
    }

    /**
     * 修改/更新
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改/更新")
    public TOutputDetails update(@Valid @RequestBody TUpdateInput input) {
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
    public void deleteById(@Valid @RequestBody DefaultEntityDto input) {
        ExceptionUtils.checkNotNull(input.getId(), "id");
        service.deleteById(input.getId());
    }

    /**
     * 重置密码
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/reset/password")
    @ApiOperation(value = "重置密码")
    public void resetPassword(@Valid @RequestBody ResetPasswordInput input) {
        service.resetPassword(input);
    }

    /**
     * 根据id获取单条数据
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/query/single")
    @ApiOperation(value = "根据id获取单条数据")
    public TOutputDetails queryById(@Valid @RequestBody DefaultEntityDto input) {
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
    public List<TOutputItem> queryForList(@Valid @RequestBody DefaultAdvancedQueryInput input) {
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
    public PageResult<TOutputItem> queryForPage(@Valid @RequestBody DefaultAdvancedPageQueryInput input) {
        return service.queryForPage(input);
    }

    /**
     * 用户授权
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/authorize")
    @ApiOperation(value = "用户授权")
    public UserPermissionOutput authorize(@Valid @RequestBody CallbackPermissionGrantedInput input) {
        PermissionGrantedInput grantedInput = AutoMapUtils.map(input, PermissionGrantedInput.class);
        grantedInput.setResourcesType(this.authCallback.moduleResourcesType(this.session));
        return service.authorize(grantedInput);
    }

    /**
     * 用户授权查询
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/query/authorize")
    @ApiOperation(value = "用户授权查询")
    public UserPermissionOutput authorizeByQuery(@Valid @RequestBody DefaultEntityDto input) {
        ExceptionUtils.checkNotNull(input.getId(), "id");
        PermissionDto permissionDto = AutoMapUtils.map(input, PermissionDto.class);
        permissionDto.setResourcesType(this.authCallback.moduleResourcesType(this.session));
        return service.authorizeByQuery(permissionDto);
    }

    /**
     * 用户授权模块权限树
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/query/authorize/tree")
    @ApiOperation(value = "用户授权模块权限树")
    public List<ResourcesModulePermissionTreeValue> authorizeByModulePermissionTree(@Valid @RequestBody DefaultEntityDto input) {
        ExceptionUtils.checkNotNull(input.getId(), "id");
        PermissionDto permissionDto = AutoMapUtils.map(input, PermissionDto.class);
        permissionDto.setResourcesType(this.authCallback.moduleResourcesType(this.session));
        return service.authorizeByModulePermissionTree(permissionDto);
    }

    /**
     * 下载到Excel
     *
     * @param input 输入
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/download/excel/body")
    @ApiOperation(value = "下载Excel到响应体", produces = "application/octet-stream")
    @IgnoreApiResponseBody
    public StreamingResponseBody downloadByExcel(@Valid @RequestBody DefaultAdvancedQueryInput input,
                                                 HttpServletResponse response) throws UnsupportedEncodingException {
        return ControllerUtils.createExcelExportBody(this.service, input, response);
    }

    /**
     * 下载文件信息
     *
     * @param input 输入
     * @return
     * @throws Exception
     */
    @PostMapping("/download/excel/Info")
    @ApiOperation(value = "下载Excel到文件信息")
    public TemporaryFileInformationDto downloadByExcelInfo(@Valid @RequestBody DefaultAdvancedQueryInput input) {
        return this.service.exportFileByExcel(input);
    }

}
