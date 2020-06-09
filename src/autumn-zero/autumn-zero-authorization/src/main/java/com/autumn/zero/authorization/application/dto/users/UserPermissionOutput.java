package com.autumn.zero.authorization.application.dto.users;

import com.autumn.zero.authorization.application.dto.PermissionResourcesModuleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户权限输出
 * 
 * @author 老码农 2018-12-11 01:14:53
 */
@ToString(callSuper = true)
public class UserPermissionOutput extends UserOutput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7936058706634769155L;

	/**
	 * 权限集合
	 */
	@ApiModelProperty(value = "权限集合")
	private List<PermissionResourcesModuleDto> permissions;
	
	/**
	 * 实例化
	 */
	public UserPermissionOutput() {
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
	 * @param permissions
	 *            权限集合
	 */
	public void setPermissions(List<PermissionResourcesModuleDto> permissions) {
		this.permissions = permissions;
	}
}
