package com.autumn.zero.authorization.controllers.auth;

import com.autumn.security.annotation.ExcludeTokenAutoLogin;
import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.dto.auth.wechat.WeChatAppTokenInput;
import com.autumn.zero.authorization.application.services.auth.WeChatAuthAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 微信身份认证
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 02:16
 **/
@RestController
@RequestMapping("/auth/wechat")
@Api(tags = "微信身份认证")
public class WeChatAuthController<TUserLoginInfo extends UserLoginInfoOutput> {

    private final WeChatAuthAppService<TUserLoginInfo> service;

    /**
     * 实例化
     *
     * @param service
     */
    public WeChatAuthController(WeChatAuthAppService<TUserLoginInfo> service) {
        this.service = service;
    }

    /**
     * 微信 app 登录
     *
     * @param input
     * @return
     */
    @ExcludeTokenAutoLogin
    @PostMapping(path = "/login/app")
    @ApiOperation(value = "微信 app 登录")
    public TUserLoginInfo loginByWeChatApp(@Valid @RequestBody WeChatAppTokenInput input) {
        return this.service.loginByWeChatApp(input, true);
    }

}
