package com.autumn.pay.channel;

/**
 * 交易退款申请结果
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 12:56
 */
public interface TradeRefundApplyResult extends TradeApplyResult {

    /**
     * 获取交易订单退款信息
     *
     * @return
     */
    TradeOrderRefundInfo getTradeOrderRefundInfo();
}
