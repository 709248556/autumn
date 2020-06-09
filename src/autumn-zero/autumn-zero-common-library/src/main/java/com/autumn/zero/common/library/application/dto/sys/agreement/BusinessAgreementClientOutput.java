package com.autumn.zero.common.library.application.dto.sys.agreement;

import com.autumn.application.dto.DefaultEntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 业务协议客户端输出
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 18:23
 **/
@Getter
@Setter
public class BusinessAgreementClientOutput extends DefaultEntityDto {

    private static final long serialVersionUID = -3301624674951775350L;

    /**
     * 协议类型
     */
    @ApiModelProperty(value = "协议类型(必输)")
    @NotNull(message = "协议类型不能为空。")
    private Integer agreementType;

    /**
     * 协议类型名称
     */
    @ApiModelProperty(value = "协议类型名称")
    private String agreementTypeName;

    /**
     * 名称
     */
    @ApiModelProperty(value = "协议名称")
    private String name;

    /**
     * 协议内容Html
     */
    @ApiModelProperty(value = "协议内容Html")
    private String agreementContent;

}
