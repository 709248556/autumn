package com.autumn.zero.authorization.application.services.callback.impl;

import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.exception.NotSupportException;
import com.autumn.zero.authorization.application.dto.captcha.SmsSendTemplateDto;

import java.util.Collection;

/**
 * 默认验证码回调
 *
 * @Description TODO AbstractCaptchaCallback
 * @Author 老码农
 * @Date 2019-07-26 12:27
 */
public class DefaultCaptchaCallback extends AbstractCaptchaCallback {

    private NotSupportException createNotSupportException(){
        return new NotSupportException("未配置或不支持。");
    }


    @Override
    public boolean isUserRegisterSmsSend(int sendType) {
        throw createNotSupportException();
    }

    @Override
    public boolean isUserLoginSmsSend(int sendType) {
        throw createNotSupportException();
    }

    @Override
    public boolean isQueryUserMobilePhone(int sendType) {
        throw createNotSupportException();
    }

    @Override
    public boolean existUserByMobilePhone(String phoneNumber) {
        throw createNotSupportException();
    }

    @Override
    public String findUserMobilePhone(long userId) {
        throw createNotSupportException();
    }

    @Override
    public int getUpdatePasswordSendType() {
        throw createNotSupportException();
    }

    @Override
    public boolean existSmsSendType(Integer value) {
        throw createNotSupportException();
    }

    @Override
    public Collection<IntegerConstantItemValue> smsSendTypeList() {
        throw createNotSupportException();
    }

    @Override
    public SmsSendTemplateDto createSmsSendTemplate(int sendType) {
        throw createNotSupportException();
    }
}
