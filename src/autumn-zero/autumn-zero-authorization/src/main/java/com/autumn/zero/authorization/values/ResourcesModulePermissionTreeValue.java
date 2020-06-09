package com.autumn.zero.authorization.values;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.domain.values.AbstractValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源模块权限值类型
 *
 * @author 老码农 2018-12-11 10:23:37
 */
public class ResourcesModulePermissionTreeValue extends AbstractValue {

    /**
     *
     */
    private static final long serialVersionUID = -7442605135948070597L;

    /**
     * 资源id
     */
    @ApiModelProperty(value = "资源id")
    @JSONField(ordinal = 1)
    private String resourcesId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @JSONField(ordinal = 2)
    private String name;

    /**
     * 自定义名称
     */
    @ApiModelProperty(value = "自定义名称")
    @JSONField(ordinal = 3)
    private String customName;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    @JSONField(ordinal = 4)
    private String icon;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    @JSONField(ordinal = 5)
    private String permissionName;

    /**
     * 是否授权
     */
    @ApiModelProperty(value = "是否授权")
    @JSONField(ordinal = 6)
    private boolean isGranted;

    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    @JSONField(ordinal = 100)
    private String summary;

    /**
     * 子级集合
     */
    @ApiModelProperty(value = "子级集合")
    @JSONField(ordinal = 1000)
    private List<ResourcesModulePermissionTreeValue> children;

    /**
     * 操作权限集合
     */
    @ApiModelProperty(value = "操作权限集合")
    @JSONField(ordinal = 1001)
    private List<ResourcesModuleOperationPermissionValue> operationPermissions;

    /**
     * 实例化 ResourcesModulePermissionTreeValue 类新实例
     */
    public ResourcesModulePermissionTreeValue() {
        // 不可初始化children ，影响前端处理

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
     */
    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
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
     * 获取自定义名称
     *
     * @return
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * 设置自定义名称
     *
     * @param customName 自定义名称
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    /**
     * 获取图标
     *
     * @return
     */
    public String getIcon() {
        return this.icon;
    }

    /**
     * 设置图标
     *
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

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
     * @param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取子级集合
     *
     * @return
     */
    public List<ResourcesModulePermissionTreeValue> getChildren() {
        return children;
    }

    /**
     * 设置子级集合
     *
     * @param childs 子级集合
     */
    public void setChildren(List<ResourcesModulePermissionTreeValue> children) {
        this.children = children;
    }

    /**
     * 获取操作权限集合
     *
     * @return
     */
    public List<ResourcesModuleOperationPermissionValue> getOperationPermissions() {
        return operationPermissions;
    }

    /**
     * 设置操作权限集合
     *
     * @param operationPermissions
     */
    public void setOperationPermissions(List<ResourcesModuleOperationPermissionValue> operationPermissions) {
        this.operationPermissions = operationPermissions;
    }

    /**
     * 子级空值初始化
     */
    public void childrenNullValueInit() {
        if (this.getChildren() == null) {
            this.setChildren(new ArrayList<>(16));
        }
    }
}
