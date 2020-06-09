package com.autumn.zero.authorization.application.dto.modules;

import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 资源类型输入
 * 
 * @author 老码农 2018-12-10 01:28:16
 */
@ToString(callSuper = true)
public class ResourcesTypeInput implements Serializable, DataValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7660731230508662192L;

	/**
	 * 资源类型
	 */
	@ApiModelProperty(value = "资源类型")
	@NotNull(message = "资源类型不能为空")
	private Integer resourcesType;

	/**
	 * 获取资源类型
	 * 
	 * @return
	 */
	public Integer getResourcesType() {
		return resourcesType;
	}

	/**
	 * 设置资源类型
	 * 
	 * @param resourcesType
	 *            资源类型
	 */
	public void setResourcesType(Integer resourcesType) {
		this.resourcesType = resourcesType;
	}

	@Override
	public void valid() {
		ValidationUtils.validation(this);
	}

}
