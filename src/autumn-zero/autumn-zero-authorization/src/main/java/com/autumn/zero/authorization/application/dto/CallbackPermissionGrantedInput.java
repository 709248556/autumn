package com.autumn.zero.authorization.application.dto;

import com.autumn.application.dto.DefaultEntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 回调授权输入
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 11:16
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class CallbackPermissionGrantedInput extends DefaultEntityDto {

    private static final long serialVersionUID = 6040287796257775740L;

    /**
     * 权限集合
     */
    @ApiModelProperty(value = "权限集合")
    private List<PermissionResourcesModuleDto> permissions;

    @NotNull(message = "授权对象id不能为空")
    @Override
    public Long getId() {
        return super.getId();
    }

    /**
     * 实例化
     */
    public CallbackPermissionGrantedInput() {
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
