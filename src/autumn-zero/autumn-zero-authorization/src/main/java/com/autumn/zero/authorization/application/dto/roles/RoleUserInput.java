package com.autumn.zero.authorization.application.dto.roles;

import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色用户输入
 * 
 * @author 老码农 2018-12-10 13:09:53
 */
@ToString(callSuper = true)
public class RoleUserInput implements Serializable, DataValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6988385228267028258L;

	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户id不能为空")
	private Long userId;

	/**
	 * 获取用户
	 * 
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public void valid() {
		ValidationUtils.validation(this);
	}
}
