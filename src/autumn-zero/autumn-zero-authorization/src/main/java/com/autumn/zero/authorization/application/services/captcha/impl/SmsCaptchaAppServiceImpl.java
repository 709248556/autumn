package com.autumn.zero.authorization.application.services.captcha.impl;

import com.autumn.application.service.AbstractApplicationService;
import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.exception.ExceptionUtils;
import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.security.AutumnNotLoginException;
import com.autumn.sms.channel.DefaultSmsMessage;
import com.autumn.sms.channel.SmsChannel;
import com.autumn.sms.channel.SmsMessage;
import com.autumn.util.StringUtils;
import com.autumn.zero.authorization.application.dto.captcha.*;
import com.autumn.zero.authorization.application.services.callback.CaptchaCallback;
import com.autumn.zero.authorization.application.services.captcha.ImageCaptchaAppService;
import com.autumn.zero.authorization.application.services.captcha.SmsCaptchaAppService;
import com.autumn.zero.authorization.utils.AuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 短信验证码应用服务实现
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 12:35
 */
public class SmsCaptchaAppServiceImpl extends AbstractApplicationService implements SmsCaptchaAppService {

    /**
     * 短信验证前缀
     */
    private static final String SMS_PREFIX = "SMS_SEND_STATUS_";

    /**
     * 短信验证会话前缀
     */
    private static final String SMS_SESSION_PREFIX = SMS_PREFIX + "SESSION_";

    /**
     * 短信验证号码前缀
     */
    private static final String SMS_MOBILE_PHONE_PREFIX = SMS_PREFIX + "MOBILE_PHONE_";

    private final SmsChannel channel;
    private final CaptchaCallback captchaCallback;
    private final AutumnRedisTemplate redisTemplate;
    private final ImageCaptchaAppService captchaAppService;

    /**
     * 实例化
     *
     * @param channel
     * @param redisTemplate
     * @param captchaCallback
     * @param captchaAppService
     */
    public SmsCaptchaAppServiceImpl(SmsChannel channel, AutumnRedisTemplate redisTemplate, CaptchaCallback captchaCallback, ImageCaptchaAppService captchaAppService) {
        this.channel = channel;
        this.redisTemplate = redisTemplate;
        this.captchaCallback = captchaCallback;
        this.captchaAppService = captchaAppService;
    }

    @Override
    public String getModuleName() {
        return "短信应用服务";
    }

    /**
     * 检查未登录
     */
    private void checkNotUserLogin() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            throw new AutumnNotLoginException();
        }
    }

    /**
     * 获取重复发送间隔(秒)
     *
     * @return
     */
    private int getRepeatInterval() {
        return captchaCallback.getSmsSendRepeatIntervalSecond();
    }

    /**
     * 获取重复发送允许次数
     *
     * @return
     */
    private int getRepeatAllowCount() {
        return captchaCallback.getSmsRepeatSendAllowCount();
    }

    /**
     * @param sendType
     * @return
     */
    private SmsSendTemplateDto getTemplateProperties(int sendType) {
        SmsSendTemplateDto dto = captchaCallback.createSmsSendTemplate(sendType);
        if (dto == null) {
            throw ExceptionUtils.throwValidationException("指定的发送类型不支持。");
        }
        return dto;
    }

    private String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private SmsCaptchaTokenDto createSmsTokenDto(SmsCaptchaSendStatus status) {
        SmsCaptchaTokenDto smsToken = new SmsCaptchaTokenDto();
        smsToken.setToken(status.getToken());
        smsToken.setRepeatInterval(this.getRepeatInterval());
        smsToken.setRepeatAllowCount(this.getRepeatAllowCount());
        smsToken.setRepeatSurplusCount(this.getRepeatAllowCount() - status.getRepeatSendCount());
        return smsToken;
    }

    @Override
    public SmsCaptchaTokenDto sendSms(SmsCaptchaSendInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        if (!captchaCallback.existSmsSendType(input.getSendType())) {
            ExceptionUtils.throwValidationException("发送类型不支持。");
        }
        SmsSendTemplateDto templateProperties = this.getTemplateProperties(input.getSendType());
        templateProperties.valid();
        if (captchaAppService.checkCaptcha(input.getImageCode(), true)) {
            throw AuthUtils.createCaptchaException("图形验证码不正确");
        }
        //用户登录
        if (captchaCallback.isUserLoginSmsSend(input.getSendType())) {
            if (!captchaCallback.existUserByMobilePhone(input.getMobilePhone())) {
                throw AuthUtils.createCaptchaException("指定的手机号未注册，请先注册。");
            }
        }
        //用户注册
        else if (captchaCallback.isUserRegisterSmsSend(input.getSendType())) {
            if (captchaCallback.existUserByMobilePhone(input.getMobilePhone())) {
                throw AuthUtils.createCaptchaException("指定的手机号已注册，不能重复注册。");
            }
        } else {
            if (captchaCallback.isQueryUserMobilePhone(input.getSendType())) {
                if (captchaCallback.existUserByMobilePhone(input.getMobilePhone())) {
                    throw AuthUtils.createCaptchaException("手机号未注册，请先注册。");
                }
            }
        }
        String sessionId = AuthUtils.checkSessionId();
        this.checkSend(input.getMobilePhone());
        String code = AuthUtils.randomSmsCode();
        String token = this.createToken();
        SmsMessage msg = this.createSmsMessage(templateProperties, input.getMobilePhone(), code);
        try {
            channel.send(msg);
            SmsCaptchaSendStatus status = new SmsCaptchaSendStatus();
            status.setMobilePhone(input.getMobilePhone());
            status.setSendTime(System.currentTimeMillis());
            status.setSendType(input.getSendType());
            status.setSessionId(sessionId);
            status.setRepeatSendCount(0);
            status.setSmsCode(code);
            status.setToken(token);
            this.saveSendStatus(status, templateProperties.getExpire());
            return this.createSmsTokenDto(status);
        } catch (Exception e) {
            this.getLogger().error(e.getMessage(), e);
            throw ExceptionUtils.throwValidationException("发送出错，短信通道出错。");
        }
    }

    @Override
    public SmsCaptchaTokenDto repeatSendSms() {
        String sessionId = AuthUtils.checkSessionId();
        SmsCaptchaSendStatus sessionStatus = redisTemplate.opsForCustomValue().get(this.getSessionKey(sessionId));
        if (sessionStatus == null) {
            throw ExceptionUtils.throwValidationException("未发送过或已过期。");
        }
        if (sessionStatus.getRepeatSendCount() >= this.getRepeatAllowCount()) {
            throw ExceptionUtils.throwValidationException("重复发送次数过多。");
        }
        this.checkSend(sessionStatus);
        String code = AuthUtils.randomSmsCode();
        String token = this.createToken();
        sessionStatus.setSessionId(sessionId);
        sessionStatus.setSendTime(System.currentTimeMillis());
        sessionStatus.setToken(token);
        sessionStatus.setSmsCode(code);
        sessionStatus.setRepeatSendCount(sessionStatus.getRepeatSendCount() + 1);
        SmsSendTemplateDto templateProperties = this.getTemplateProperties(sessionStatus.getSendType());
        templateProperties.valid();
        SmsMessage msg = this.createSmsMessage(templateProperties, sessionStatus.getMobilePhone(), code);
        try {
            channel.send(msg);
            this.saveSendStatus(sessionStatus, templateProperties.getExpire());
            return this.createSmsTokenDto(sessionStatus);
        } catch (Exception e) {
            this.getLogger().error(e.getMessage(), e);
            throw ExceptionUtils.throwValidationException("发送出错，短信通道出错。");
        }
    }

    @Override
    public SmsCaptchaSendStatus checkSendStatus(SmsCaptchaCheck input, boolean isDelete) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        return this.checkSendStatus(input.getToken(), input.getSmsCode(), isDelete);
    }

    @Override
    public SmsCaptchaSendStatus checkSendStatus(String token, String smsCode, boolean isDelete) {
        ExceptionUtils.checkNotNullOrBlank(token, "token");
        ExceptionUtils.checkNotNullOrBlank(smsCode, "smsCode");
        String sessionId = AuthUtils.checkSessionId();
        SmsCaptchaSendStatus sessionStatus = redisTemplate.opsForCustomValue().get(this.getSessionKey(sessionId));
        if (sessionStatus == null) {
            return null;
        }
        if (!sessionStatus.getSessionId().equalsIgnoreCase(sessionId)) {
            return null;
        }
        if (!sessionStatus.getToken().equalsIgnoreCase(token)) {
            return null;
        }
        if (!sessionStatus.getSmsCode().equalsIgnoreCase(smsCode)) {
            return null;
        }
        if (isDelete) {
            deleteStatus(sessionStatus, sessionId);
        }
        return sessionStatus;
    }

    @Override
    public Collection<IntegerConstantItemValue> smsSendTypeList() {
        return captchaCallback.smsSendTypeList();
    }

    /**
     * 删除状态
     *
     * @param status
     * @param sessionId
     */
    private void deleteStatus(SmsCaptchaSendStatus status, String sessionId) {
        if (status == null) {
            return;
        }
        redisTemplate.delete(this.getMobilePhoneKey(status.getMobilePhone()));
        redisTemplate.delete(this.getSessionKey(status.getSessionId()));
        if (!sessionId.equalsIgnoreCase(status.getSessionId())) {
            redisTemplate.delete(this.getSessionKey(sessionId));
        }
    }

    private void checkSend(String mobilePhone) {
        if (StringUtils.isNullOrBlank(mobilePhone)) {
            ExceptionUtils.throwValidationException("发送号码不能为空。");
        }
        SmsCaptchaSendStatus sessionStatus = redisTemplate.opsForCustomValue().get(this.getSessionKey());
        this.checkSend(sessionStatus);
        if (sessionStatus != null && !sessionStatus.getMobilePhone().equalsIgnoreCase(mobilePhone)) {
            SmsCaptchaSendStatus mobilePhoneStatus = redisTemplate.opsForCustomValue().get(this.getMobilePhoneKey(mobilePhone));
            this.checkSend(mobilePhoneStatus);
        }
    }

    private void checkSend(SmsCaptchaSendStatus status) {
        if (status != null) {
            if (status.getSendTime() + (this.getRepeatInterval() * 1000) > System.currentTimeMillis()) {
                throw ExceptionUtils.throwValidationException("发送间隔必须有 " + this.getRepeatInterval() + " 秒。");
            }
        }
    }

    private void saveSendStatus(SmsCaptchaSendStatus status, int expire) {
        redisTemplate.opsForValue().set(this.getSessionKey(status.getSessionId()), status, expire, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(this.getMobilePhoneKey(status.getMobilePhone()), status, expire, TimeUnit.SECONDS);
    }

    private String getSessionKey() {
        return getSessionKey(AuthUtils.checkSessionId());
    }

    private String getSessionKey(String sessionId) {
        return (SMS_SESSION_PREFIX + sessionId).toUpperCase(Locale.ENGLISH);
    }

    private String getMobilePhoneKey(String mobilePhone) {
        return (SMS_MOBILE_PHONE_PREFIX + mobilePhone).toUpperCase(Locale.ENGLISH);
    }

    /**
     * 创建短信消息
     *
     * @param templateProperties
     * @param mobilePhone
     * @param code
     * @return
     */
    private SmsMessage createSmsMessage(SmsSendTemplateDto templateProperties,
                                        String mobilePhone, String code) {
        SmsMessage msg = new DefaultSmsMessage();
        msg.setCreate(new Date());
        msg.setPhoneNumbers(mobilePhone);
        msg.setSignName(templateProperties.getSignName());
        msg.setTemplateCode(templateProperties.getTemplateCode());
        msg.setMessageContent(templateProperties.getMessageContent());
        msg.getParams().put(templateProperties.getParamName(), code);
        return msg;
    }

}
