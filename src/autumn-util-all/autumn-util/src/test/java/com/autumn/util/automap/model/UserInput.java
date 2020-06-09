package com.autumn.util.automap.model;

import com.autumn.validation.annotation.NotNullOrBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 老码农
 *
 *         2017-11-15 16:09:53
 */
public class UserInput {

	@Min(value = 1L,message = "用户id不能小于1")
	private int userId;

	@NotNullOrBlank(message = "用户名称不能为空")
	private String userName;

	@NotNull(message = "至少需要一个角色")
	private List<RoleInput> roles;

	public UserInput() {
		this.roles = new ArrayList<>();
	}

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

	public List<RoleInput> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleInput> roles) {
		this.roles = roles;
	}

}
