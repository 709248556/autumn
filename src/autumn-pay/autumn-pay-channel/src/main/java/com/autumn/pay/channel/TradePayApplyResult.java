package com.autumn.pay.channel;

import com.autumn.pay.channel.enums.TradePayApplyResultCallType;

import java.io.Serializable;

/**
 * 交易支付申请结果
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 12:42
 */
public interface TradePayApplyResult extends Serializable {

    /**
     * 获取付款id
     *
     * @return
     */
    String getPayId();

    /**
     * 获取付款Url
     *
     * @return
     */
    String getPayUrl();

    /**
     * 获取付款参数
     *
     * @return
     */
    String getPayParam();

    /**
     * 获取调用类型
     *
     * @return
     */
    TradePayApplyResultCallType getCallType();

    /**
     * 付款交易订单信息
     *
     * @return 如果直接支付成功，则返回(协议支付)
     */
    TradeOrderInfo getTradeOrderInfo();
}
