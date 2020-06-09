package com.autumn.zero.authorization.application.dto.users;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 重置密码输入
 * 
 * @author 老码农 2019-03-08 09:06:26
 */
@ToString(callSuper = true)
public class ResetPasswordInput extends DefaultEntityDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8707362053241346435L;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户id不能为空")
	private Long id;

	@ApiModelProperty(value = "新密码")
	@NotNullOrBlank(message = "新密码不能为空")
	private String newPassword;

	@NotNull(message = "用户id不能为空")
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取新密码
	 * 
	 * @return
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * 设置新密码
	 * 
	 * @param newPassword
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
