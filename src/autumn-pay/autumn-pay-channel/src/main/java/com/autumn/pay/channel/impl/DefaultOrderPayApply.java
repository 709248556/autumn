package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.OrderPayApply;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认订单付款申请
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 14:28
 */
@Getter
@Setter
public class DefaultOrderPayApply implements OrderPayApply {

    private static final long serialVersionUID = 3639666269068061105L;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date expireTime;
    /**
     * 客户端ip
     */
    private String clientIp;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 主题
     */
    private String subject;
    /**
     * 主体
     */
    private String body;
    /**
     * 扩展参数
     */
    private String extraParam;
    /**
     * 订单金额
     */
    private Long orderAmount;
    /**
     * 用户标识
     */
    private String userToken;
    /**
     * 扩展参数
     */
    private Map<String, String> params;

    public DefaultOrderPayApply() {
        this.setParams(new HashMap<>());
    }
}
