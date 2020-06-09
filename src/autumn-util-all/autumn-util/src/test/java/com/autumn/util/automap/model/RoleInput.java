package com.autumn.util.automap.model;

import com.autumn.validation.annotation.NotNullOrBlank;

/**
 * 角色输入
 * 
 * @author 老码农
 *
 *         2017-11-15 16:10:15
 */
public class RoleInput {

	@NotNullOrBlank(message = "角色名称不能为空")
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


}
