package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.TradeOrderInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 默认交易订单信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 14:52
 */
@Getter
@Setter
public class DefaultTradeOrderInfo implements TradeOrderInfo {

    private static final long serialVersionUID = -2506180557635707894L;
    private String orderNo;
    private String tradeNo;
    private Date payTime;
    private Long orderAmount;
    private Long refundAmount;
    private String channelMode;
    private String otherPartyAccount;
    private String otherPartyAccountId;
    private String bankType;
    private String bankName;
    private String extraParam;
    private String subject;
    private String body;

}
