package com.autumn.zero.common.library.web.controllers.admin.sys;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.zero.common.library.application.dto.input.CommonStatusInput;
import com.autumn.zero.common.library.application.dto.sys.help.*;
import com.autumn.zero.common.library.application.dto.tree.input.DefaultChildrenQueryInput;
import com.autumn.zero.common.library.application.services.sys.SystemHelpDocumentAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 帮助文档管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 21:23
 **/
@RestController
@RequestMapping("/sys/help/document")
@Api(tags = "帮助文档管理")
@RequiresUser
public class AdminSystemHelpDocumentController {
    private final SystemHelpDocumentAppService service;

    /**
     * @param service
     */
    public AdminSystemHelpDocumentController(SystemHelpDocumentAppService service) {
        this.service = service;
    }

    /**
     * 添加目录
     *
     * @param input
     * @return
     */
    @PostMapping("/add/directory")
    @ApiOperation(value = "添加目录")
    public SystemHelpDocumentOutput addDirectory(@RequestBody SystemHelpDocumentDirectoryInput input) {
        return service.add(input);
    }

    /**
     * 添加文件
     *
     * @param input
     * @return
     */
    @PostMapping("/add/file")
    @ApiOperation(value = "添加文件")
    public SystemHelpDocumentFileOutput addFile(@RequestBody SystemHelpDocumentFileInput input) {
        return (SystemHelpDocumentFileOutput) service.add(input);
    }

    /**
     * 修改目录
     *
     * @param input
     * @return
     */
    @PostMapping("/update/directory")
    @ApiOperation(value = "修改目录")
    public SystemHelpDocumentOutput updateDirectory(@RequestBody SystemHelpDocumentDirectoryInput input) {
        return service.update(input);
    }

    /**
     * 修改文件
     *
     * @param input
     * @return
     */
    @PostMapping("/update/file")
    @ApiOperation(value = "修改文件")
    public SystemHelpDocumentFileOutput updateFile(@RequestBody SystemHelpDocumentFileInput input) {
        return (SystemHelpDocumentFileOutput) service.update(input);
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
     * 生成Html
     *
     * @param input
     * @return
     */
    @PostMapping("/generate/html")
    @ApiOperation(value = "生成Html")
    public void generateHtml(@RequestBody SystemHelpDocumentGenerateInput input) {
        service.generateHtml(input);
    }

    /**
     * 根据id查询单条数据
     *
     * @param input
     * @return
     */
    @PostMapping("query/single")
    @ApiOperation(value = "根据id查询单条数据")
    public SystemHelpDocumentOutput queryById(@Valid @RequestBody DefaultEntityDto input) {
        return service.queryById(input.getId());
    }

    /**
     * 查询子级
     *
     * @param input
     * @return
     */
    @PostMapping("/query/children")
    @ApiOperation(value = "查询子级")
    public List<SystemHelpDocumentListOutput> queryChildren(@RequestBody DefaultChildrenQueryInput input) {
        return service.queryChildren(input);
    }

}
