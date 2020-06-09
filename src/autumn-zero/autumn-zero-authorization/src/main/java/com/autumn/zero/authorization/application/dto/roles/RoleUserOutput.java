package com.autumn.zero.authorization.application.dto.roles;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * 角色用户输出
 * 
 * @author 老码农 2018-12-10 13:16:34
 */
@ToString(callSuper = true)
public class RoleUserOutput extends RoleUserInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6555619697946391576L;

	@ApiModelProperty(value = "用户名称")
	private String userName;

	/**
	 * 获取用户名称
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置用户名称
	 * 
	 * @param userName 用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
