package com.autumn.util.automap.model;

import com.autumn.validation.annotation.NotNullOrBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户信息
 * 
 * @author 老码农
 *
 *         2017-11-15 16:06:35
 */
public class UserInfo {

	@Min(value = 1L,message = "用户id不能小于1")
	private int userId;
	@NotNullOrBlank(message = "用户名称不能为空")
	private String userName;
	@NotNull(message = "至少需要一个角色")
	private List<RoleInfo> roles;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleInfo> roles) {
		this.roles = roles;
	}

}
