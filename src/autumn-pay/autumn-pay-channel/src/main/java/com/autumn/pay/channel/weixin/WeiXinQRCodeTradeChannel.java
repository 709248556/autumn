package com.autumn.pay.channel.weixin;

import com.autumn.pay.channel.OrderPayApply;
import com.autumn.pay.channel.TradePayApplyResult;
import com.autumn.pay.channel.enums.ChannelType;
import com.autumn.pay.channel.weixin.request.QrCodePayRequest;

/**
 * 微信扫码交易通道
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 17:12
 */
public class WeiXinQRCodeTradeChannel extends AbstractWeiXinTradeChannel {

    /**
     * 通道id
     */
    public static final String CHANNEL_ID = "WeiXin.QRCode";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "微信扫码支付";

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ALL;
    }

    @Override
    public String getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    public String getChannelName() {
        return CHANNEL_NAME;
    }

    /**
     * 内部付款申请
     *
     * @param order         订单
     * @param configureInfo 配置信息
     * @return
     */
    @Override
    protected TradePayApplyResult internalPayApply(OrderPayApply order, WeiXinChannelAccount configureInfo) {
        QrCodePayRequest request = new QrCodePayRequest(this, configureInfo, order);
        return request.toResult();
    }
}
