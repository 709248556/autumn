package com.autumn.zero.authorization.plugins.data;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModulePermission;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 资源模块权限
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 19:18
 **/
@Getter
@Setter
public class ResourcesModulePermissionData extends DefaultDataValidation {

    private static final long serialVersionUID = 6114471057767879516L;

    /**
     * 权限名称
     */
    @NotNullOrBlank(message = "权限名称不能为空")
    @Length(max = ResourcesModulePermission.MAX_NAME_LENGTH, message = "权限名称长度不能超过"
            + ResourcesModulePermission.MAX_NAME_LENGTH + "个字。")
    private String name;

    /**
     * 友好名称
     */
    @NotNullOrBlank(message = "友好名称不能为空")
    @Length(max = ResourcesModulePermission.MAX_FRIENDLY_NAME_LENGTH, message = "友好长度不能超过"
            + ResourcesModulePermission.MAX_FRIENDLY_NAME_LENGTH + "个字。")
    private String friendlyName;

    /**
     * 顺序
     */
    @NotNull(message = "顺序不能为空")
    private Integer sortId;

    /**
     * 权限Url，多个权限采用换行符
     */
    private String permissionUrl;

    /**
     * 摘要
     */
    @Length(max = ResourcesModulePermission.MAX_SUMMARY_LENGTH, message = "摘要长度不能超过"
            + ResourcesModulePermission.MAX_SUMMARY_LENGTH + "个字。")
    private String summary;

}
