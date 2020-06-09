package com.autumn.zero.common.library.web.controllers.admin.sys;

import com.autumn.zero.common.library.application.dto.sys.config.SystemInfoConfigInput;
import com.autumn.zero.common.library.application.dto.sys.config.SystemInfoConfigOutput;
import com.autumn.zero.common.library.application.dto.sys.help.SystemHelpDocumentVisitOutput;
import com.autumn.zero.common.library.application.services.sys.SystemHelpDocumentAppService;
import com.autumn.zero.common.library.application.services.sys.SystemInfoConfigAppService;
import com.autumn.zero.file.storage.FileStorageUtils;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 管理端系统信息配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 23:38
 **/
@RestController
@RequestMapping("/sys/info")
@Api(tags = "系统信息配置")
public class AdminSystemInfoConfigController {

    private final SystemInfoConfigAppService service;
    private final SystemHelpDocumentAppService documentAppService;

    /**
     * 实例化
     *
     * @param service
     * @param documentAppService
     */
    public AdminSystemInfoConfigController(SystemInfoConfigAppService service, SystemHelpDocumentAppService documentAppService) {
        this.service = service;
        this.documentAppService = documentAppService;
    }

    /**
     * 查询
     *
     * @return
     */
    @PostMapping("/query")
    @ApiOperation(value = "查询")
    public SystemInfoConfigOutput query() {
        return service.queryForOutput();
    }

    /**
     * 保存
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    @RequiresUser
    public SystemInfoConfigOutput save(@RequestBody SystemInfoConfigInput input) {
        return service.save(input);
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload/file")
    @ApiOperation(value = "上传图片")
    @RequiresUser
    public FileAttachmentInformationResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return FileStorageUtils.saveUploadFile(this.service, file);
    }

    /**
     * 查询帮助文档
     *
     * @return
     */
    @PostMapping("/query/help/document")
    @ApiOperation(value = "查询帮助文档")
    public SystemHelpDocumentVisitOutput queryHelpDocument() {
        SystemInfoConfigOutput configOutput = service.queryForOutput();
        if (configOutput == null || configOutput.getSystemHelpDocNo() == null) {
            return null;
        }
        return documentAppService.queryVisit(configOutput.getSystemHelpDocNo());
    }
}
