package com.autumn.zero.authorization.plugins.data;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源模块数据
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 19:05
 **/
@Getter
@Setter
public class ResourcesModuleData extends DefaultDataValidation {

    private static final long serialVersionUID = -854474970844393987L;

    /**
     * 资源id
     */
    @NotNullOrBlank(message = "id不能为空")
    @Length(max = ResourcesModule.MAX_ID_LENGTH, message = "id长度不能超过" + ResourcesModule.MAX_ID_LENGTH + "个字。")
    private String id;

    /**
     * 名称
     */
    @NotNullOrBlank(message = "名称不能为空")
    @Length(max = ResourcesModule.MAX_NAME_LENGTH, message = "名称长度不能超过" + ResourcesModule.MAX_NAME_LENGTH + "个字。")
    private String name;

    /**
     * 自定义资源名称
     */
    @Length(max = ResourcesModule.MAX_NAME_LENGTH, message = "自定义资源名称长度不能超过" + ResourcesModule.MAX_NAME_LENGTH + "个字。")
    private String customName;

    /**
     * 资源类型
     */
    @NotNull(message = "资源类型不能为空")
    private Integer resourcesType;

    /**
     * 顺序
     */
    @NotNull(message = "顺序不能为空")
    private Integer sortId;

    /**
     * 需要授权
     */
    private boolean authorize;

    /**
     * 是否属于菜单
     */
    private boolean menu;

    /**
     * 是否系统模块
     */
    private boolean sysModule;

    /**
     * 标识
     */
    @Length(max = ResourcesModule.MAX_IDENTIFICATION_LENGTH, message = "标识长度不能超过" + ResourcesModule.MAX_IDENTIFICATION_LENGTH + "个字。")
    private String identification;

    /**
     * 权限Url
     */
    @Length(max = ResourcesModule.MAX_URL_LENGTH, message = "权限Url长度不能超过" + ResourcesModule.MAX_URL_LENGTH + "个字。")
    private String permissionUrl;

    /**
     * Url
     */
    @Length(max = ResourcesModule.MAX_URL_LENGTH, message = "Url长度不能超过" + ResourcesModule.MAX_URL_LENGTH + "个字。")
    private String url;

    /**
     * 图标
     */
    @Length(max = ResourcesModule.MAX_ICON_LENGTH, message = "icon长度不能超过" + ResourcesModule.MAX_ICON_LENGTH + "个字。")
    private String icon;

    /**
     * 摘要
     */
    @Length(max = ResourcesModule.MAX_SUMMARY_LENGTH, message = "摘要长度不能超过" + ResourcesModule.MAX_SUMMARY_LENGTH + "个字。")
    private String summary;

    /**
     * 子级集合
     */
    private List<ResourcesModuleData> children = new ArrayList<>(16);

    /**
     * 权限集合
     */
    private List<ResourcesModulePermissionData> permissions = new ArrayList<>(16);

}
