package com.autumn.zero.authorization.application.dto.auth.wechat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * WeChatAccessToken Dto
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-21 1:33
 */
@ToString(callSuper = true)
@Getter
@Setter
public class WeChatAccessTokenDto implements Serializable {

    private static final long serialVersionUID = -122978871618327860L;

    /**
     * 系统繁忙
     */
    public static final int ERROR_CODE_SYSTEM_BUSY = -1;

    /**
     * 成功
     */
    public static final int ERROR_CODE_SUCCESS = 0;

    /**
     * 无效的 appsecret
     */
    public static final int ERROR_CODE_INVALID_APP_SECRET = 40001;

    /**
     * 无效的 appid
     */
    public static final int ERROR_CODE_INVALID_APP_ID = 40013;

    /**
     * ip 不存白名单
     */
    public static final int ERROR_CODE_IP_NOT_WHITE_LIST = 40164;

    /**
     * 访问令牌(获取到的凭证)
     */
    private String access_token;

    /**
     * 凭证有效时间，单位：秒
     */
    private Integer expires_in;

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误消息
     */
    private String errmsg;
}
