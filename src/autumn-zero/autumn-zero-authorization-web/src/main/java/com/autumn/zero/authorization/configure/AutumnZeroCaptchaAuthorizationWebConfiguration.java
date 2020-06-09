package com.autumn.zero.authorization.configure;

import com.autumn.zero.authorization.application.services.callback.CaptchaCallback;
import com.autumn.zero.authorization.application.services.captcha.ImageCaptchaAppService;
import com.autumn.zero.authorization.application.services.captcha.SmsCaptchaAppService;
import com.autumn.zero.authorization.controllers.captcha.ImageCaptchaController;
import com.autumn.zero.authorization.controllers.captcha.SmsCaptchaController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 01:50
 **/
@Configuration
public class AutumnZeroCaptchaAuthorizationWebConfiguration {

    /**
     * 图形验证码
     *
     * @param service 服务
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ImageCaptchaController.class)
    @ConditionalOnBean(value = {ImageCaptchaAppService.class})
    public ImageCaptchaController autumnZeroImageCaptchaController(ImageCaptchaAppService service) {
        return new ImageCaptchaController(service);
    }

    /**
     * 短信验证码
     *
     * @param service         服务
     * @param captchaCallback 回调
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCaptchaController.class)
    @ConditionalOnBean(value = {SmsCaptchaAppService.class, CaptchaCallback.class})
    public SmsCaptchaController autumnZeroSmsCaptchaController(SmsCaptchaAppService service, CaptchaCallback captchaCallback) {
        return new SmsCaptchaController(service, captchaCallback);
    }
}
