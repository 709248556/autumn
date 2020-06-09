package com.autumn.zero.authorization.values;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.domain.values.AbstractValue;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源模块操作权限
 * 
 * @author 老码农 2018-12-17 11:49:22
 */
public class ResourcesModuleOperationPermissionValue extends AbstractValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4610267966662807267L;

	/**
	 * 权限名称
	 */
	@ApiModelProperty(value = "权限名称")
	@JSONField(ordinal = 1)
	private String permissionName;

	/**
	 * 友好名称
	 */
	@ApiModelProperty(value = "友好名称")
	@JSONField(ordinal = 2)
	private String friendlyName;

	/**
	 * 是否授权
	 */
	@ApiModelProperty(value = "是否授权")
	@JSONField(ordinal = 3)
	private boolean isGranted;

	/**
	 * 摘要
	 */
	@ApiModelProperty(value = "摘要")
	@JSONField(ordinal = 4)
	private String summary;

	/**
	 * 获取权限名称
	 * 
	 * @return
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * 设置权限名称
	 * 
	 * @param permissionName
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * 获取友好名称
	 * 
	 * @return
	 */
	public String getFriendlyName() {
		return friendlyName;
	}

	/**
	 * 设置友好名称
	 * 
	 * @param friendlyName
	 */
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
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
	 * @param isGranted
	 */
	public void setIsGranted(boolean isGranted) {
		this.isGranted = isGranted;
	}

	/**
	 * 获取摘要
	 * 
	 * @return
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * 设置摘要
	 * 
	 * @param summary 摘要
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

}
