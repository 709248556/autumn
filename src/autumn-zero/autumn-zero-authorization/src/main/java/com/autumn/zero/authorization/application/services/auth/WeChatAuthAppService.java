package com.autumn.zero.authorization.application.services.auth;

import com.autumn.application.service.ApplicationService;
import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.dto.auth.wechat.WeChatAppTokenInput;

/**
 * 微信身份认证应用服务
 * <p>
 * </p>
 *
 * @param <TUserLoginInfo> 用户登录信息
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 13:04
 **/
public interface WeChatAuthAppService<TUserLoginInfo extends UserLoginInfoOutput> extends ApplicationService {

    /**
     * 根据微信 app 登录
     *
     * @param input      输入
     * @param isRegister 不存在时，是否注册
     * @return
     */
    TUserLoginInfo loginByWeChatApp(WeChatAppTokenInput input, boolean isRegister);
}
