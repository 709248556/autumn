package com.autumn.zero.authorization.application.dto.auth;

import com.autumn.security.token.DefaultCredentialsDeviceInfo;
import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户名称与密码登录输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 15:57
 */
@ToString(callSuper = true)
@Getter
@Setter
public class UserNamePasswordLoingInput implements DataValidation, Serializable {
    private static final long serialVersionUID = -2694063985502017713L;

    @ApiModelProperty(value = "用户名称")
    @NotNullOrBlank(message = "用户名称不能为空")
    private String userName;

    @ApiModelProperty(value = "密码")
    @NotNullOrBlank(message = "密码名称不能为空")
    private String password;

    /**
     * 是否记住我
     */
    @ApiModelProperty(value = "记住我")
    private boolean rememberMe;

    /**
     * 图片验证码
     */
    @ApiModelProperty(value = "图片验证码")
    private String imageCode;

    /**
     * 认证设备信息
     */
    @ApiModelProperty(value = "认证设备信息")
    private DefaultCredentialsDeviceInfo credentialsDeviceInfo;

    @Override
    public void valid() {
        ValidationUtils.validation(this);
    }
}
