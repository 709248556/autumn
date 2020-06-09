package com.autumn.zero.common.library.application.services.sys;

import com.autumn.application.service.EditByStatusApplicationService;
import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementClientOutput;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementDetailsOutput;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementInput;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementListOutput;
import com.autumn.zero.file.storage.application.services.FileExportAppService;

import java.util.Collection;

/**
 * 业务协议应用服务
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 18:29
 **/
public interface BusinessAgreementAppService extends
        EditByStatusApplicationService<Long,
                BusinessAgreementInput,
                BusinessAgreementInput,
                BusinessAgreementListOutput,
                BusinessAgreementDetailsOutput>
        , FileExportAppService {

    /**
     * 查询协议
     *
     * @param id 主键
     * @return
     */
    BusinessAgreementClientOutput queryAgreementById(Long id);

    /**
     * 查询默认协议
     *
     * @param agreementType 协议类型
     * @return
     */
    BusinessAgreementClientOutput queryDefaultAgreement(Integer agreementType);

    /**
     * 协议类型列表
     *
     * @return
     */
    Collection<IntegerConstantItemValue> agreementTypeList();
}
