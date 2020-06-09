package com.autumn.zero.authorization.application.dto.auth;

import com.autumn.security.token.DefaultCredentialsDeviceInfo;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaCheckInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 短信验证码登录
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 14:21
 **/
@Getter
@Setter
public class SmsCaptchaLoginInput extends SmsCaptchaCheckInput {

    private static final long serialVersionUID = 2791149052454135324L;

    /**
     * 认证设备信息
     */
    @ApiModelProperty(value = "认证设备信息")
    private DefaultCredentialsDeviceInfo credentialsDeviceInfo;
}
