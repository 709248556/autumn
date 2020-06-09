package com.autumn.zero.authorization.application.dto.roles;

import com.autumn.zero.authorization.application.dto.PermissionDto;
import com.autumn.zero.authorization.application.dto.PermissionResourcesModuleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限输入
 * 
 * @author 老码农 2018-12-11 01:03:42
 */
@ToString(callSuper = true)
public class RolePermissionInput extends PermissionDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9212751128120847807L;

	/**
	 * 权限集合
	 */
	@ApiModelProperty(value = "权限集合")
	private List<PermissionResourcesModuleDto> permissions;

	/**
	 * 实例化
	 */
	public RolePermissionInput() {
		this.setPermissions(new ArrayList<>());
	}

	/**
	 * 获取权限集合
	 * 
	 * @return
	 */
	public List<PermissionResourcesModuleDto> getPermissions() {
		return permissions;
	}

	/**
	 * 设置权限集合
	 * 
	 * @param permissions 权限集合
	 */
	public void setPermissions(List<PermissionResourcesModuleDto> permissions) {
		this.permissions = permissions;
	}

	/**
	 * 
	 */
	@Override
	public void valid() {
		super.valid();
		if (this.getPermissions() == null) {
			this.setPermissions(new ArrayList<>());
		}
		for (PermissionResourcesModuleDto permissionDto : this.getPermissions()) {
			permissionDto.valid();
		}
	}

}
