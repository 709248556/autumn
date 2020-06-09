package com.autumn.zero.authorization.application.dto.modules;

import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 资源输入
 * 
 * @author 老码农 2018-12-09 17:22:44
 */
@ToString(callSuper = true)
public class ResourcesInput implements Serializable, DataValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3432729697918921037L;

	@ApiModelProperty(value = "资源id")
	@NotNullOrBlank(message = "资源id不能为空")
	@Length(max = ResourcesModule.MAX_ID_LENGTH, message = "资源id不能超过" + ResourcesModule.MAX_ID_LENGTH + "个字。")
	private String resourcesId;

	/**
	 * 
	 */
	public ResourcesInput() {

	}

	/**
	 * 
	 * @param resourcesId
	 */
	public ResourcesInput(String resourcesId) {
		super();
		this.resourcesId = resourcesId;
	}

	/**
	 * 获取资源id
	 * 
	 * @return
	 */
	public String getResourcesId() {
		return resourcesId;
	}

	/**
	 * 设置资源id
	 * 
	 * @param resourcesId
	 *            资源id
	 */
	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}

	@Override
	public void valid() {
		ValidationUtils.validation(this);
	}
}
