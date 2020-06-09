package com.autumn.zero.authorization.application.dto.captcha;

import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 短信Token
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 12:18
 */
@ToString(callSuper = true)
@Getter
@Setter
public class SmsCaptchaTokenDto implements Serializable {
    private static final long serialVersionUID = 5284157263612734838L;

    /**
     * 票据
     */
    @ApiModelProperty(value = "票据")
    @NotNullOrBlank(message = "票据不能为空。")
    private String token;

    /**
     * 重发间隔(秒)
     */
    @ApiModelProperty(value = "重发间隔(秒)")
    private Integer repeatInterval;

    /**
     * 重发允许次数
     */
    @ApiModelProperty(value = "重发允许次数")
    private Integer repeatAllowCount;

    /**
     * 重发剩余次数
     */
    @ApiModelProperty(value = "重发剩余次数")
    private Integer repeatSurplusCount;
}
