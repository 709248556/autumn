package com.autumn.zero.authorization.application.dto.modules;

import com.autumn.validation.DataValidation;
import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;
import com.autumn.validation.ValidationUtils;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModulePermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 资源模块权限
 * 
 * @author 老码农 2018-12-09 13:19:24
 */
@ToString(callSuper = true)
@ExcelWorkSheet(exportTitle = "资源模块权限")
public class ResourcesModulePermissionDto extends DefaultEntityDto implements DataValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6451730103547891462L;

	@ApiModelProperty(value = "资源id")
	@NotNullOrBlank(message = "资源id不能为空")
	@Length(max = ResourcesModule.MAX_ID_LENGTH, message = "资源id不能超过" + ResourcesModule.MAX_ID_LENGTH + "个字。")
	@ExcelColumn(order = 1, friendlyName = "资源id", width = 80)
	private String resourcesId;

	/**
	 * 
	 */
	@ApiModelProperty(value = "顺序")
	@NotNull(message = "顺序不能为空")
	@ExcelColumn(order = 2, friendlyName = "显示顺序", width = 60)
	private Integer sortId;

	/**
	 * 
	 */
	@ApiModelProperty(value = "权限名称")
	@NotNullOrBlank(message = "权限名称不能为空")
	@Length(max = ResourcesModulePermission.MAX_NAME_LENGTH, message = "权限名称长度不能超过"
			+ ResourcesModulePermission.MAX_NAME_LENGTH + "个字。")
	@ExcelColumn(order = 3, friendlyName = "权限名称", width = 80)
	private String name;

	/**
	 * 
	 */
	@ApiModelProperty(value = "友好名称")
	@NotNullOrBlank(message = "友好名称不能为空")
	@Length(max = ResourcesModulePermission.MAX_FRIENDLY_NAME_LENGTH, message = "友好长度不能超过"
			+ ResourcesModulePermission.MAX_FRIENDLY_NAME_LENGTH + "个字。")
	@ExcelColumn(order = 4, friendlyName = "权限名称友好名称", width = 120)
	private String friendlyName;

	/**
	 * 权限Url
	 */
	@ApiModelProperty(value = "权限Url")
	@ExcelColumn(order = 5, friendlyName = "权限Url地址", width = 200)
	private String permissionUrl;

	/**
	 * 
	 */
	@ApiModelProperty(value = "摘要")
	@Length(max = ResourcesModulePermission.MAX_SUMMARY_LENGTH, message = "摘要长度不能超过"
			+ ResourcesModulePermission.MAX_SUMMARY_LENGTH + "个字。")
	@ExcelColumn(order = 6, friendlyName = "摘要", width = 300)
	private String summary;

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
	 * 获取顺序
	 * 
	 * @return
	 */
	public Integer getSortId() {
		return sortId;
	}

	/**
	 * 设置顺序
	 * 
	 * @param sortId
	 */
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 获取友好名称
	 * 
	 * @param friendlyName 友好名称
	 */
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	/**
	 * 获取权限Url
	 * 
	 * @return
	 */
	public String getPermissionUrl() {
		return permissionUrl;
	}

	/**
	 * 设置权限Url
	 * 
	 * @param permissionUrl 权限Url
	 */
	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
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
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public void valid() {
		ValidationUtils.validation(this);
	}

}
