package com.autumn.pay.channel.weixin.response;

import com.autumn.pay.channel.TradeOrderInfo;
import com.autumn.pay.channel.impl.DefaultTradeOrderInfo;
import com.autumn.pay.channel.weixin.AbstractWeiXinTradeChannel;
import com.autumn.pay.channel.weixin.WeiXinChannelAccount;
import com.autumn.pay.channel.weixin.util.WeiXinPayUtils;
import com.autumn.pay.channel.weixin.util.WeixinPayConstants;

import java.text.ParseException;
import java.util.Map;

/**
 * 支付通知
 *
 * @author ycg
 */
public class PayNotifyResponse extends AbstractWeiXinPayResponse<TradeOrderInfo> {

    private String xml;

    public PayNotifyResponse(AbstractWeiXinTradeChannel tradeChannel, WeiXinChannelAccount channelAccount, String xml) {
        super(tradeChannel, channelAccount);
        this.xml = xml;
    }

    @Override
    public TradeOrderInfo toResult() {
        Map<String, String> resultData = WeiXinPayUtils.processResponseXml(xml, "", this.getChannelAccount().getKey());
        if (!WeiXinPayUtils.isResultSuccess(resultData)) {
            throw this.createTradeChannelException(WeiXinPayUtils.getErrorCodeDes(resultData));
        }
        DefaultTradeOrderInfo orderInfo = new DefaultTradeOrderInfo();
        orderInfo.setOrderNo(resultData.get("out_trade_no"));
        orderInfo.setTradeNo(resultData.get("transaction_id"));
        try {
            orderInfo.setPayTime(WeixinPayConstants.FORMAT_DATE_TIME.parse(resultData.get("time_end")));
        } catch (ParseException e) {
            throw createTradeChannelException("日期转换错误");
        }
        orderInfo.setOrderAmount(Long.parseLong(resultData.get("total_fee")));
        orderInfo.setBankType(resultData.get("bank_type"));
        return orderInfo;
    }
}
