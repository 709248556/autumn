package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.TradeOrderRefundInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 默认交易订单退款信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 14:58
 */
@Getter
@Setter
public class DefaultTradeOrderRefundInfo implements TradeOrderRefundInfo {
    private static final long serialVersionUID = -340510684088930882L;

    /**
     * 退款单号
     */
    private String refundNo;
    /**
     * 交易号
     */
    private String tradeNo;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 本次退款金额
     */
    private Long refundAmount;
    /**
     * 退款时间
     */
    private Date refundTime;
    /**
     * 退款原因
     */
    private String refundReason;

}
