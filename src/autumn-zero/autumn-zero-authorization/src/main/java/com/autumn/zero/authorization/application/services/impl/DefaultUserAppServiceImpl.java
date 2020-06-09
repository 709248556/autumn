package com.autumn.zero.authorization.application.services.impl;

import com.autumn.zero.authorization.application.dto.users.UserAddInput;
import com.autumn.zero.authorization.application.dto.users.UserDetailsOutput;
import com.autumn.zero.authorization.application.dto.users.UserInput;
import com.autumn.zero.authorization.application.dto.users.UserOutput;
import com.autumn.zero.authorization.application.services.DefaultUserAppService;
import com.autumn.zero.authorization.entities.defaulted.DefaultRole;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;
import com.autumn.zero.authorization.repositories.defaulted.DefaultUserRepository;

/**
 * 默认用户应用服务
 *
 * @author 老码农 2018-12-10 15:58:04
 */
public class DefaultUserAppServiceImpl extends AbstractUserAppService<DefaultUser, DefaultRole, DefaultUserRepository, UserAddInput, UserInput, UserOutput, UserDetailsOutput>
        implements DefaultUserAppService {

    public DefaultUserAppServiceImpl() {
        super(DefaultUser.class, UserOutput.class, UserDetailsOutput.class);
    }
}
