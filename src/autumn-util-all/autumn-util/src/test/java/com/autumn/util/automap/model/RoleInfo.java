package com.autumn.util.automap.model;

import com.autumn.validation.annotation.NotNullOrBlank;

import javax.validation.constraints.Min;

/**
 * 角色信息
 * 
 * @author 老码农
 *
 *         2017-11-15 16:07:19
 */
public class RoleInfo {

	@Min(value = 1L,message = "角色id不能小于1")
	private int roleId;
	@NotNullOrBlank(message = "角色名称不能为空")
	private String roleName;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



}
