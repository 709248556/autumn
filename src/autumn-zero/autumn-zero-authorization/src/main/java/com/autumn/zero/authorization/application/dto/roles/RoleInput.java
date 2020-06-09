package com.autumn.zero.authorization.application.dto.roles;

import com.autumn.exception.ExceptionUtils;
import com.autumn.security.constants.RoleStatusConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色输入
 *
 * @author 老码农 2018-12-10 13:12:08
 */
@ToString(callSuper = true)
public class RoleInput extends RoleDto {

    /**
     *
     */
    private static final long serialVersionUID = 2844292377192605026L;

    @ApiModelProperty(value = "用户集合")
    private List<RoleUserInput> users;

    /**
     *
     */
    public RoleInput() {
        this.setUsers(new ArrayList<>());
    }

    /**
     * 获取用户集合
     *
     * @return
     */
    public List<RoleUserInput> getUsers() {
        return users;
    }

    /**
     * 设置用户集合
     *
     * @param users 用户集合
     */
    public void setUsers(List<RoleUserInput> users) {
        this.users = users;
    }

    @Override
    public void valid() {
        super.valid();
        if (this.getUsers() == null) {
            this.setUsers(new ArrayList<>());
        }
        if (this.getStatus() == null) {
            this.setStatus(RoleStatusConstants.STATUS_ENABLE);
        } else {
            if (!RoleStatusConstants.exist(this.getStatus())) {
                ExceptionUtils.throwValidationException("指定的状态无效或不支持。");
            }
        }
        Set<Long> userSet = new HashSet<>();
        for (RoleUserInput user : this.getUsers()) {
            user.valid();
            if (!userSet.add(user.getUserId())) {
                ExceptionUtils.throwValidationException("同一角色用户不能重复。");
            }
        }
    }

}
