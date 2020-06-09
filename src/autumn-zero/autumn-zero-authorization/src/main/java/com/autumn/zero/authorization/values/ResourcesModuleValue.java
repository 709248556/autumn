package com.autumn.zero.authorization.values;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.validation.DataValidation;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.values.AbstractValue;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;
import com.autumn.validation.ValidationUtils;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 
 * 资源基本值
 * 
 * @author 老码农 2018-12-10 10:42:56
 */
@ExcelWorkSheet(exportTitle = "资源模块")
public class ResourcesModuleValue extends AbstractValue implements DataValidation, Entity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2250705732960931127L;

	@ApiModelProperty(value = "资源id")
	@NotNullOrBlank(message = "id不能为空")
	@Length(max = ResourcesModule.MAX_ID_LENGTH, message = "id长度不能超过" + ResourcesModule.MAX_ID_LENGTH + "个字。")
	@JSONField(ordinal = 1)
	@ExcelColumn(order = 1, friendlyName = "资源id", width = 80)
	private String id;

	@ApiModelProperty(value = "名称")
	@NotNullOrBlank(message = "名称不能为空")
	@Length(max = ResourcesModule.MAX_NAME_LENGTH, message = "名称长度不能超过" + ResourcesModule.MAX_NAME_LENGTH + "个字。")
	@JSONField(ordinal = 2)
	@ExcelColumn(order = 2, friendlyName = "资源名称", width = 80)
	private String name;

	@ApiModelProperty(value = "自定义名称")
	@NotNullOrBlank(message = "自定义资源名称不能为空")
	@Length(max = ResourcesModule.MAX_NAME_LENGTH, message = "自定义资源名称长度不能超过" + ResourcesModule.MAX_NAME_LENGTH + "个字。")
	@JSONField(ordinal = 3)
	@ExcelColumn(order = 3, friendlyName = "资源自定义名称", width = 80)
	private String customName;

	@ApiModelProperty(value = "父级id")
	@Length(max = ResourcesModule.MAX_ID_LENGTH, message = "父级Id长度不能超过" + ResourcesModule.MAX_ID_LENGTH + "个字。")
	@JSONField(ordinal = 4)
	private String parentId;

	@ApiModelProperty(value = "资源类型")
	@NotNull(message = "资源类型不能为空")
	@JSONField(ordinal = 10)
	@ExcelColumn(order = 10, friendlyName = "资源类型", width = 60)
	private Integer resourcesType;

	@ApiModelProperty(value = "顺序")
	@NotNull(message = "顺序不能为空")
	@Min(value = 1, message = "顺序最小值必须大于零")
	@JSONField(ordinal = 11)
	@ExcelColumn(order = 11, friendlyName = "显示顺序", width = 60)
	private Integer sortId;

	@ApiModelProperty(value = "是否需要授权")
	@JSONField(ordinal = 12)
	@ExcelColumn(order = 12, friendlyName = "需要授权", width = 60)
	private boolean isAuthorize;

	@ApiModelProperty(value = "是否属于菜单")
	@JSONField(ordinal = 13)
	@ExcelColumn(order = 13, friendlyName = "属于菜单", width = 60)
	private boolean isMenu;

	@ApiModelProperty(value = "是否系统模块")
	@JSONField(ordinal = 14)
	@ExcelColumn(order = 14, friendlyName = "系统模块", width = 60)
	private boolean isSysModule;

	@ApiModelProperty(value = "标识")
	@Length(max = ResourcesModule.MAX_IDENTIFICATION_LENGTH, message = "标识长度不能超过" + ResourcesModule.MAX_IDENTIFICATION_LENGTH + "个字。")
	@JSONField(ordinal = 14)
	@ExcelColumn(order = 14, friendlyName = "标识", width = 100)
	private String identification;

	@ApiModelProperty(value = "权限Url")
	@Length(max = ResourcesModule.MAX_URL_LENGTH, message = "权限Url长度不能超过" + ResourcesModule.MAX_URL_LENGTH + "个字。")
	@JSONField(ordinal = 15)
	@ExcelColumn(order = 15, friendlyName = "权限Url地址", width = 200)
	private String permissionUrl;

	@ApiModelProperty(value = "Url")
	@Length(max = ResourcesModule.MAX_URL_LENGTH, message = "Url长度不能超过" + ResourcesModule.MAX_URL_LENGTH + "个字。")
	@JSONField(ordinal = 16)
	@ExcelColumn(order = 16, friendlyName = "Url地址", width = 200)
	private String url;

	@ApiModelProperty(value = "图标")
	@Length(max = ResourcesModule.MAX_ICON_LENGTH, message = "icon长度不能超过" + ResourcesModule.MAX_ICON_LENGTH + "个字。")
	@JSONField(ordinal = 17)
	@ExcelColumn(order = 17, friendlyName = "图标", width = 80)
	private String icon;

	@ApiModelProperty(value = "摘要")
	@Length(max = ResourcesModule.MAX_SUMMARY_LENGTH, message = "摘要长度不能超过" + ResourcesModule.MAX_SUMMARY_LENGTH + "个字。")
	@JSONField(ordinal = 18)
	@ExcelColumn(order = 18, friendlyName = "摘要", width = 250)
	private String summary;

	/**
	 * 实例化
	 */
	public ResourcesModuleValue() {

	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getResourcesType() {
		return resourcesType;
	}

	public void setResourcesType(Integer resourcesType) {
		this.resourcesType = resourcesType;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public boolean getIsAuthorize() {
		return isAuthorize;
	}

	public void setIsAuthorize(boolean isAuthorize) {
		this.isAuthorize = isAuthorize;
	}

	public boolean getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(boolean isMenu) {
		this.isMenu = isMenu;
	}

	/**
	 * 获取是否是系统模块
	 * 
	 * @return
	 */
	public boolean getIsSysModule() {
		return isSysModule;
	}

	/**
	 * 设置是否是系统模块
	 * 
	 * @param isSysModule
	 */
	public void setIsSysModule(boolean isSysModule) {
		this.isSysModule = isSysModule;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public void valid() {
		ValidationUtils.validation(this);
	}
}
