package com.autumn.zero.authorization.application.dto.auth.wechat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 微信会话
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-21 0:52
 */
@ToString(callSuper = true)
@Getter
@Setter
public class WeChatSessionDto implements Serializable {
    private static final long serialVersionUID = -4406909642881153705L;

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
     * 无效的 code
     */
    public static final int ERROR_CODE_INVALID_CODE = 40029;

    /**
     * 无效的 appsecret
     */
    public static final int ERROR_CODE_INVALID_APP_SECRET = 40125;

    /**
     * 频率限制
     */
    public static final int ERROR_CODE_FREQUENCY_LIMIT = 45011;

    /**
     * 用户唯一标识
     */
    private String openid;

    /**
     * 会话密钥
     */
    private String session_key;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
     */
    private String unionid;

    /**
     * 错误码(-1=系统繁忙，此时请开发者稍候再试,0=成功,40013=appid无效，40125=无效的 appsecret,40029=code 无效,45011=频率限制，每个用户每分钟100次)
     */
    private Integer errcode;

    /**
     * 错误消息
     */
    private String errmsg;
}
