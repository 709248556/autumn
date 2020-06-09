package com.autumn.zero.authorization.application.services.callback.impl;

import com.autumn.util.ImageCaptcha;
import com.autumn.zero.authorization.application.dto.captcha.ImageCaptchaInput;
import com.autumn.zero.authorization.application.services.callback.CaptchaCallback;

/**
 * 验证码回调抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 13:42
 */
public abstract class AbstractCaptchaCallback implements CaptchaCallback {

    /**
     * 图形验证码长度
     */
    public static final int DEFAULT_IMAGE_CAPTCHA_CODE_LENGTH = 4;
    /**
     * 图形验证码干扰线数量
     */
    public static final int DEFAULT_IMAGE_CAPTCHA_LINE_NUMBER = 20;
    /**
     * 图形验证码过期秒数
     */
    public static final int DEFAULT_IMAGE_EXPIRE_SECOND = 300;

    /**
     * 短信发送重复间隔
     */
    public static final int DEFAULT_SMS_SEND_REPEAT_INTERVAL_SECOND = 120;

    /**
     * 短信重复发送允许次数
     */
    public static final int DEFAULT_SMS_REPEAT_SEND_ALLOW_COUNT = 3;

    /**
     * 图形验证码长度
     *
     * @return
     */
    public int getImageCaptchaCodeLength() {
        return DEFAULT_IMAGE_CAPTCHA_CODE_LENGTH;
    }

    /**
     * 图形验证码干扰线数量
     *
     * @return
     */
    public int getImageCaptchaLineNumber() {
        return DEFAULT_IMAGE_CAPTCHA_LINE_NUMBER;
    }

    @Override
    public int getImageExpireSecond() {
        return DEFAULT_IMAGE_EXPIRE_SECOND;
    }

    @Override
    public ImageCaptcha createImageCaptcha(ImageCaptchaInput input) {
        ImageCaptcha imageCaptcha = new ImageCaptcha(input.effectiveWidth(),
                input.effectiveHeight(),
                this.getImageCaptchaCodeLength(),
                this.getImageCaptchaLineNumber());
        return imageCaptcha;
    }

    @Override
    public int getSmsSendRepeatIntervalSecond() {
        return DEFAULT_SMS_SEND_REPEAT_INTERVAL_SECOND;
    }

    @Override
    public int getSmsRepeatSendAllowCount() {
        return DEFAULT_SMS_REPEAT_SEND_ALLOW_COUNT;
    }
}
