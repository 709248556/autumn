package com.autumn.zero.authorization.services.impl;

import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.zero.authorization.entities.defaulted.DefaultRole;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;
import com.autumn.zero.authorization.entities.defaulted.query.DefaultRoleByUserQuery;
import com.autumn.zero.authorization.entities.defaulted.query.DefaultUserByRoleQuery;
import com.autumn.zero.authorization.repositories.defaulted.DefaultRoleRepository;
import com.autumn.zero.authorization.repositories.defaulted.DefaultUserRepository;
import com.autumn.zero.authorization.repositories.defaulted.query.RoleByUserQueryRepository;
import com.autumn.zero.authorization.repositories.defaulted.query.UserByRoleQueryRepository;
import com.autumn.zero.authorization.services.DefaultAdministratorDefinition;
import com.autumn.zero.authorization.services.DefaultAuthorizationService;
import com.autumn.zero.authorization.services.UserRoleDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 默认授权
 *
 * @author 老码农 2018-11-30 11:15:30
 */
public class DefaultAuthorizationServiceImpl extends AbstractAuthorizationService<DefaultUser, DefaultUserRepository, DefaultRole, DefaultRoleRepository>
        implements DefaultAuthorizationService {

    /**
     *
     */
    public DefaultAuthorizationServiceImpl() {
        super(DefaultUser.class, DefaultRole.class);
    }

    @Autowired
    private DefaultUserRepository defaultUserRepository;

    @Autowired
    private DefaultRoleRepository defaultRoleRepository;

    @Autowired
    private RoleByUserQueryRepository roleUserQueryRepository;

    @Autowired
    private UserByRoleQueryRepository userRoleQueryRepository;

    @Override
    protected final DefaultUserRepository getUserRepository() {
        return this.defaultUserRepository;
    }

    @Override
    protected final DefaultRoleRepository getRoleRepository() {
        return this.defaultRoleRepository;
    }

    @Override
    public List<DefaultRole> queryUserByRoles(long userId) {
        QueryWrapper query = new QueryWrapper(DefaultRoleByUserQuery.class);
        query.where().eq(DefaultRoleByUserQuery.FIELD_USER_ID, userId).of().orderBy(DefaultRoleByUserQuery.FIELD_SORT_ID)
                .orderBy(DefaultRoleByUserQuery.FIELD_ID);
        List<DefaultRoleByUserQuery> queryRoles = roleUserQueryRepository.selectForList(query);
        return AutoMapUtils.mapForList(queryRoles, DefaultRole.class);
    }

    @Override
    public List<DefaultUser> queryRoleByUsers(long roleId) {
        QueryWrapper query = new QueryWrapper(DefaultUserByRoleQuery.class);
        query.where().eq(DefaultUserByRoleQuery.FIELD_ROLE_ID, roleId).of().orderBy(DefaultUserByRoleQuery.FIELD_ID);
        List<DefaultUserByRoleQuery> queryUoles = userRoleQueryRepository.selectForList(query);
        return AutoMapUtils.mapForList(queryUoles, DefaultUser.class);
    }

    @Override
    public UserRoleDefinition<DefaultUser, DefaultRole> createAdministratorDefinition() {
        return new DefaultAdministratorDefinition();
    }

}
