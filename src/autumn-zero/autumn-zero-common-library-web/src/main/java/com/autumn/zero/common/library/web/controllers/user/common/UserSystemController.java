package com.autumn.zero.common.library.web.controllers.user.common;

import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementClientOutput;
import com.autumn.zero.common.library.application.dto.sys.agreement.DefaultAgreementQueryInput;
import com.autumn.zero.common.library.application.dto.sys.config.SystemInfoConfigOutput;
import com.autumn.zero.common.library.application.dto.sys.help.SystemHelpDocumentVisitOutput;
import com.autumn.zero.common.library.application.services.sys.BusinessAgreementAppService;
import com.autumn.zero.common.library.application.services.sys.SystemHelpDocumentAppService;
import com.autumn.zero.common.library.application.services.sys.SystemInfoConfigAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

/**
 * 客户端系统控制器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 15:24
 **/
@RestController
@RequestMapping("/common/sys")
@Api(tags = "系统")
public class UserSystemController {

    private final SystemInfoConfigAppService infoConfigAppService;
    private final SystemHelpDocumentAppService documentAppService;
    private final BusinessAgreementAppService agreementAppService;

    public UserSystemController(SystemInfoConfigAppService infoConfigAppService, SystemHelpDocumentAppService documentAppService, BusinessAgreementAppService agreementAppService) {
        this.infoConfigAppService = infoConfigAppService;
        this.documentAppService = documentAppService;
        this.agreementAppService = agreementAppService;
    }

    /**
     * 查询协议类型列表
     *
     * @return
     */
    @PostMapping("query/agreement/types")
    @ApiOperation(value = "查询协议类型列表")
    public Collection<IntegerConstantItemValue> agreementTypeList() {
        return this.agreementAppService.agreementTypeList();
    }

    /**
     * 查询用户协议
     *
     * @param input
     * @return
     */
    @PostMapping("/query/agreement/user")
    @ApiOperation(value = "查询用户协议")
    public BusinessAgreementClientOutput queryDefaultAgreement(@Valid @RequestBody DefaultAgreementQueryInput input) {
        return this.agreementAppService.queryDefaultAgreement(input.getAgreementType());
    }

    /**
     * 查询系统信息
     *
     * @return
     */
    @PostMapping("/info")
    @ApiOperation(value = "查询系统信息")
    public SystemInfoConfigOutput systemInfo() {
        return this.infoConfigAppService.queryForOutput();
    }

    /**
     * 查询帮助文档
     *
     * @return
     */
    @PostMapping("/help/document")
    @ApiOperation(value = "查询帮助文档")
    public SystemHelpDocumentVisitOutput queryHelpDocumentVisit() {
        SystemInfoConfigOutput configOutput = infoConfigAppService.queryForOutput();
        if (configOutput == null || configOutput.getSiteHelpDocNo() == null) {
            return null;
        }
        return documentAppService.queryVisit(configOutput.getSiteHelpDocNo());
    }


}
