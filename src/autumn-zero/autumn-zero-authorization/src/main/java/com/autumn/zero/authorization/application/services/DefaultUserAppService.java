package com.autumn.zero.authorization.application.services;

import com.autumn.zero.authorization.application.dto.users.UserAddInput;
import com.autumn.zero.authorization.application.dto.users.UserDetailsOutput;
import com.autumn.zero.authorization.application.dto.users.UserInput;
import com.autumn.zero.authorization.application.dto.users.UserOutput;

/**
 * 默认用户应用服务
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 12:53
 **/
public interface DefaultUserAppService extends UserAppServiceBase<UserAddInput, UserInput, UserOutput, UserDetailsOutput> {
}
