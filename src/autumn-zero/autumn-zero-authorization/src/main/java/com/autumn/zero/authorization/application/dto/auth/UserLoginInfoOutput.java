package com.autumn.zero.authorization.application.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户登录信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 15:54
 */
@ToString(callSuper = true)
@Getter
@Setter
public class UserLoginInfoOutput implements Serializable, AuthSessionInfo {

    private static final long serialVersionUID = 1288452154184972879L;

    /**
     * 用户id
     */
    // @ApiModelProperty(value = "用户id")
    // private Long userId;

    /**
     * 用户名称(账号)
     */
    @ApiModelProperty(value = "用户名称(账号)")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 真实姓名
     */
    // @ApiModelProperty(value = "真实姓名")
    //private String realName;

    /**
     * 性别
     */
    // @ApiModelProperty(value = "性别")
    //  private String sex;

    /**
     * 出生日期
     */
    //  @ApiModelProperty(value = "出生日期")
    //  private Date birthday;

    /**
     * 手机号
     */
    // @ApiModelProperty(value = "手机号")
    // private String phoneNumber;

    /**
     * 邮箱地址
     */
    // @ApiModelProperty(value = "邮箱地址")
    // private String emailAddress;

    /**
     * 头像路径
     */
    @ApiModelProperty(value = "头像路径")
    private String headPortraitPath;

    @ApiModelProperty(value = "会话键")
    private String sessionKey;

    @ApiModelProperty(value = "会话id")
    private String sessionId;

    @ApiModelProperty(value = "会话过期时间(毫秒)")
    private Long sessionTimeout;

    /**
     * 设备Token
     */
    @ApiModelProperty(value = "设备Token")
    private String deviceToken;
}
