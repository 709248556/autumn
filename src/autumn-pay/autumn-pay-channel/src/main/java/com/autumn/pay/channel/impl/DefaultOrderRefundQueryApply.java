package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.OrderRefundQueryApply;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 14:49
 */
@Getter
@Setter
public class DefaultOrderRefundQueryApply implements OrderRefundQueryApply {
    private static final long serialVersionUID = -3031047086016432505L;
    /**
     * 退款编号
     */
    private String refundNo;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 交易号
     */
    private String tradeNo;

}
