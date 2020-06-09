package com.autumn.zero.authorization.application.dto.auth.wechat;

import com.autumn.security.token.DefaultCredentialsDeviceInfo;
import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信应用 Token 输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-21 2:48
 */
@ToString(callSuper = true)
@Getter
@Setter
public class WeChatAppTokenInput extends DefaultDataValidation {

    private static final long serialVersionUID = 5148609029153088768L;

    /**
     * 前端用户的js_code
     */
    @ApiModelProperty(value = "jsCode 前端用户的js_code", required = true, dataType = "String")
    @NotNullOrBlank(message = "jsCode 不能为空。")
    private String jsCode;

    /**
     * encryptedData 加密数据
     */
    @ApiModelProperty(value = "encryptedData 加密数据", required = true, dataType = "String")
    @NotNullOrBlank(message = "encryptedData 不能为空。")
    private String encryptedData;

    /**
     * 向量
     */
    @ApiModelProperty(value = "iv 向量", required = true, dataType = "String")
    @NotNullOrBlank(message = "iv 不能为空。")
    private String iv;

    /**
     * 用户的昵称
     */
    @ApiModelProperty(value = "用户的昵称")
    private String nickName;

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    @ApiModelProperty(value = "性别")
    private Integer gender;

    /**
     * 用户所在城市
     */
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 用户所在国家
     */
    @ApiModelProperty(value = "国家")
    private String country;

    /**
     * 用户所在省份
     */
    @ApiModelProperty(value = "省份")
    private String province;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    /**
     * 认证设备信息
     */
    @ApiModelProperty(value = "认证设备信息")
    private DefaultCredentialsDeviceInfo credentialsDeviceInfo;
}
