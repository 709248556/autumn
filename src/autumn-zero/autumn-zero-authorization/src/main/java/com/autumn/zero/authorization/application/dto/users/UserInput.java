package com.autumn.zero.authorization.application.dto.users;

import com.autumn.exception.ExceptionUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * 用户输入
 * 
 * @author 老码农 2018-12-10 15:05:20
 */
@ToString(callSuper = true)
public class UserInput extends UserDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7974248624411728455L;

	@ApiModelProperty(value = "角色集合")
	private List<UserRoleInput> roles;

	/**
	 * 
	 */
	public UserInput() {
		this.setRoles(new ArrayList<>());
	}

	/**
	 * 获取角色集合
	 * 
	 * @return
	 */
	public List<UserRoleInput> getRoles() {
		return roles;
	}

	/**
	 * 设置角色集合
	 * 
	 * @param roles 角色集合
	 */
	public void setRoles(List<UserRoleInput> roles) {
		this.roles = roles;
	}

	@Override
	public void valid() {
		super.valid();
		if (this.getRoles() == null) {
			this.setRoles(new ArrayList<>());
		}
		Set<Long> roleSet = new HashSet<>();
		for (UserRoleInput role : this.getRoles()) {
			role.valid();
			if (!roleSet.add(role.getRoleId())) {
				ExceptionUtils.throwValidationException("同一用户的角色不能重复。");
			}
		}
	}
}
