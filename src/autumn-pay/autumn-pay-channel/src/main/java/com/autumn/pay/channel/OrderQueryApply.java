package com.autumn.pay.channel;

import java.io.Serializable;

/**
 * 订单查询申请
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 11:27
 */
public interface OrderQueryApply extends Serializable {

    /**
     * 获取订单号
     *
     * @return
     */
    String getOrderNo();

    /**
     * 获取交易号
     *
     * @return
     */
    String getTradeNo();
}
