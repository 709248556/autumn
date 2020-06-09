package com.autumn.zero.authorization.application.services.auth;

import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;

/**
 * 默认用户授权服务
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 12:05
 **/
public interface DefaultAuthAppService extends AuthAppServiceBase<DefaultUser, UserLoginInfoOutput> {

}
