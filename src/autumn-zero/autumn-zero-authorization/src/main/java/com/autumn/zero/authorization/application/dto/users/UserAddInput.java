package com.autumn.zero.authorization.application.dto.users;

import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * 用户添加输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 12:20
 */
@ToString(callSuper = true)
public class UserAddInput extends UserInput {
    private static final long serialVersionUID = 2390096445479871124L;

    @ApiModelProperty(value = "初始密码")
    @NotNullOrBlank(message = "初始密码不能为空。")
    private String password;

    /**
     * 获取初始密码
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置初始密码
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
