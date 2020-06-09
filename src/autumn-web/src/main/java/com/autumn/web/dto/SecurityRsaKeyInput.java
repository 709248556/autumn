package com.autumn.web.dto;

import com.autumn.annotation.FriendlyProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 安全键 Rsa 键 Input
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-23 18:14
 **/
@Getter
@Setter
public class SecurityRsaKeyInput implements Serializable {

    private static final long serialVersionUID = 6002334500484247075L;

    /**
     * 是否 Pkcs1格式
     */
    @ApiModelProperty(value = "是否 Pkcs1 格式")
    @FriendlyProperty(value = "是否 Pkcs1 格式")
    private boolean pkcs1Format = true;
}
