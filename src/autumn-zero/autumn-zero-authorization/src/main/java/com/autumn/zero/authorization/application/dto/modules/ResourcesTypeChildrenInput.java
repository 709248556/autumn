package com.autumn.zero.authorization.application.dto.modules;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 资源类型子级输入
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-03 20:26
 **/
@Getter
@Setter
public class ResourcesTypeChildrenInput extends ResourcesTypeInput {
    private static final long serialVersionUID = 5054511201804970948L;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private String parentId;
}
