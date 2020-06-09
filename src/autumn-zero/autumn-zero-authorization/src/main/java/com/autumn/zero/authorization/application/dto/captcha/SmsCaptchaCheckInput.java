package com.autumn.zero.authorization.application.dto.captcha;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 短信验证码检查输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 12:09
 */
@ToString(callSuper = true)
@Getter
@Setter
public class SmsCaptchaCheckInput extends DefaultDataValidation implements SmsCaptchaCheck {

    private static final long serialVersionUID = 5177298138244704966L;

    @ApiModelProperty(value = "token")
    @NotNullOrBlank(message = "Token 不能为空。")
    private String token;

    @ApiModelProperty(value = "短信验证码")
    @NotNullOrBlank(message = "短信验证码不能为空。")
    private String smsCode;

}
