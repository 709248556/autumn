package com.autumn.zero.common.library.application.dto.sys.agreement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 默认协议查询输入
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 18:27
 **/
@Getter
@Setter
public class DefaultAgreementQueryInput implements Serializable {

    private static final long serialVersionUID = -4160927090379661381L;

    /**
     * 协议类型
     */
    @ApiModelProperty(value = "协议类型(必输)")
    @NotNull(message = "协议类型不能为空。")
    private Integer agreementType;
}
