package com.autumn.zero.common.library.application.dto.personal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户收货地址输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 16:43
 **/
@Getter
@Setter
public class UserReceivingAddressOutput extends UserReceivingAddressInput {

    private static final long serialVersionUID = -5513878298929840299L;

    /**
     * 行政区代码
     */
    @ApiModelProperty(value = "行政区代码")
    private String regionCode;

    /**
     * 行政区名称
     */
    @ApiModelProperty(value = "行政区名称")
    private String regionName;

    /**
     * 完整行政区id
     */
    @ApiModelProperty(value = "完整行政区id")
    private String regionFullId;

    /**
     * 完整行政区代码
     */
    @ApiModelProperty(value = "完整行政区代码")
    private String regionFullCode;

    /**
     * 完整行政区名称
     */
    @ApiModelProperty(value = "完整行政区名称")
    private String regionFullName;

}