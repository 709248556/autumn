package com.autumn.zero.authorization.application.dto.users;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * 
 * 用户角色输出
 * 
 * @author 老码农 2018-12-10 15:10:46
 */
@ToString(callSuper = true)
public class UserRoleOutput extends UserRoleInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8077825901921876972L;

	/**
	 * 获取角色名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 获取角色名称
	 * 
	 * @return
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 设置角色名称
	 * 
	 * @param roleName 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
