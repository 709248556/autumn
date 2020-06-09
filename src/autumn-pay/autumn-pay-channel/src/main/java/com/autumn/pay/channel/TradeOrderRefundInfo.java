package com.autumn.pay.channel;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易订单退款信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 13:04
 */
public interface TradeOrderRefundInfo extends Serializable {

    /**
     * 获取退款编号
     *
     * @return
     */
    String getRefundNo();

    /**
     * 获取交易号
     *
     * @return
     */
    String getTradeNo();

    /**
     * 获取订单号
     *
     * @return
     */
    String getOrderNo();

    /**
     * 获取退款金额
     *
     * @return
     */
    Long getRefundAmount();

    /**
     * 获取退款时间
     *
     * @return
     */
    Date getRefundTime();

    /**
     * 获取退款原因
     *
     * @return
     */
    String getRefundReason();
}
