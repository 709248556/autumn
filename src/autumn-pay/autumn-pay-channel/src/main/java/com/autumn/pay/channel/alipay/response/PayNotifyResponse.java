package com.autumn.pay.channel.alipay.response;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.autumn.pay.channel.PayUtils;
import com.autumn.pay.channel.TradeOrderInfo;
import com.autumn.pay.channel.alipay.AbstractAiliPayTradeChannel;
import com.autumn.pay.channel.alipay.AliPayChannelAccount;
import com.autumn.pay.channel.alipay.AlipayConstants;
import com.autumn.pay.channel.impl.DefaultTradeOrderInfo;
import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付通知
 *
 * @author ycg
 */
public class PayNotifyResponse extends AbstractAlipayPayResponse<TradeOrderInfo> {

    /**
     *
     */
    private static final long serialVersionUID = 7424165141119306837L;

    private Map<String, String> notifyMap;

    public PayNotifyResponse(AbstractAiliPayTradeChannel tradeChannel, AliPayChannelAccount channelAccount, Map<String, String> notifyMap) {
        super(tradeChannel, channelAccount);
        this.notifyMap = notifyMap;
    }

    @Override
    public TradeOrderInfo toResult() {
        // 验签
        String signType = notifyMap.get("sign_type");
        boolean isSignSuccess;
        try {
            if (!StringUtils.isNullOrEmpty(signType)) {
                isSignSuccess = AlipaySignature.rsaCheckV1(notifyMap, this.getChannelAccount().getAlipayPublicKey(), AlipayConstants.CHARSET, signType);
            } else {
                isSignSuccess = AlipaySignature.rsaCheckV1(notifyMap, this.getChannelAccount().getAlipayPublicKey(), AlipayConstants.CHARSET);
            }
        } catch (AlipayApiException e) {
            throw createTradeChannelException(e.getErrCode(), e.getErrMsg());
        }
        if (!isSignSuccess) {
            throw createTradeChannelException("验签失败");
        }

        // 支付成功，才会触发通知，如果收到通知，说明已经支付成功了
        DefaultTradeOrderInfo orderInfo = new DefaultTradeOrderInfo();
        orderInfo.setOrderNo(notifyMap.get("out_trade_no"));
        orderInfo.setTradeNo(notifyMap.get("trade_no"));
        orderInfo.setPayTime(DateUtils.parseDate(notifyMap.get("gmt_payment")));
        orderInfo.setOrderAmount(PayUtils.getPercentMoney(new BigDecimal(notifyMap.get("total_amount"))));
        orderInfo.setSubject(notifyMap.get("subject"));
        orderInfo.setBody(notifyMap.get("body"));
        return orderInfo;
    }
}
