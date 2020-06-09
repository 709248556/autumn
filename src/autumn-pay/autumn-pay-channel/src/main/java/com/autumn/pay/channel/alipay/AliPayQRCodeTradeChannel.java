package com.autumn.pay.channel.alipay;

import com.autumn.pay.channel.OrderPayApply;
import com.autumn.pay.channel.TradePayApplyResult;
import com.autumn.pay.channel.TradeRefundApplyResult;
import com.autumn.pay.channel.alipay.request.QrCodePayRequest;
import com.autumn.pay.channel.enums.ChannelType;

import java.util.Map;

/**
 * 支付宝扫码交易通道
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 17:23
 */
public class AliPayQRCodeTradeChannel extends AbstractAiliPayTradeChannel {

    /**
     * 通道id
     */
    public static final String CHANNEL_ID = "AiliPay.QRCode";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "支付宝扫码支付";


    @Override
    public ChannelType getChannelType() {
        return ChannelType.WEB;
    }

    @Override
    public String getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    public String getChannelName() {
        return CHANNEL_NAME;
    }

    @Override
    protected TradePayApplyResult internalPayApply(OrderPayApply order, AliPayChannelAccount channelAccount) {
        QrCodePayRequest request = new QrCodePayRequest(this, channelAccount, order);
        return request.toResult();
    }

    @Override
    protected TradeRefundApplyResult internalCreateRefundNotify(Map<String, String> stringStringMap, AliPayChannelAccount channelAccount) {
        //扫码退款无退款通道
        return null;
    }
}
