package com.autumn.pay.channel.alipay;

import com.autumn.pay.channel.*;
import com.autumn.pay.channel.alipay.request.PayQueryRequest;
import com.autumn.pay.channel.alipay.request.RefundQueryRequest;
import com.autumn.pay.channel.alipay.request.RefundRequest;
import com.autumn.pay.channel.alipay.response.PayNotifyResponse;
import com.autumn.pay.channel.enums.TradeNotifyType;

import java.util.Map;

/**
 * 支付宝交易通道抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 17:17
 */
public abstract class AbstractAiliPayTradeChannel
        extends AbstractTradeChannel<AliPayChannelAccount, Map<String, String>> {

    private static final String NOTIFY_RESPONSE_SUCCESS = "success";
    private static final String NOTIFY_RESPONSE_FAIL = "fail";

    /**
     *
     */
    public AbstractAiliPayTradeChannel() {
        super(AliPayChannelAccount.class);
    }

    @Override
    public final String getChannelProviderSimple() {
        return "支付宝";
    }

    @Override
    public String getChannelProvider() {
        return "支付宝(中国)网络技术有限公司";
    }

    @Override
    public String tradeNotifyResponseMessage(TradeNotifyType tradeNotifyType, boolean success) {
        if (success) {
            return NOTIFY_RESPONSE_SUCCESS;
        }
        return NOTIFY_RESPONSE_FAIL;
    }

    @Override
    protected void internalCheckTradeChannelAccount(AliPayChannelAccount configureInfo) {

    }

    /**
     * 内部查询订单信息
     *
     * @param apply          申请
     * @param channelAccount 配置信息
     * @return
     */
    @Override
    protected TradeOrderInfo internalQueryOrderInfo(OrderQueryApply apply, AliPayChannelAccount channelAccount) {
        PayQueryRequest request = new PayQueryRequest(this, channelAccount, apply);
        return request.toResult();
    }

    /**
     * 内部创建支付通知
     *
     * @param notifyMap
     * @param channelAccount 配置信息
     * @return
     */
    @Override
    protected TradeOrderInfo internalCreatePayNotify(Map<String, String> notifyMap, AliPayChannelAccount channelAccount) {
        PayNotifyResponse response = new PayNotifyResponse(this, channelAccount, notifyMap);
        return response.toResult();
    }

    /**
     * 内部退款申请
     *
     * @param apply          申请
     * @param channelAccount 配置信息
     * @return
     */
    @Override
    protected TradeRefundApplyResult internalRefundApply(OrderRefundApply apply, AliPayChannelAccount channelAccount) {
        RefundRequest request = new RefundRequest(this, channelAccount, apply);
        return request.toResult();
    }

    /**
     * 内部查询订单退款信息
     *
     * @param apply          申请
     * @param channelAccount 配置信息
     * @return
     */
    @Override
    protected TradeOrderRefundInfo internalQueryOrderRefundInfo(OrderRefundQueryApply apply, AliPayChannelAccount channelAccount) {
        RefundQueryRequest request = new RefundQueryRequest(this, channelAccount, apply);
        return request.toResult();
    }
}
