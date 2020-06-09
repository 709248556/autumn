package com.autumn.pay.channel;

import com.autumn.pay.channel.enums.ChannelType;
import com.autumn.pay.channel.enums.TradeNotifyType;
import com.autumn.util.channel.Channel;

import java.nio.charset.Charset;

/**
 * 交易通道
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 11:14
 */
public interface TradeChannel extends Channel {

    /**
     * 获取编码
     *
     * @return
     */
    Charset getCharset();

    /**
     * 是否是协议支付
     *
     * @return
     */
    boolean isProtocolPay();

    /**
     * 获取付款是否每次都是新请求
     *
     * @return
     */
    boolean isPayNewRequest();

    /**
     * 通道提供者简称
     *
     * @return
     */
    String getChannelProviderSimple();

    /**
     * 通道提供者
     *
     * @return 一般为通道提供公司名称
     */
    String getChannelProvider();

    /**
     * 获取通道类型
     *
     * @return
     */
    ChannelType getChannelType();

    /**
     * 交易通知响应消息
     * <p>原 payNotifyResponse 与 refundNotifyResponse </p>
     *
     * @param tradeNotifyType 交易通知类型
     * @param success         是否成功
     * @return
     */
    String tradeNotifyResponseMessage(TradeNotifyType tradeNotifyType, boolean success);

    /**
     * 获取通道配置
     *
     * @return
     */
    TradeChannelConfigure getChannelConfigure();

    /**
     * 获取默认账户
     *
     * @return
     */
    TradeChannelAccount getDefaultChannelAccount();

    /**
     * 检查账户信息
     *
     * @param tradeChannelAccount 账户
     */
    void checkTradeChannelAccount(TradeChannelAccount tradeChannelAccount);

    /**
     * 获取交易配置信息类型
     *
     * @return
     */
    Class<? extends TradeChannelAccount> getTradeChannelAccountType();

    /**
     * 付款申请
     *
     * @param order 订单
     * @return
     */
    TradePayApplyResult payApply(OrderPayApply order);

    /**
     * 付款申请
     *
     * @param order               订单
     * @param tradeChannelAccount 通道账户
     * @return
     */
    TradePayApplyResult payApply(OrderPayApply order, TradeChannelAccount tradeChannelAccount);

    /**
     * 退款申请
     *
     * @param apply 申请
     * @return
     */
    TradeRefundApplyResult refundApply(OrderRefundApply apply);

    /**
     * 退款申请
     *
     * @param apply               申请
     * @param tradeChannelAccount 通道账户
     * @return
     */
    TradeRefundApplyResult refundApply(OrderRefundApply apply, TradeChannelAccount tradeChannelAccount);

    /**
     * 查询订单信息
     *
     * @param apply 申请
     * @return
     */
    TradeOrderInfo queryOrderInfo(OrderQueryApply apply);

    /**
     * 查询订单信息
     *
     * @param apply               申请
     * @param tradeChannelAccount 通道账户
     * @return
     */
    TradeOrderInfo queryOrderInfo(OrderQueryApply apply, TradeChannelAccount tradeChannelAccount);

    /**
     * 查询订单退款信息
     *
     * @param apply 申请
     * @return
     */
    TradeOrderRefundInfo queryOrderRefundInfo(OrderRefundQueryApply apply);

    /**
     * 查询订单退款信息
     *
     * @param apply               申请
     * @param tradeChannelAccount 通道账户
     * @return
     */
    TradeOrderRefundInfo queryOrderRefundInfo(OrderRefundQueryApply apply, TradeChannelAccount tradeChannelAccount);

    /**
     * 创建支付通知
     *
     * @param notifyMessage 通知消息
     * @return
     */
    TradeOrderInfo createPayNotify(Object notifyMessage);

    /**
     * 创建支付通知
     *
     * @param notifyMessage       通知消息
     * @param tradeChannelAccount 通道账户
     * @return
     */
    TradeOrderInfo createPayNotify(Object notifyMessage, TradeChannelAccount tradeChannelAccount);

    /**
     * 创建退款通知
     *
     * @param notifyMessage 通知消息
     * @return
     */
    TradeRefundApplyResult createRefundNotify(Object notifyMessage);

    /**
     * 创建退款通知
     *
     * @param notifyMessage       通知消息
     * @param tradeChannelAccount 通道账户
     * @return
     */
    TradeRefundApplyResult createRefundNotify(Object notifyMessage, TradeChannelAccount tradeChannelAccount);
}
