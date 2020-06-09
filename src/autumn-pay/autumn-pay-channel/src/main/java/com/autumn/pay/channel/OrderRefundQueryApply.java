package com.autumn.pay.channel;

import java.io.Serializable;

/**
 * 订单退款查询申请
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 13:40
 */
public interface OrderRefundQueryApply extends Serializable {

    /**
     * 获取退款批号
     *
     * @return
     */
    String getRefundNo();

    /**
     * 获取订单号(与交易号至少一项不能为空)
     *
     * @return
     */
    String getOrderNo();

    /**
     * 获取交易号(与订单号至少一项不能为空)
     *
     * @return
     */
    String getTradeNo();
}
