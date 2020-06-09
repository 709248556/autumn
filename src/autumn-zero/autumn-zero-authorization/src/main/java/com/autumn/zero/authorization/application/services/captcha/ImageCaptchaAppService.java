package com.autumn.zero.authorization.application.services.captcha;

import com.autumn.application.service.ApplicationService;
import com.autumn.zero.authorization.application.dto.captcha.ImageCaptchaInput;
import com.autumn.zero.authorization.application.dto.captcha.ImageCaptchaOutput;

import java.awt.image.BufferedImage;

/**
 * 图形验证码
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 18:14
 **/
public interface ImageCaptchaAppService extends ApplicationService {

    /**
     * 验证码
     *
     * @param input 输入
     * @returnc
     */
    BufferedImage captchaByImage(ImageCaptchaInput input);

    /**
     * 验证码
     *
     * @param input 输入
     * @returnc
     */
    ImageCaptchaOutput captcha(ImageCaptchaInput input);

    /**
     * 检查验证码
     *
     * @param imageCode 图片验证码
     * @param isDelete  是否删除
     * @return 成功 返true,否则 false
     */
    boolean checkCaptcha(String imageCode, boolean isDelete);

}
