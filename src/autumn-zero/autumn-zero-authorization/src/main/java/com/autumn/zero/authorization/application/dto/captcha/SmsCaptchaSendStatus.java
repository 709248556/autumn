package com.autumn.zero.authorization.application.dto.captcha;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 短信发送状态
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 12:15
 */
@ToString(callSuper = true)
@Getter
@Setter
public class SmsCaptchaSendStatus implements Serializable {

    private static final long serialVersionUID = 7905329727573763848L;

    /**
     * 发送时间(时间戳）
     */
    @ApiModelProperty(value = "发送时间")
    private long sendTime;

    /**
     * 发送Token
     */
    @ApiModelProperty(value = "发送Token")
    private String token;

    /**
     * 发送类型
     */
    @ApiModelProperty(value = "发送类型")
    private int sendType;

    /**
     * 重复发送次数
     */
    @ApiModelProperty(value = "重复发送次数")
    private int repeatSendCount;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobilePhone;

    /**
     * 短信验证码
     */
    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    /**
     * 会话id
     */
    @ApiModelProperty(value = "会话id")
    private String sessionId;
}
