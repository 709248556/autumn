package com.autumn.zero.authorization.application.services.auth.impl;

import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.services.auth.DefaultAuthAppService;
import com.autumn.zero.authorization.application.services.callback.AuthCallback;
import com.autumn.zero.authorization.application.services.captcha.ImageCaptchaAppService;
import com.autumn.zero.authorization.entities.defaulted.DefaultRole;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;

import java.util.List;

/**
 * 默认授权服务
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 11:58
 **/
public class DefaultAuthAppServiceImpl extends AbstractAuthAppServiceBase<DefaultUser, DefaultRole, UserLoginInfoOutput> implements DefaultAuthAppService {


    /**
     * @param authService
     * @param authCallback
     * @param captchaAppService
     */
    public DefaultAuthAppServiceImpl(AuthorizationServiceBase<DefaultUser, DefaultRole> authService,
                                     AuthCallback authCallback,
                                     ImageCaptchaAppService captchaAppService) {
        super(authService, authCallback, captchaAppService);
    }

    @Override
    public DefaultUser createRegisterUser() {
        return new DefaultUser();
    }

    @Override
    protected UserLoginInfoOutput createUserLoginInfo() {
        return new UserLoginInfoOutput();
    }

    @Override
    public List<ResourcesModuleTreeValue> queryUserMenuTree() {
        return authService.queryUserByMenuTree(this.authCallback.moduleResourcesType(this.getSession()), this.getSession().getUserId());
    }

    @Override
    public String getModuleName() {
        return "用户授权";
    }
}
