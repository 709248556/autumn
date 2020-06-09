package com.autumn.zero.authorization.controllers.auth;

import com.autumn.security.annotation.ExcludeTokenAutoLogin;
import com.autumn.zero.authorization.application.dto.auth.SmsCaptchaLoginInput;
import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaCheckInput;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaPasswordInput;
import com.autumn.zero.authorization.application.services.auth.SmsAuthAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 短信身份认证
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 02:16
 **/
@RestController
@RequestMapping("/auth/sms")
@Api(tags = "短信身份认证")
public class SmsAuthController<TUserLoginInfo extends UserLoginInfoOutput> {

    private final SmsAuthAppService<TUserLoginInfo> service;

    /**
     * 实例化
     *
     * @param service
     */
    public SmsAuthController(SmsAuthAppService<TUserLoginInfo> service) {
        this.service = service;
    }

    /**
     * 根据短信 Token登录
     *
     * @param input 输入
     * @return
     */
    @ExcludeTokenAutoLogin
    @PostMapping(path = "/login")
    @ApiOperation(value = "根据短信 Token 登录")
    public TUserLoginInfo loginBySmsToken(@Valid @RequestBody SmsCaptchaLoginInput input) {
        return service.loginBySmsToken(input);
    }

    /**
     * 根据短信 Token 绑定手机
     *
     * @param input 输入
     * @return
     */
    @PostMapping(path = "/binding/telephone")
    @ApiOperation(value = "根据短信 Token 绑定手机")
    @RequiresUser
    public void bindingMobilePhone(@Valid @RequestBody SmsCaptchaCheckInput input) {
        service.bindingMobilePhone(input);
    }

    /**
     * 根据短信更新密码
     *
     * @param ：@param input 输入
     */
    @PostMapping("/update/password")
    @ApiOperation(value = "根据短信更新密码")
    public void updatePasswordBySmsToken(@Valid @RequestBody SmsCaptchaPasswordInput input) {
        this.service.updatePasswordBySmsToken(input);
    }

}
