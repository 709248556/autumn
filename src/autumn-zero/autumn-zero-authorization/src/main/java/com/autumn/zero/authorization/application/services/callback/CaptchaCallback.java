package com.autumn.zero.authorization.application.services.callback;

import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.util.ImageCaptcha;
import com.autumn.zero.authorization.application.dto.captcha.ImageCaptchaInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsSendTemplateDto;

import java.util.Collection;

/**
 * 验证码回调
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 12:23
 */
public interface CaptchaCallback {

    /**
     * 获取图形验证码过期秒数
     *
     * @return
     */
    int getImageExpireSecond();

    /**
     * 创建图形验证码
     * @param input
     * @return
     */
    ImageCaptcha createImageCaptcha(ImageCaptchaInput input);

    /**
     * 获取短信发送重复间隔
     *
     * @return
     */
    int getSmsSendRepeatIntervalSecond();

    /**
     * 获取短信重复发送允许次数
     *
     * @return
     */
    int getSmsRepeatSendAllowCount();

    /**
     * 是否是用户注册发送短信
     *
     * @param sendType
     * @return
     */
    boolean isUserRegisterSmsSend(int sendType);

    /**
     * 是否是用户登录发送短信
     *
     * @param sendType
     * @return
     */
    boolean isUserLoginSmsSend(int sendType);

    /**
     * 是否查询用户手机号
     *
     * @param sendType
     * @return
     */
    boolean isQueryUserMobilePhone(int sendType);

    /**
     * 存在用户手机号
     *
     * @param phoneNumber 手机号
     * @return
     */
    boolean existUserByMobilePhone(String phoneNumber);

    /**
     * 查询用户手机号
     *
     * @param userId 用户id
     * @return
     */
    String findUserMobilePhone(long userId);

    /**
     * 获取修改/找回密码发送类型
     * @return
     */
    int getUpdatePasswordSendType();

    /**
     * 是否存在发送类型
     *
     * @param value 值
     * @return
     */
    boolean existSmsSendType(Integer value);

    /**
     * 短信发送类型列表
     *
     * @return
     */
    Collection<IntegerConstantItemValue> smsSendTypeList();

    /**
     * 创建短信发送模板
     *
     * @param sendType 发送类型
     * @return
     */
    SmsSendTemplateDto createSmsSendTemplate(int sendType);
}
