package com.autumn.zero.authorization.application.services.captcha;

import com.autumn.application.service.ApplicationService;
import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaCheck;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaSendInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaSendStatus;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaTokenDto;

import java.util.Collection;

/**
 * 短信验证码应用服务
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 12:02
 */
public interface SmsCaptchaAppService extends ApplicationService {

    /**
     * 发送短信
     *
     * @param input 输入
     * @return
     */
    SmsCaptchaTokenDto sendSms(SmsCaptchaSendInput input);

    /**
     * 重复发送短信
     *
     * @return
     */
    SmsCaptchaTokenDto repeatSendSms();

    /**
     * 检查发送状态
     *
     * @param input    输入
     * @param isDelete 是否删除
     * @return 成功 SmsSendStatus ,否则 null
     */
    SmsCaptchaSendStatus checkSendStatus(SmsCaptchaCheck input, boolean isDelete);

    /**
     * 检查发送状态
     *
     * @param token    票据
     * @param smsCode  短信验证码
     * @param isDelete 是否删除
     * @return 成功 SmsSendStatus ,否则 null
     */
    SmsCaptchaSendStatus checkSendStatus(String token, String smsCode, boolean isDelete);

    /**
     * 短信发送类型列表
     *
     * @return
     */
    Collection<IntegerConstantItemValue> smsSendTypeList();
}
