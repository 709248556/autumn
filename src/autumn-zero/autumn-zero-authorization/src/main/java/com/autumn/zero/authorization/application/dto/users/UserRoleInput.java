package com.autumn.zero.authorization.application.dto.users;

import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 
 * 用户角色输入
 * 
 * @author 老码农 2018-12-10 15:11:24
 */
@ToString(callSuper = true)
public class UserRoleInput implements Serializable, DataValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8390130031186603921L;

	@ApiModelProperty(value = "角色id")
	@NotNull(message = "角色id不能为空")
	private Long roleId;

	/**
	 * 获取角色id
	 * 
	 * @return
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * 设置角色id
	 * 
	 * @param roleId 角色id
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public void valid() {
		ValidationUtils.validation(this);
	}

}
