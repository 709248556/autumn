package com.autumn.zero.authorization.application.dto.captcha;

import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 短信密码输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 12:10
 */
@ToString(callSuper = true)
@Getter
@Setter
public class SmsCaptchaPasswordInput extends SmsCaptchaCheckInput {

    private static final long serialVersionUID = 6702095370410270345L;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码(必输)", required = true, dataType = "String")
    @NotNullOrBlank(message = "密码不能为空。")
    @Length(max = 20, min = 6, message = "密码长度为6-20位。")
    private String password;
}
