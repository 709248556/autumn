package com.autumn.zero.common.library.web.controllers.admin.common;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.application.dto.input.DefaultAdvancedPageQueryInput;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.web.utils.ControllerUtils;
import com.autumn.zero.common.library.application.dto.common.dictionary.CommonDataDictionaryAdvancedQueryInput;
import com.autumn.zero.common.library.application.dto.common.region.RegionInput;
import com.autumn.zero.common.library.application.dto.common.region.RegionOutput;
import com.autumn.zero.common.library.application.dto.input.CommonStatusInput;
import com.autumn.zero.common.library.application.dto.tree.input.DefaultChildrenPinyinSortQueryInput;
import com.autumn.zero.common.library.application.services.common.RegionAppService;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 行政区管理控制器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 18:18
 **/
@RestController
@RequestMapping("/common/region")
@Api(tags = "行政区管理")
@RequiresUser
public class AdminRegionController {

    private final RegionAppService service;

    /**
     * 实例化
     *
     * @param service
     */
    public AdminRegionController(RegionAppService service) {
        this.service = service;
    }

    /**
     * 添加
     *
     * @param input
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加")
    public RegionOutput add(@RequestBody RegionInput input) {
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
    public RegionOutput update(@RequestBody RegionInput input) {
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
     * 根据id查询单条数据
     *
     * @param input
     * @return
     */
    @PostMapping("/query/single")
    @ApiOperation(value = "根据id查询单条数据")
    public RegionOutput queryById(@Valid @RequestBody DefaultEntityDto input) {
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
    public PageResult<RegionOutput> queryForPage(@RequestBody DefaultAdvancedPageQueryInput input) {
        return service.queryForPage(input);
    }

    /**
     * 查询子级
     *
     * @param input
     * @return
     */
    @PostMapping("/query/children")
    @ApiOperation(value = "查询子级")
    public List<RegionOutput> queryChildren(@Valid @RequestBody DefaultChildrenPinyinSortQueryInput input) {
        return service.queryChildren(input);
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

    /**
     * 下载Excel导入模板文件
     *
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/download/import/template/body")
    @ApiOperation(value = "下载Excel导入模板文件到响应体", produces = "application/octet-stream")
    @IgnoreApiResponseBody
    public StreamingResponseBody downloadImportTemplateByExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        return ControllerUtils.createExcelImportTemplateBody(this.service, response);
    }

    /**
     * 下载到Excel导入模板文件信息
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/download/import/template/Info")
    @ApiOperation(value = "下载到Excel导入模板文件信息")
    public TemporaryFileInformationDto downloadImportTemplateByExcelInfo() {
        return this.service.excelImportTemplateForFileInformation();
    }

    /**
     * Excel上传并导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload/import/excel")
    @ApiOperation(value = "Excel上传并导入")
    public int dataImportByExcel(@Valid @RequestParam("file") MultipartFile file) throws IOException {
        return this.service.excelImport(file.getInputStream(), null);
    }
}
