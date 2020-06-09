package com.autumn.zero.authorization.configure;

import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.sms.channel.SmsChannel;
import com.autumn.zero.authorization.application.services.callback.CaptchaCallback;
import com.autumn.zero.authorization.application.services.captcha.ImageCaptchaAppService;
import com.autumn.zero.authorization.application.services.captcha.SmsCaptchaAppService;
import com.autumn.zero.authorization.application.services.captcha.impl.ImageCaptchaAppServiceImpl;
import com.autumn.zero.authorization.application.services.captcha.impl.SmsCaptchaAppServiceImpl;
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
 * @create: 2020-03-30 00:53
 **/
@Configuration
public class AutumnZeroCaptchaAuthorizationConfiguration {

    /**
     * 图形码证码服务
     *
     * @param redisTemplate   模板
     * @param captchaCallback 回调
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ImageCaptchaAppService.class)
    @ConditionalOnBean({AutumnRedisTemplate.class, CaptchaCallback.class})
    public ImageCaptchaAppService zeroImageCaptchaAppService(AutumnRedisTemplate redisTemplate, CaptchaCallback captchaCallback) {
        return new ImageCaptchaAppServiceImpl(redisTemplate, captchaCallback);
    }


    /**
     * 短信码证码服务
     *
     * @param smsChannel        短信通道
     * @param redisTemplate     模板
     * @param captchaCallback   回调
     * @param captchaAppService 图形码证码
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCaptchaAppService.class)
    @ConditionalOnBean({SmsChannel.class, AutumnRedisTemplate.class, CaptchaCallback.class, ImageCaptchaAppService.class})
    public SmsCaptchaAppService zeroSmsCaptchaAppService(SmsChannel smsChannel, AutumnRedisTemplate redisTemplate, CaptchaCallback captchaCallback, ImageCaptchaAppService captchaAppService) {
        return new SmsCaptchaAppServiceImpl(smsChannel, redisTemplate, captchaCallback, captchaAppService);
    }

}
