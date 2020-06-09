package com.autumn.zero.authorization.services;

import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.security.constants.UserStatusConstants;
import com.autumn.zero.authorization.application.services.callback.impl.DefaultAuthCallback;
import com.autumn.zero.authorization.entities.defaulted.DefaultRole;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;

import java.util.Date;

/**
 * 默认超级管理员配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 00:30
 **/
public class DefaultAdministratorDefinition implements UserRoleDefinition<DefaultUser, DefaultRole> {

    @Override
    public DefaultUser createUser() {
        DefaultUser user = new DefaultUser();
        user.setUserName("admin");
        user.setRealName("系统管理员");
        user.setNickName("");
        user.setPassword("admin");
        user.setSex("");
        user.setGmtCreate(new Date());
        user.setIsSysUser(true);
        user.setStatus(UserStatusConstants.NORMAL);
        return user;
    }

    @Override
    public DefaultRole createRole() {
        DefaultRole role = new DefaultRole();
        role.setIsSysRole(true);
        role.setSortId(1);
        role.setGmtCreate(new Date());
        role.setSummary("具有系统所有权限");
        role.setName("administrators");
        return role;
    }

    @Override
    public void setRoleRelationCondition(EntityQueryWrapper<DefaultRole> wrapper) {

    }

    @Override
    public int getModuleResourcesType() {
        return DefaultAuthCallback.DEFAULT_MODULE_RESOURCES_TYPE;
    }
}
