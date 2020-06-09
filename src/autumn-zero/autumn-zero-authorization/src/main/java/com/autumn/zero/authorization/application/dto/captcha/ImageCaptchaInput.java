package com.autumn.zero.authorization.application.dto.captcha;

import com.autumn.util.ImageCaptcha;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 图形验证码输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 11:33
 */
@ToString(callSuper = true)
@Getter
@Setter
public class ImageCaptchaInput implements Serializable {

    private static final long serialVersionUID = -1194695810319155341L;

    private static final int MAX_WIDTH = 800;

    private static final int MAX_HEIGHT = 600;

    /**
     * 宽度
     */
    @ApiModelProperty(value = "宽度")
    private Integer width;

    /**
     * 高度
     */
    @ApiModelProperty(value = "高度")
    private Integer height;

    /**
     * 有效宽度
     *
     * @return
     */
    public int effectiveWidth() {
        if (this.getWidth() == null || this.getWidth() <= 0) {
            return ImageCaptcha.DEFAULT_WIDTH;
        }
        return Math.min(this.getWidth(), MAX_WIDTH);
    }

    /**
     * 有效高度
     *
     * @return
     */
    public int effectiveHeight() {
        if (this.getHeight() == null || this.getHeight() <= 0) {
            return ImageCaptcha.DEFAULT_HEIGHT;
        }
        return Math.min(this.getHeight(), MAX_HEIGHT);
    }
}
