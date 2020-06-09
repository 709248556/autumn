package com.autumn.zero.authorization.application.dto;

import com.autumn.application.dto.DefaultEntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 权限Dto
 *
 * @author 老码农 2018-12-17 11:06:00
 */
@ToString(callSuper = true)
@Getter
@Setter
public class PermissionDto extends DefaultEntityDto {

    /**
     *
     */
    private static final long serialVersionUID = -2214488432991881948L;

    /**
     * 资源类型
     */
    @ApiModelProperty(value = "资源类型")
    @NotNull(message = "资源类型不能为空")
    private Integer resourcesType;

    @NotNull(message = "授权对象id不能为空")
    @Override
    public Long getId() {
        return super.getId();
    }
}
