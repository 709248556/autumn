package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.TradeChannelConfigure;
import lombok.Getter;
import lombok.Setter;

/**
 * 默认交易通道配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 15:29
 */
@Getter
@Setter
public class DefaultTradeChannelConfigure implements TradeChannelConfigure {

    /**
     * 付款通知Url
     */
    private String payNotifyUrl;

    /**
     * 付款返回Url
     */
    private String payWebReturnUrl;

    /**
     * h5返回url
     */
    private String payH5ReturnUrl;

    /**
     * 转账通知url
     */
    private String transfersNotifyUrl;

    /**
     * 退款通知url
     */
    private String refundNotifyUrl;

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     */
    private int httpConnectTimeoutMs = 10 * 1000;

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     */
    private int httpReadTimeoutMs = 30 * 1000;

}
