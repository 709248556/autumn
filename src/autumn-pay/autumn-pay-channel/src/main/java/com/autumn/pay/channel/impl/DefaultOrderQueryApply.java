package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.OrderQueryApply;
import lombok.Getter;
import lombok.Setter;

/**
 * 默认订单查询申请
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 14:37
 */
@Getter
@Setter
public class DefaultOrderQueryApply implements OrderQueryApply {
    private static final long serialVersionUID = -3375400440912825636L;

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 交易号
     */
    private String tradeNo;

}
