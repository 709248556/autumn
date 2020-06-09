package com.autumn.zero.authorization.application.dto.captcha;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.MobilePhone;
import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 短信发送基本输入
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 01:37
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class SmsCaptchaSendBaseInput extends DefaultDataValidation {

    private static final long serialVersionUID = -8405807757820845593L;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @NotNullOrBlank(message = "手机号码不能为空。")
    @MobilePhone(message = "手机号码格式不正确。")
    private String mobilePhone;

    /**
     * 图片验证码
     */
    @ApiModelProperty(value = "图片验证码")
    @NotNullOrBlank(message = "图片验证码不能为空。")
    private String imageCode;

}
