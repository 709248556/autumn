package com.autumn.zero.authorization.application.dto.auth;

import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 更新密码输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 16:05
 */
@ToString(callSuper = true)
@Getter
@Setter
public class UpdatePasswordInput implements DataValidation, Serializable {

    private static final long serialVersionUID = 63486076169692538L;

    @ApiModelProperty(value = "旧密码")
    @NotNullOrBlank(message = "旧密码不能为空。")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    @NotNullOrBlank(message = "新密码不能为空。")
    private String newPassword;

    @Override
    public void valid() {
        ValidationUtils.validation(this);
    }
}
