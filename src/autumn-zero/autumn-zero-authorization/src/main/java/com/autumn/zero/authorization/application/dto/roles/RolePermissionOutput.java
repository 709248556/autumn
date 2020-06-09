package com.autumn.zero.authorization.application.dto.roles;

import com.autumn.domain.entities.auditing.gmt.GmtCreateAuditing;
import com.autumn.domain.entities.auditing.gmt.GmtModifiedAuditing;
import com.autumn.zero.authorization.application.dto.PermissionResourcesModuleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色权限输出
 * 
 * @author 老码农 2018-12-11 01:16:45
 */
@ToString(callSuper = true)
public class RolePermissionOutput extends RoleDto implements GmtCreateAuditing, GmtModifiedAuditing {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7530825680502818831L;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date gmtModified;
	
	/**
	 * 权限集合
	 */
	@ApiModelProperty(value = "权限集合")
	private List<PermissionResourcesModuleDto> permissions;

	/**
	 * 实例化
	 */
	public RolePermissionOutput() {
		this.setPermissions(new ArrayList<>());
	}
	
	@Override
	public Date getGmtCreate() {
		return gmtCreate;
	}

	@Override
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public Date getGmtModified() {
		return gmtModified;
	}

	@Override
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
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
