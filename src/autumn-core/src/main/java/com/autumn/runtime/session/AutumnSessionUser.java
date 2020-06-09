package com.autumn.runtime.session;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * 会话用户
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 19:35
 **/
@Getter
public class AutumnSessionUser implements Serializable {

    private static final long serialVersionUID = -8156489805941927973L;

    /**
     * 会话id
     */
    private final Serializable sessionId;

    /**
     * 获取会话开始时间
     *
     * @return
     */
    private final Date startTime;

    /**
     * 客户端主机
     */
    private final String host;

    /**
     * 获取用户
     */
    private final AutumnUser user;

    /**
     * 实例化
     *
     * @param sessionId
     * @param startTime
     * @param host
     * @param user
     */
    public AutumnSessionUser(Serializable sessionId, Date startTime, String host, AutumnUser user) {
        this.sessionId = sessionId;
        this.startTime = startTime;
        this.host = host;
        this.user = user;
    }
}
