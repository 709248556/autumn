package com.autumn.zero.authorization.application.services.auth;

import com.autumn.application.service.ApplicationService;
import com.autumn.zero.authorization.application.dto.auth.SmsCaptchaLoginInput;
import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.dto.auth.UserSmsRegisterInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaCheckInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaPasswordInput;

/**
 * 短信身份认证应用服务
 * <p>
 * </p>
 *
 * @param <TUserLoginInfo> 用户登录信息
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 18:40
 **/
public interface SmsAuthAppService<TUserLoginInfo extends UserLoginInfoOutput> extends ApplicationService {

    /**
     * 根据短信Token登录
     *
     * @param input 输入
     * @return
     */
    TUserLoginInfo loginBySmsToken(SmsCaptchaLoginInput input);

    /**
     * 根据短信更新密码
     *
     * @param input 输入
     */
    void updatePasswordBySmsToken(SmsCaptchaPasswordInput input);

    /**
     * 绑定手机
     *
     * @param input 输入
     */
    void bindingMobilePhone(SmsCaptchaCheckInput input);

    /**
     * 短信注册
     *
     * @param input 输入
     * @return
     */
    TUserLoginInfo userRegisterBySms(UserSmsRegisterInput input);
}
