package com.autumn.pay.channel.weixin;

import com.autumn.pay.channel.*;
import com.autumn.pay.channel.enums.ChannelType;
import com.autumn.pay.channel.enums.TradeNotifyType;
import com.autumn.pay.channel.weixin.request.PayQueryRequest;
import com.autumn.pay.channel.weixin.request.RefundQueryRequest;
import com.autumn.pay.channel.weixin.request.RefundRequest;
import com.autumn.pay.channel.weixin.response.PayNotifyResponse;
import com.autumn.pay.channel.weixin.response.RefundNotifyResponse;

/**
 * 微信交易通道抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 15:59
 */
public abstract class AbstractWeiXinTradeChannel extends AbstractTradeChannel<WeiXinChannelAccount, String> {

    private static final String NOTIFY_RESPONSE_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    private static final String NOTIFY_RESPONSE_FAIL = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[ERROR]]></return_msg></xml>";

    public AbstractWeiXinTradeChannel() {
        super(WeiXinChannelAccount.class);
    }

    @Override
    public final String getChannelProviderSimple() {
        return "微信";
    }

    @Override
    public String getChannelProvider() {
        return "深圳市财付通科技有限公司";
    }

    @Override
    public ChannelType getChannelType() {
        return null;
    }

    @Override
    public String tradeNotifyResponseMessage(TradeNotifyType tradeNotifyType, boolean success) {
        if (success) {
            return NOTIFY_RESPONSE_SUCCESS;
        }
        return NOTIFY_RESPONSE_FAIL;
    }

    @Override
    protected void internalCheckTradeChannelAccount(WeiXinChannelAccount configureInfo) {

    }

    /**
     * 内部查询订单信息
     *
     * @param apply         申请
     * @param channelAccount 配置信息
     * @return
     */
    @Override
    protected TradeOrderInfo internalQueryOrderInfo(OrderQueryApply apply, WeiXinChannelAccount channelAccount) {
        PayQueryRequest request = new PayQueryRequest(this, channelAccount, apply);
        return request.toResult();
    }

    /**
     * 内部创建支付通知
     *
     * @param xml           通知
     * @param channelAccount 配置信息
     * @return
     */
    @Override
    protected TradeOrderInfo internalCreatePayNotify(String xml, WeiXinChannelAccount channelAccount) {
        PayNotifyResponse response = new PayNotifyResponse(this, channelAccount, xml);
        return response.toResult();
    }

    /**
     * 内部退款申请
     *
     * @param apply         申请
     * @param channelAccount 配置信息
     * @return
     */
    @Override
    protected TradeRefundApplyResult internalRefundApply(OrderRefundApply apply, WeiXinChannelAccount channelAccount) {
        RefundRequest request = new RefundRequest(this, channelAccount, apply);
        return request.toResult();
    }

    /**
     * 内部查询订单退款信息
     *
     * @param apply         申请
     * @param channelAccount 配置信息
     * @return
     */
    @Override
    protected TradeOrderRefundInfo internalQueryOrderRefundInfo(OrderRefundQueryApply apply, WeiXinChannelAccount channelAccount) {
        RefundQueryRequest request = new RefundQueryRequest(this, channelAccount, apply);
        return request.toResult();
    }

    /**
     * 内部创建退款通知
     *
     * @param xml
     * @param channelAccount 配置信息
     * @return
     */
    @Override
    protected TradeRefundApplyResult internalCreateRefundNotify(String xml, WeiXinChannelAccount channelAccount) {
        RefundNotifyResponse response = new RefundNotifyResponse(this, channelAccount, xml);
        return response.toResult();
    }
}
