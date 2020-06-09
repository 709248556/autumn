package com.autumn.pay.channel;

/**
 * 通短服务
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 14:06
 */
public interface NotifyService {

    /**
     * 订单付款通知
     *
     * @param tradeChannel 通道
     * @param order        订单
     */
    void orderPayNotify(TradeChannel tradeChannel, TradeOrderInfo order);

    /**
     * 订单退款通知
     *
     * @param tradeChannel 通道
     * @param result       结果
     */
    void orderRefundNotify(TradeChannel tradeChannel, TradeRefundApplyResult result);
}
