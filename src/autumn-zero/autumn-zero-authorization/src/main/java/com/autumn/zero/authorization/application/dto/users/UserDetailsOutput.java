package com.autumn.zero.authorization.application.dto.users;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户详情输出
 * 
 * @author 老码农 2018-12-10 15:05:31
 */
@ToString(callSuper = true)
public class UserDetailsOutput extends UserOutput {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4323569724429190812L;

	@ApiModelProperty(value = "角色集合")
	private List<UserRoleOutput> roles;

	/**
	 * 
	 */
	public UserDetailsOutput() {
		this.setRoles(new ArrayList<>());
	}

	/**
	 * 获取角色集合
	 * 
	 * @return
	 */
	public List<UserRoleOutput> getRoles() {
		return roles;
	}

	/**
	 * 设置角色集合
	 * 
	 * @param roles 角色集合
	 */
	public void setRoles(List<UserRoleOutput> roles) {
		this.roles = roles;
	}
}
