package com.autumn.zero.authorization.application.dto.captcha;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 图形验证码输出
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 11:34
 */
@ToString(callSuper = true)
@Getter
@Setter
public class ImageCaptchaOutput implements Serializable {
    private static final long serialVersionUID = 4783710122433901861L;

    /**
     * 图片格式
     */
    @ApiModelProperty(value = "图片格式")
    private String format;

    /**
     * 图片Base64编辑
     */
    @ApiModelProperty(value = "图片Base64编辑")
    private String imageBase64;
}
