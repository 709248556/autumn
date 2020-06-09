package com.autumn.zero.authorization.values;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.web.vo.AbstractUrlRequestMappingInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 资源模块Url请求权限映射信息
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2019-12-31 20:07
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class ResourcesModuleUrlRequestPermissionMappingInfoValue
        extends AbstractUrlRequestMappingInfo implements Serializable {

    private static final long serialVersionUID = -5030670069373909072L;

    /**
     * 权限访问模块名称列表
     *
     * @return
     */
    @ApiModelProperty(value = "权限访问模块名称列表")
    @JSONField(ordinal = 100)
    private String permissionVisitModuleNames;

    /**
     * 权限访问
     *
     * @return
     */
    @ApiModelProperty(value = "权限访问")
    @JSONField(ordinal = 101)
    private boolean permissionVisit;

    /**
     * 权限访问说明
     */
    @ApiModelProperty(value = "权限访问说明")
    @JSONField(ordinal = 103)
    private String permissionVisitExplain;

    /**
     * 权限集合
     */
    @ApiModelProperty(value = "权限集合")
    @JSONField(ordinal = 103)
    private String permissions;

    /**
     * 权限说明集合
     */
    @ApiModelProperty(value = "权限说明集合")
    @JSONField(ordinal = 104)
    private String permissionExplains;
}
