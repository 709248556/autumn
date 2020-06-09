package com.autumn.zero.authorization.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限授权输入
 *
 * @author 老码农 2018-12-17 11:17:23
 */
@ToString(callSuper = true)
@Getter
@Setter
public class PermissionGrantedInput extends PermissionDto {

    /**
     *
     */
    private static final long serialVersionUID = -8022631368247653100L;

    /**
     * 权限集合
     */
    @ApiModelProperty(value = "权限集合")
    private List<PermissionResourcesModuleDto> permissions;

    /**
     * 实例化
     */
    public PermissionGrantedInput() {
        this.setPermissions(new ArrayList<>(16));
    }

    @Override
    public void valid() {
        super.valid();
        if (this.getPermissions() == null) {
            this.setPermissions(new ArrayList<>());
        }
    }
}
