package com.autumn.zero.authorization.application.dto.auth.wechat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 微信用户信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-21 2:10
 */
@ToString(callSuper = true)
@Getter
@Setter
public class WeChatUserInfoDto implements Serializable {
    private static final long serialVersionUID = -9097988170759635879L;

    /**
     * 系统繁忙
     */
    public static final int ERROR_CODE_SYSTEM_BUSY = -1;

    /**
     * 成功
     */
    public static final int ERROR_CODE_SUCCESS = 0;

    /**
     * 无效的 appid
     */
    public static final int ERROR_CODE_INVALID_APP_ID = 40013;

    /**
     * 用户的标识，对当前公众号唯一
     */
    private String openid;

    /**
     * 会话密钥
     */
    private String session_key;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionid;

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误消息
     */
    private String errmsg;

}
