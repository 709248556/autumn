package com.autumn.zero.authorization.entities.common.modules;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.constants.SettingConstants;
import com.autumn.domain.entities.AbstractEntity;
import com.autumn.domain.entities.EntityDataBean;
import com.autumn.mybatis.mapper.annotation.*;
import com.autumn.util.BeanUtils;
import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 资源模块
 * <p>
 * 应当采用三级菜单，不可多也不可少
 * </p>
 *
 * @author 老码农 2018-12-05 15:59:18
 */
@ToString(callSuper = true)
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_res_module")
@TableDocument(value = "资源模块", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class ResourcesModule extends AbstractEntity<String> implements DataValidation, EntityDataBean {

    /**
     *
     */
    private static final long serialVersionUID = -8016163370935755027L;

    /**
     * 最大Id长度
     */
    public static final int MAX_ID_LENGTH = 100;

    /**
     * 最大名称长度
     */
    public static final int MAX_NAME_LENGTH = 50;

    /**
     * 最大图标长度
     */
    public static final int MAX_ICON_LENGTH = 50;

    /**
     * 最大标识长度
     */
    public static final int MAX_IDENTIFICATION_LENGTH = 255;

    /**
     * 最大url长度
     */
    public static final int MAX_URL_LENGTH = 255;

    /**
     * 最大摘要长度
     */
    public static final int MAX_SUMMARY_LENGTH = 255;

    /**
     * 字段 name
     */
    public static final String FIELD_NAME = "name";

    /**
     * 字段 customName
     */
    public static final String FIELD_CUSTOM_NAME = "customName";

    /**
     * 字段 parentId
     */
    public static final String FIELD_PARENT_ID = "parentId";

    /**
     * 字段 resourcesType
     */
    public static final String FIELD_RESOURCES_TYPE = "resourcesType";

    /**
     * 字段 sortId
     */
    public static final String FIELD_SORT_ID = "sortId";

    /**
     * 字段 isAuthorize
     */
    public static final String FIELD_IS_AUTHORIZE = "isAuthorize";

    /**
     * 字段 isMenu
     */
    public static final String FIELD_IS_MENU = "isMenu";

    /**
     * 字段 identification
     */
    public static final String FIELD_IDENTIFICATION = "identification";

    /**
     * 字段 permissionUrl
     */
    public static final String FIELD_PERMISSION_URL = "permissionUrl";

    /**
     * 字段 url
     */
    public static final String FIELD_URL = "url";

    /**
     * 字段 icon
     */
    public static final String FIELD_ICON = "icon";

    /**
     * 字段 summary
     */
    public static final String FIELD_SUMMARY = "summary";

    @NotNullOrBlank(message = "名称不能为空")
    @Length(max = MAX_NAME_LENGTH, message = "名称长度不能超过" + MAX_NAME_LENGTH + "个字。")
    @Column(name = "name", nullable = false, length = MAX_NAME_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index
    @ColumnOrder(2)
    @LogMessage(name = "资源名称", order = 1)
    @ColumnDocument("资源名称")
    private String name;

    @NotNullOrBlank(message = "自定义资源名称不能为空")
    @Length(max = MAX_NAME_LENGTH, message = "自定义资源名称长度不能超过" + MAX_NAME_LENGTH + "个字。")
    @Column(name = "custom_name", nullable = false, length = MAX_NAME_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index
    @ColumnOrder(3)
    @LogMessage(name = "自定义资源名称", order = 2)
    @ColumnDocument("自定义资源名称")
    private String customName;

    @Length(max = MAX_ID_LENGTH, message = "父级Id长度不能超过" + MAX_ID_LENGTH + "个字。")
    @Column(name = "parent_id", nullable = false, length = MAX_ID_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(4)
    @ColumnDocument("父级Id")
    private String parentId;

    /**
     * 资源类型
     */
    @NotNull(message = "资源类型不能为空")
    @Column(name = "resources_type", nullable = false)
    @ColumnOrder(5)
    @Index
    @ColumnDocument("资源类型")
    private Integer resourcesType;

    /**
     *
     */
    @NotNull(message = "顺序不能为空")
    @ColumnOrder(6)
    @Column(name = "sort_id", nullable = false)
    @Index
    @LogMessage(name = "顺序", order = 3)
    @ColumnDocument("顺序")
    private Integer sortId;

    /**
     * 是否需要授权
     */
    @ColumnOrder(7)
    @Column(name = "is_authorize", nullable = false)
    @LogMessage(name = "需要授权", order = 4)
    @ColumnDocument("需要授权")
    private boolean isAuthorize;

    /**
     * 是否菜单
     */
    @Column(name = "is_menu", nullable = false)
    @ColumnOrder(8)
    @LogMessage(name = "菜单", order = 5)
    @ColumnDocument("菜单")
    private boolean isMenu;

    /**
     * 系统模块
     */
    @Column(name = "is_sys_module", nullable = false)
    @ColumnOrder(9)
    @LogMessage(name = "系统模块", order = 6)
    @ColumnDocument("系统模块")
    private boolean isSysModule;

    @Length(max = MAX_IDENTIFICATION_LENGTH, message = "标识长度不能超过" + MAX_IDENTIFICATION_LENGTH + "个字。")
    @Column(name = "identification", nullable = false, length = MAX_IDENTIFICATION_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(10)
    @LogMessage(name = "标识", order = 7)
    @ColumnDocument("标识")
    private String identification;

    /**
     * 权限Url
     */
    @Length(max = MAX_URL_LENGTH, message = "权限Url长度不能超过" + MAX_URL_LENGTH + "个字。")
    @Column(name = "permission_url", nullable = false, length = MAX_URL_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(11)
    @LogMessage(name = "权限Url", order = 8)
    @ColumnDocument("权限Url")
    private String permissionUrl;

    @Length(max = MAX_URL_LENGTH, message = "Url长度不能超过" + MAX_URL_LENGTH + "个字。")
    @Column(name = "url", nullable = false, length = MAX_URL_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(12)
    @LogMessage(name = "Url", order = 9)
    @ColumnDocument("Url")
    private String url;

    @Length(max = MAX_ICON_LENGTH, message = "icon长度不能超过" + MAX_ICON_LENGTH + "个字。")
    @Column(name = "icon", nullable = false, length = MAX_ICON_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(13)
    @LogMessage(name = "图标", order = 10)
    @ColumnDocument("图标")
    private String icon;

    @Length(max = MAX_SUMMARY_LENGTH, message = "摘要长度不能超过" + MAX_SUMMARY_LENGTH + "个字。")
    @Column(name = "summary", nullable = false, length = MAX_SUMMARY_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(14)
    @LogMessage(name = "摘要", order = 11)
    @ColumnDocument("摘要")
    private String summary;

    /**
     *
     */
    public ResourcesModule() {

    }

    /**
     * 获取菜单名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名称
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
     * @param customName
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    /**
     * 获取父级id
     *
     * @return
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父级id
     *
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取资源类型
     *
     * @return
     */
    public Integer getResourcesType() {
        return resourcesType;
    }

    /**
     * 设置资源类型
     *
     * @param resourcesType
     */
    public void setResourcesType(Integer resourcesType) {
        this.resourcesType = resourcesType;
    }

    /**
     * 获取排序
     *
     * @return
     */
    public Integer getSortId() {
        return sortId;
    }

    /**
     * 设置排序
     *
     * @param sortId
     */
    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    /**
     * 获取是否菜单
     *
     * @return
     */
    public boolean getIsMenu() {
        return isMenu;
    }

    /**
     * 设置是否菜单
     *
     * @param isMenu
     */
    public void setIsMenu(boolean isMenu) {
        this.isMenu = isMenu;
    }

    /**
     * 获取授权
     *
     * @return
     */
    public boolean getIsAuthorize() {
        return isAuthorize;
    }

    /**
     * 设置授权
     *
     * @param isAuthorize
     */
    public void setIsAuthorize(boolean isAuthorize) {
        this.isAuthorize = isAuthorize;
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

    /**
     * 获取标识
     *
     * @return
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * 设置标识
     *
     * @param identification
     */
    public void setIdentification(String identification) {
        this.identification = identification;
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
     * 获取url
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取图标
     *
     * @return
     */
    public String getIcon() {
        return icon;
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

    /**
     * 默认转换
     */
    @Override
    public void forNullToDefault() {
        BeanUtils.setDbEntityforNullToDefault(this);
    }
}
