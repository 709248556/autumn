package com.autumn.zero.authorization.controllers.admin.sys;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.application.dto.input.DefaultAdvancedPageQueryInput;
import com.autumn.application.dto.input.DefaultAdvancedQueryInput;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.web.utils.ControllerUtils;
import com.autumn.zero.authorization.application.dto.UserLoginLogOutput;
import com.autumn.zero.authorization.application.services.UserLoginLogAppService;
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

/**
 * 登录日志管理
 */
@RestController
@RequestMapping("/sys/log/login")
@Api(tags = "登录日志管理")
@RequiresUser
public class AdminUserLoginLogController {

    private final UserLoginLogAppService service;

    public AdminUserLoginLogController(UserLoginLogAppService service) {
        this.service = service;
    }

    /**
     * 全部删除
     */
    @PostMapping("/delete/all")
    @ApiOperation(value = "全部删除")
    public void deleteAll() {
        service.deleteAll();
    }

    /**
     * 根据id获取单条数据
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/query/single")
    @ApiOperation(value = "根据id获取单条数据")
    public UserLoginLogOutput queryById(@Valid @RequestBody DefaultEntityDto input) {
        ExceptionUtils.checkNotNull(input.getId(), "id");
        return service.queryById(input.getId());
    }

    /**
     * 分页查询
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/query/page")
    @ApiOperation(value = "分页查询")
    public PageResult<UserLoginLogOutput> queryForPage(@Valid @RequestBody DefaultAdvancedPageQueryInput input) {
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

