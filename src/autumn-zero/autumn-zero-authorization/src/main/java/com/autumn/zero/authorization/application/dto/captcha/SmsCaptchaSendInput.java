package com.autumn.zero.authorization.application.dto.captcha;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 短信发送输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 12:12
 */
@ToString(callSuper = true)
@Getter
@Setter
public class SmsCaptchaSendInput extends SmsCaptchaSendBaseInput {

    private static final long serialVersionUID = 3454406428729359444L;

    /**
     * 发送类型
     */
    @ApiModelProperty(value = "发送类型)")
    @NotNull(message = "发送类型不能为空。")
    private Integer sendType;


}
