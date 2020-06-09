package com.autumn.zero.authorization.application.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户登录状态输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 02:09
 **/
@Getter
@Setter
public class UserLoginStatusOutput<TUserLoginInfo extends UserLoginInfoOutput> implements Serializable {

    private static final long serialVersionUID = -9141830399245325340L;

    /**
     * 是否登录
     */
    @ApiModelProperty(value = "是否登录")
    private boolean login;

    /**
     * 登录信息
     */
    @ApiModelProperty(value = "登录信息,如果未登录，则返回null.")
    private TUserLoginInfo loginInfo;

}
