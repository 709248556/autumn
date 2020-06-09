package com.autumn.zero.authorization.application.dto.auth;


import com.autumn.security.token.DefaultCredentialsDeviceInfo;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaPasswordInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户短信注册输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 17:21
 */
@ToString(callSuper = true)
@Getter
@Setter
public class UserSmsRegisterInput extends SmsCaptchaPasswordInput {
    private static final long serialVersionUID = 364698440953939137L;

    /**
     * 注册后是否登录
     */
    @ApiModelProperty(value = "注册后是否登录")
    boolean login;

    /**
     * 认证设备信息
     */
    @ApiModelProperty(value = "认证设备信息")
    private DefaultCredentialsDeviceInfo credentialsDeviceInfo;
}
