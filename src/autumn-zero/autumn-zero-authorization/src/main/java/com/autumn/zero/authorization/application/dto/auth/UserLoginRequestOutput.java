package com.autumn.zero.authorization.application.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 登录请求信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 15:56
 */
@ToString(callSuper = true)
@Getter
@Setter
public class UserLoginRequestOutput implements Serializable, AuthSessionInfo{

    private static final long serialVersionUID = -8586891852523680502L;
    /**
     * 必须提供图形验证码
     */
    @ApiModelProperty(value = "必须提供图形验证码")
    boolean mustImageCode;

    /**
     * 错误次数
     */
    @ApiModelProperty(value = "错误次数")
    Integer errorCount;

    @ApiModelProperty(value = "会话键")
    private String sessionKey;

    @ApiModelProperty(value = "会话id")
    private String sessionId;

    @ApiModelProperty(value = "会话过期时间(毫秒)")
    private Long sessionTimeout;
}
