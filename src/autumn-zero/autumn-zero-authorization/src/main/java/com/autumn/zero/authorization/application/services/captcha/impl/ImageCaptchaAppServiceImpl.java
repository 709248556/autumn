package com.autumn.zero.authorization.application.services.captcha.impl;

import com.autumn.application.service.AbstractApplicationService;
import com.autumn.exception.ExceptionUtils;
import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.util.ImageCaptcha;
import com.autumn.zero.authorization.application.dto.captcha.ImageCaptchaInput;
import com.autumn.zero.authorization.application.dto.captcha.ImageCaptchaOutput;
import com.autumn.zero.authorization.application.services.callback.CaptchaCallback;
import com.autumn.zero.authorization.application.services.captcha.ImageCaptchaAppService;
import com.autumn.zero.authorization.utils.AuthUtils;

import java.awt.image.BufferedImage;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 图形验证码应用服务
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 11:38
 */
public class ImageCaptchaAppServiceImpl extends AbstractApplicationService implements ImageCaptchaAppService {

    /**
     * 图片验证缓存
     */
    private static final String IMAGE_VERIFICATION_PREFIX = "IMAGE_VERIFICATION_";

    private final AutumnRedisTemplate redisTemplate;
    private final CaptchaCallback captchaCallback;

    public ImageCaptchaAppServiceImpl(AutumnRedisTemplate redisTemplate, CaptchaCallback captchaCallback) {
        this.redisTemplate = redisTemplate;
        this.captchaCallback = captchaCallback;
    }

    @Override
    public String getModuleName() {
        return "图片验证码服务";
    }

    @Override
    public BufferedImage captchaByImage(ImageCaptchaInput input) {
        ImageCaptcha imageCaptcha = this.createImageCaptcha(input);
        return imageCaptcha.getImage();
    }

    @Override
    public ImageCaptchaOutput captcha(ImageCaptchaInput input) {
        ImageCaptcha imageCaptcha = this.createImageCaptcha(input);
        ImageCaptchaOutput result = new ImageCaptchaOutput();
        result.setFormat(imageCaptcha.getImageFormat());
        result.setImageBase64(imageCaptcha.toBase64());
        return result;
    }

    @Override
    public boolean checkCaptcha(String imageCode, boolean isDelete) {
        ExceptionUtils.checkNotNullOrBlank(imageCode, "imageCode");
        String key = this.getKey();
        String oldCode = redisTemplate.opsForCustomValue().get(key);
        boolean result = imageCode.equalsIgnoreCase(oldCode);
        if (isDelete && result) {
            redisTemplate.delete(key);
        }
        return !result;
    }

    private String getKey() {
        String sessionId = AuthUtils.checkSessionId();
        return (IMAGE_VERIFICATION_PREFIX + sessionId).toUpperCase(Locale.ENGLISH);
    }

    /**
     * 创建图形码证码
     *
     * @param input
     * @return
     */
    protected ImageCaptcha createImageCaptcha(ImageCaptchaInput input) {
        if (input == null) {
            input = new ImageCaptchaInput();
        }
        String key = this.getKey();
        ImageCaptcha imageCaptcha = this.captchaCallback.createImageCaptcha(input);
        redisTemplate.opsForValue().set(key, imageCaptcha.getCode(), this.captchaCallback.getImageExpireSecond(), TimeUnit.SECONDS);
        return imageCaptcha;
    }
}
