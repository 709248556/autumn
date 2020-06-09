package com.autumn.zero.authorization.application.dto.captcha;

import com.autumn.validation.DataValidation;

/**
 * 短信验证检查
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 12:07
 */
public interface SmsCaptchaCheck extends DataValidation {

    /**
     * 获取 Token
     *
     * @return
     */
    String getToken();

    /**
     * 设置 Token
     *
     * @param token 票据
     */
    void setToken(String token);

    /**
     * 获取短信验证码
     *
     * @return
     */
    String getSmsCode();

    /**
     * 设置短信验证码
     *
     * @param smsCode 短信验证码
     */
    void setSmsCode(String smsCode);
}
