package com.autumn.zero.authorization.application.dto.modules;

import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * 权限名称检查输入
 * 
 * @author 老码农 2018-12-12 10:17:22
 */
@ToString(callSuper = true)
public class PermissionNameCheckInput extends ResourcesInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4063323763197324968L;

	@ApiModelProperty(value = "权限名称")
	@NotNullOrBlank(message = "权限名称不能重复")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
