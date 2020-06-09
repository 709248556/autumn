package com.autumn.security.token;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 票据审计日志
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 11:11
 **/
@Getter
@Setter
public class ToeknAuditedLog implements Serializable {

    private static final long serialVersionUID = 2050245897745215762L;

    /**
     * 用户账户
     */
    private String userAccount;

    /**
     * 提供者
     */
    private String provider;

    /**
     * 键
     */
    private String providerKey;

    /**
     * 失败状态消息
     */
    private String failStatusMessage;

}
