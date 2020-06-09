package com.autumn.web.dto;

import com.autumn.annotation.FriendlyProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 安全键输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-23 17:55
 **/
@Getter
@Setter
public class SecurityKeyOutput implements Serializable {

    private static final long serialVersionUID = -7000398859266778674L;

    /**
     * 键
     */
    @FriendlyProperty(value = "key")
    @ApiModelProperty(value = "键")
    private String key;

    /**
     *
     */
    public SecurityKeyOutput() {

    }

    /**
     *
     * @param key
     */
    public SecurityKeyOutput(String key) {
        this.setKey(key);
    }

}
