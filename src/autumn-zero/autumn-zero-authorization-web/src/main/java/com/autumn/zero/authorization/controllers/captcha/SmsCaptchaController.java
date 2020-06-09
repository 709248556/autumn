package com.autumn.zero.authorization.controllers.captcha;

import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.util.AutoMapUtils;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaSendBaseInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaSendInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaTokenDto;
import com.autumn.zero.authorization.application.services.callback.CaptchaCallback;
import com.autumn.zero.authorization.application.services.captcha.SmsCaptchaAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

/**
 * 短信验证码控制器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 00:28
 **/
@RestController
@RequestMapping("/captcha/sms")
@Api(tags = "短信验证码")
public class SmsCaptchaController {

    private final SmsCaptchaAppService service;
    private final CaptchaCallback captchaCallback;

    public SmsCaptchaController(SmsCaptchaAppService service, CaptchaCallback captchaCallback) {
        this.service = service;
        this.captchaCallback = captchaCallback;
    }

    /**
     * 发送短信
     */
    @PostMapping("/send")
    @ApiOperation(value = "发送短信")
    public SmsCaptchaTokenDto sendSms(@Valid @RequestBody SmsCaptchaSendInput input) {
        return service.sendSms(input);
    }

    /**
     * 短信发送类型列表
     *
     * @return
     */
    @PostMapping("/send/types")
    @ApiOperation(value = "短信发送类型列表")
    public Collection<IntegerConstantItemValue> smsSendTypeList() {
        return service.smsSendTypeList();
    }

    /**
     * 重复发送短信
     */
    @PostMapping("/send/repeat")
    @ApiOperation(value = "重复发送短信")
    public SmsCaptchaTokenDto repeatSendSms() {
        return service.repeatSendSms();
    }

    /**
     * 更新密码/找回发送短信
     */
    @PostMapping("/send/update/password")
    @ApiOperation(value = "更新密码/找回发送短信")
    public SmsCaptchaTokenDto updatePasswordSendSms(@Valid @RequestBody SmsCaptchaSendBaseInput input) {
        SmsCaptchaSendInput sendInput = AutoMapUtils.map(input, SmsCaptchaSendInput.class);
        sendInput.setSendType(captchaCallback.getUpdatePasswordSendType());
        return service.sendSms(sendInput);
    }


}
