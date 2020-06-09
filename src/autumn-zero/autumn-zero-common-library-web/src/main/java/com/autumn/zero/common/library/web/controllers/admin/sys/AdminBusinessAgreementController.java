package com.autumn.zero.common.library.web.controllers.admin.sys;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.application.dto.input.DefaultAdvancedPageQueryInput;
import com.autumn.application.dto.input.DefaultAdvancedQueryInput;
import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.web.utils.ControllerUtils;
import com.autumn.zero.common.library.application.dto.input.CommonStatusInput;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementDetailsOutput;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementInput;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementListOutput;
import com.autumn.zero.common.library.application.services.sys.BusinessAgreementAppService;
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
import java.util.Collection;

/**
 * 协议管理端
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 23:47
 **/
@RestController
@RequestMapping("/sys/agreement")
@Api(tags = "用户协议管理")
@RequiresUser
public class AdminBusinessAgreementController {

    private final BusinessAgreementAppService service;

    public AdminBusinessAgreementController(BusinessAgreementAppService service) {
        this.service = service;
    }

    /**
     * 查询类型协议列表
     *
     * @return
     */
    @PostMapping("/types")
    @ApiOperation(value = "查询类型协议列表")
    public Collection<IntegerConstantItemValue> agreementTypeList() {
        return service.agreementTypeList();
    }

    /**
     * 添加
     *
     * @param input
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加")
    public BusinessAgreementDetailsOutput add(@RequestBody BusinessAgreementInput input) {
        return service.add(input);
    }

    /**
     * 修改
     *
     * @param input
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public BusinessAgreementDetailsOutput update(@RequestBody BusinessAgreementInput input) {
        return service.update(input);
    }

    /**
     * 根据id删除
     *
     * @param input
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation(value = "根据id删除")
    public void deleteById(@Valid @RequestBody DefaultEntityDto input) {
        service.deleteById(input.getId());
    }

    /**
     * 更新状态
     *
     * @param input
     * @return
     */
    @PostMapping("/update/status")
    @ApiOperation(value = "更新状态")
    public void updateStatus(@Valid @RequestBody CommonStatusInput input) {
        service.updateStatus(input);
    }

    /**
     * 根据id查询单条数据
     *
     * @param input
     * @return
     */
    @PostMapping("/query/single")
    @ApiOperation(value = "根据id查询单条数据")
    public BusinessAgreementDetailsOutput queryById(@Valid @RequestBody DefaultEntityDto input) {
        return service.queryById(input.getId());
    }

    /**
     * 分页查询
     *
     * @param input
     * @return
     */
    @PostMapping("/query/page")
    @ApiOperation(value = "分页查询")
    public PageResult<BusinessAgreementListOutput> queryForPage(@RequestBody DefaultAdvancedPageQueryInput input) {
        return service.queryForPage(input);
    }

    /**
     * 下载Excel到响应体
     *
     * @param input
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/download/excel/body")
    @ApiOperation(value = "下载Excel到响应体", produces = "application/octet-stream")
    @IgnoreApiResponseBody
    public StreamingResponseBody downloadForExcel(@RequestBody DefaultAdvancedQueryInput input, HttpServletResponse response) throws UnsupportedEncodingException {
        return ControllerUtils.createExcelExportBody(this.service, input, response);
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
    public TemporaryFileInformationDto downloadForExcelInfo(@RequestBody DefaultAdvancedQueryInput input) {
        return this.service.exportFileByExcel(input);
    }
}
