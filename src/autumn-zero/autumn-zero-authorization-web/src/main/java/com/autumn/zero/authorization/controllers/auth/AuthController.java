package com.autumn.zero.authorization.controllers.auth;

import com.autumn.runtime.session.AutumnSession;
import com.autumn.security.annotation.ExcludeTokenAutoLogin;
import com.autumn.security.credential.AutumnCredentialsMatcher;
import com.autumn.spring.boot.properties.AutumnAuthProperties;
import com.autumn.zero.authorization.application.dto.auth.*;
import com.autumn.zero.authorization.application.services.auth.AuthAppServiceBase;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 身份认证控器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 00:01
 **/
@RestController
@RequestMapping("/auth")
@Api(tags = "基本身份认证")
public class AuthController<TUser extends AbstractUser, TUserLoginInfo extends UserLoginInfoOutput> {

    @Autowired
    private AutumnSession session;

    @Autowired
    private AutumnCredentialsMatcher credentialsMatcher;

    private final AuthAppServiceBase<TUser, TUserLoginInfo> service;

    /**
     * 实例化
     *
     * @param service
     */
    public AuthController(AuthAppServiceBase<TUser, TUserLoginInfo> service) {
        this.service = service;
    }

    /**
     * 传输加密
     *
     * @return
     */
    @ExcludeTokenAutoLogin
    @PostMapping(path = "/transfer/encrypt")
    @ApiOperation(value = "传输加密")
    public AutumnAuthProperties.TransferEncrypt transferEncrypt() {
        return this.credentialsMatcher.transferEncrypt();
    }

    /**
     * 用户名与密码登录
     *
     * @param input 输入
     * @return
     */
    @ExcludeTokenAutoLogin
    @PostMapping(path = "/login")
    @ApiOperation(value = "用户名与密码登录")
    public TUserLoginInfo login(@Valid @RequestBody UserNamePasswordLoingInput input) {
        if (input == null) {
            input = new UserNamePasswordLoingInput();
        }
        return service.login(input);
    }

    /**
     * 注销
     */
    @ExcludeTokenAutoLogin
    @RequestMapping(path = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "注销")
    public void logout() {
        service.logout();
    }

    /**
     * 查询登录状态
     *
     * @return
     */
    @RequestMapping(path = "/login/status", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "查询登录状态")
    public UserLoginStatusOutput<TUserLoginInfo> loginStatus() {
        UserLoginStatusOutput<TUserLoginInfo> result = new UserLoginStatusOutput<>();
        result.setLogin(false);
        if (session.isAuthenticated()) {
            result.setLogin(true);
            result.setLoginInfo(this.service.loginInfo());
        }
        return result;
    }

    /**
     * 登录请求，确认是否需要图形验证码。
     *
     * @return
     */
    @RequestMapping(path = "/login/request", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "登录请求，确认是否需要图形验证码和会话相息。")
    public UserLoginRequestOutput loginRequest() {
        return service.loginRequest();
    }

    /**
     * 登录信息
     *
     * @return
     */
    @RequestMapping(path = "/login/info", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "获取用户信息")
    @RequiresUser
    public TUserLoginInfo loginInfo() {
        return service.loginInfo();
    }

    /**
     * 更新密码
     *
     * @param input
     */
    @PostMapping("/update/password")
    @ApiOperation(value = "更新密码")
    @RequiresUser
    public void updatePassword(@Valid @RequestBody UpdatePasswordInput input) {
        this.service.updatePassword(input);
    }

    /**
     * 查询用户菜单树
     *
     * @return
     */
    @PostMapping("/query/user/menu/tree")
    @ApiOperation(value = "查询用户菜单树")
    @RequiresUser
    public List<ResourcesModuleTreeValue> queryUserMenuTree() {
        return this.service.queryUserMenuTree();
    }

}
