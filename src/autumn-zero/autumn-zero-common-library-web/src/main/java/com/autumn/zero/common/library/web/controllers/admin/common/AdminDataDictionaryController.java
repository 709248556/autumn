package com.autumn.zero.common.library.web.controllers.admin.common;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.web.utils.ControllerUtils;
import com.autumn.zero.common.library.application.dto.common.dictionary.*;
import com.autumn.zero.common.library.application.dto.input.CommonStatusInput;
import com.autumn.zero.common.library.application.services.common.CommonDataDictionaryAppService;
import com.autumn.zero.file.storage.FileStorageUtils;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

/**
 * 数据字典管理控制器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 15:26
 **/
@RestController
@RequestMapping("/common/dictionary")
@Api(tags = "数据字典管理")
@RequiresUser
public class AdminDataDictionaryController {

    private final CommonDataDictionaryAppService service;

    /**
     * 实例化
     *
     * @param service
     */
    public AdminDataDictionaryController(CommonDataDictionaryAppService service) {
        this.service = service;
    }

    /**
     * 字典类型集合
     *
     * @return
     */
    @PostMapping("/types")
    @ApiOperation(value = "字典类型集合")
    public Collection<IntegerConstantItemValue> dictionaryTypes() {
        return service.dictionaryTypeList();
    }

    /**
     * 添加字典
     *
     * @param input
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加字典")
    public CommonDataDictionaryDetailsOutput add(@RequestBody CommonDataDictionaryInput input) {
        return service.add(input);
    }

    /**
     * 更新
     *
     * @param input
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新")
    public CommonDataDictionaryDetailsOutput update(@RequestBody CommonDataDictionaryInput input) {
        return service.update(input);
    }

    /**
     * 删除
     *
     * @param input
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除")
    public void delete(@Valid @RequestBody DefaultEntityDto input) {
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
    public void updateStatus(@RequestBody CommonStatusInput input) {
        service.updateStatus(input);
    }

    /**
     * 上传附件
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload/file")
    @ApiOperation(value = "上传附件")
    public FileAttachmentInformationResponse uploadFile(@RequestParam("file") MultipartFile file)
            throws Exception {
        return FileStorageUtils.saveUploadFile(this.service, file);
    }

    /**
     * 根据id查询单条数据
     *
     * @param input
     * @return
     */
    @PostMapping("/query/single")
    @ApiOperation(value = "根据id查询单条数据")
    public CommonDataDictionaryDetailsOutput queryById(@Valid @RequestBody DefaultEntityDto input) {
        return service.queryById(input.getId());
    }

    /**
     * 列表查询
     *
     * @param input
     * @return
     */
    @PostMapping("/query/list")
    @ApiOperation(value = "列表查询")
    public List<CommonDataDictionaryOutput> queryForList(@RequestBody CommonDataDictionaryAdvancedQueryInput input) {
        return service.queryForList(input);
    }

    /**
     * 选择列表
     *
     * @param input
     * @return
     */
    @PostMapping("/query/list/choose")
    @ApiOperation(value = "选择列表")
    public List<CommonDataDictionaryOutput> chooseForList(@RequestBody DictionaryTypeInput input) {
        return service.chooseForList(input.getDictionaryType());
    }

    /**
     * 分页查询
     *
     * @param input
     * @return
     */
    @PostMapping("/query/page")
    @ApiOperation(value = "分页查询")
    public PageResult<CommonDataDictionaryOutput> queryForPage(@RequestBody CommonDataDictionaryAdvancedPageQueryInput input) {
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
    public StreamingResponseBody downloadForExcel(@RequestBody CommonDataDictionaryAdvancedQueryInput input, HttpServletResponse response) throws UnsupportedEncodingException {
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
    public TemporaryFileInformationDto downloadForExcelInfo(@RequestBody CommonDataDictionaryAdvancedQueryInput input) {
        return this.service.exportFileByExcel(input);
    }

}
