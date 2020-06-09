package com.autumn.zero.authorization.application.dto.auth;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户账号输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 15:53
 */
@ToString(callSuper = true)
@Getter
@Setter
public class UserAccountInput extends DefaultDataValidation {

    private static final long serialVersionUID = -7012347901359006525L;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号,用户名、手机号、邮箱")
    @NotNullOrBlank(message = "账号不能为空。")
    private String account;
}
