package com.autumn.zero.authorization.application.dto;

import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.AbstractPermission;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 权限资源 Dto
 * 
 * @author 老码农 2018-12-11 00:52:51
 */
@ToString(callSuper = true)
public class PermissionResourcesModuleDto implements Serializable, DataValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5544955508341263300L;

	@ApiModelProperty(value = "资源id")
	@NotNullOrBlank(message = "资源id不能为空")
	@Length(max = ResourcesModule.MAX_ID_LENGTH, message = "资源id不能超过" + ResourcesModule.MAX_ID_LENGTH + "个字。")
	private String resourcesId;

	/**
	 * 权限名称
	 */
	@ApiModelProperty(value = "权限名称")
	@Length(max = AbstractPermission.MAX_NAME_LENGTH, message = "权限名称长度不能超过" + AbstractPermission.MAX_NAME_LENGTH
			+ "个字。")
	private String permissionName;

	/**
	 * 是否授权
	 */
	@ApiModelProperty(value = "是否授权")
	private boolean isGranted;

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
	 * @param resourcesId 资源id
	 */
	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}

	/**
	 * 获取授权名称
	 * 
	 * @return
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * 设置权限名称
	 * 
	 * @param permissionName 权限名称
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * 获取是否授权
	 * 
	 * @return
	 */
	public boolean getIsGranted() {
		return isGranted;
	}

	/**
	 * 设置是否授权
	 * 
	 * @param isGranted 是否授权
	 */
	public void setIsGranted(boolean isGranted) {
		this.isGranted = isGranted;
	}

	@Override
	public void valid() {
		ValidationUtils.validation(this);
	}

}
