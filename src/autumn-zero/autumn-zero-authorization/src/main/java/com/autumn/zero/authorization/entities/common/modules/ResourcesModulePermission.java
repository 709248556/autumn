package com.autumn.zero.authorization.entities.common.modules;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.constants.SettingConstants;
import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.mybatis.mapper.annotation.*;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 资源模块权限
 *
 * @author 老码农 2018-12-05 12:48:46
 */
@ToString(callSuper = true)
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_res_module_permission")
@ComplexIndexs(indexs = {@ComplexIndex(propertys = {"resourcesId", "name"}, unique = true)})
@TableDocument(value = "资源模块权限", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class ResourcesModulePermission extends AbstractDefaultEntity {

    /**
     *
     */
    private static final long serialVersionUID = -4520513910043394913L;

    /**
     * 最大名称长度
     */
    public static final int MAX_NAME_LENGTH = 50;

    /**
     * 最大友好长度
     */
    public static final int MAX_FRIENDLY_NAME_LENGTH = 50;

    /**
     * 最大url长度
     */
    public static final int MAX_URL_LENGTH = 255;

    /**
     * 最大摘要长度
     */
    public static final int MAX_SUMMARY_LENGTH = 255;

    /**
     * 字段 resourcesId
     */
    public static final String FIELD_RESOURCES_ID = "resourcesId";

    /**
     * 字段 sortId
     */
    public static final String FIELD_SORT_ID = "sortId";

    /**
     * 字段 name
     */
    public static final String FIELD_NAME = "name";

    /**
     * 字段 permissionUrl
     */
    public static final String FIELD_PERMISSION_URL = "permissionUrl";

    /**
     * 字段 friendlyName
     */
    public static final String FIELD_FRIENDLY_NAME = "friendlyName";

    /**
     * 字段 summary
     */
    public static final String FIELD_SUMMARY = "summary";

    @NotNullOrBlank(message = "资源id不能为空")
    @Length(max = ResourcesModule.MAX_ID_LENGTH, message = "资源id不能超过" + ResourcesModule.MAX_ID_LENGTH + "个字。")
    @Column(name = "resources_id", nullable = false, length = ResourcesModule.MAX_ID_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(1)
    @ColumnDocument("资源id")
    private String resourcesId;

    /**
     *
     */
    @NotNull(message = "顺序不能为空")
    @Column(name = "sort_id", nullable = false)
    @Index
    @ColumnOrder(2)
    @ColumnDocument("顺序")
    private Integer sortId;

    @NotNullOrBlank(message = "权限名称不能为空")
    @Length(max = MAX_NAME_LENGTH, message = "权限名称长度不能超过" + MAX_NAME_LENGTH + "个字。")
    @Column(name = "name", nullable = false, length = MAX_NAME_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(3)
    @LogMessage(name = "权限名称", order = 1)
    @ColumnDocument("权限名称")
    private String name;

    @NotNullOrBlank(message = "友好不能为空")
    @Length(max = MAX_FRIENDLY_NAME_LENGTH, message = "友好长度不能超过" + MAX_FRIENDLY_NAME_LENGTH + "个字。")
    @Column(name = "friendly_name", nullable = false, length = MAX_FRIENDLY_NAME_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(4)
    @LogMessage(name = "友好名称", order = 2)
    @ColumnDocument("友好名称")
    private String friendlyName;

    /**
     * 权限Url
     */
    @Column(name = "permission_url", nullable = false)
    @ColumnType(jdbcType = JdbcType.BLOB)
    @ColumnOrder(5)
    @LogMessage(name = "权限Url", order = 3)
    @ColumnDocument("权限Url")
    private String permissionUrl;

    @Length(max = MAX_SUMMARY_LENGTH, message = "摘要长度不能超过" + MAX_SUMMARY_LENGTH + "个字。")
    @Column(name = "summary", nullable = false, length = MAX_SUMMARY_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(6)
    @LogMessage(name = "摘要", order = 4)
    @ColumnDocument("摘要")
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
     * @param permissionUrl
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

}
