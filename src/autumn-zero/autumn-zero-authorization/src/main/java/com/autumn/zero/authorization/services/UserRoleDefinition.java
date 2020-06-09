package com.autumn.zero.authorization.services;

import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;

/**
 * 用户角色定义信息
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 00:25
 **/
public interface UserRoleDefinition<TUser extends AbstractUser, TRole extends AbstractRole> {

    /**
     * 创建用户
     *
     * @return
     */
    TUser createUser();

    /**
     * 创建角色
     *
     * @return
     */
    TRole createRole();

    /**
     * 设置角色关联条件
     *
     * @param wrapper
     */
    void setRoleRelationCondition(EntityQueryWrapper<TRole> wrapper);

    /**
     * 获取授权资源类型
     *
     * @return
     */
    int getModuleResourcesType();

}
