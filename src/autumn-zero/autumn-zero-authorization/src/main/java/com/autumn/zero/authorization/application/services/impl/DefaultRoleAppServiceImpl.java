package com.autumn.zero.authorization.application.services.impl;

import com.autumn.zero.authorization.application.dto.roles.RoleDto;
import com.autumn.zero.authorization.application.dto.roles.RoleInput;
import com.autumn.zero.authorization.application.dto.roles.RoleOutput;
import com.autumn.zero.authorization.application.services.DefaultRoleAppService;
import com.autumn.zero.authorization.entities.defaulted.DefaultRole;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;
import com.autumn.zero.authorization.repositories.defaulted.DefaultRoleRepository;

/**
 * 默认角色应用服务
 *
 * @author 老码农 2018-12-10 15:57:40
 */
public class DefaultRoleAppServiceImpl extends AbstractRoleAppService<DefaultRole, DefaultUser, DefaultRoleRepository, RoleInput, RoleInput, RoleDto, RoleOutput>
        implements DefaultRoleAppService {

    public DefaultRoleAppServiceImpl() {
        super(DefaultRole.class, RoleDto.class, RoleOutput.class);
    }
}
