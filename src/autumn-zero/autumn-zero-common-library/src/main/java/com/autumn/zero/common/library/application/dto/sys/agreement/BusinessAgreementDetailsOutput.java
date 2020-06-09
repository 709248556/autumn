package com.autumn.zero.common.library.application.dto.sys.agreement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务协议详情输出
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 18:21
 **/
@Getter
@Setter
public class BusinessAgreementDetailsOutput extends BusinessAgreementListOutput {

    private static final long serialVersionUID = -5675206905964906090L;

    /**
     * 协议内容Html
     */
    @ApiModelProperty(value = "协议内容Html")
    private String agreementContent;
}
