package com.autumn.pay.channel.weixin.request;

import com.autumn.pay.channel.OrderPayApply;
import com.autumn.pay.channel.TradePayApplyResult;
import com.autumn.pay.channel.enums.TradePayApplyResultCallType;
import com.autumn.pay.channel.impl.DefaultTradePayApplyResult;
import com.autumn.pay.channel.weixin.WeiXinChannelAccount;
import com.autumn.pay.channel.weixin.WeiXinQRCodeTradeChannel;
import com.autumn.pay.channel.weixin.util.WeiXinPayUtils;
import com.autumn.util.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 二维码支付请求
 *
 * @author ycg
 */
public class QrCodePayRequest extends AbstractWeiXinPayRequest<TradePayApplyResult> {

    /**
     *
     */
    private static final long serialVersionUID = -2264209561743250461L;

    private OrderPayApply order;

    public QrCodePayRequest(WeiXinQRCodeTradeChannel tradeChannel, WeiXinChannelAccount channelAccount, OrderPayApply order) {
        super(tradeChannel, channelAccount);
        this.order = order;
    }

    /**
     * 构建请求参数
     *
     * @return
     */
    @Override
    public Map<String, String> buildReqData() {
        Map<String, String> reqData = new HashMap<>(16);
        reqData.put("appid", this.getChannelAccount().getAppId());
        reqData.put("mch_id", this.getChannelAccount().getMchId());
        reqData.put("nonce_str", WeiXinPayUtils.generateNonceStr());
        reqData.put("body", this.getLeft(order.getSubject(), 128));
        reqData.put("detail", this.getLeft(order.getBody(), 6000));
        reqData.put("out_trade_no", order.getOrderNo());
        reqData.put("total_fee", order.getOrderAmount().toString());
        reqData.put("spbill_create_ip", order.getClientIp());
        if (order.getStartTime() != null) {
            reqData.put("time_start", DateUtils.dateFormat(order.getStartTime(), DATE_TIME_FORMAT));
        }
        if (order.getExpireTime() != null) {
            reqData.put("time_expire", DateUtils.dateFormat(order.getExpireTime(), DATE_TIME_FORMAT));
        }
        reqData.put("notify_url", this.getTradeChannel().getChannelConfigure().getPayNotifyUrl());
        reqData.put("trade_type", "NATIVE");
        this.signature(reqData);
        return reqData;
    }

    /**
     * 发起统一下单支付请求（扫码支付）
     *
     * @return
     * @throws Exception
     */
    @Override
    public TradePayApplyResult toResult() {
        Map<String, String> resultData = this.createRequestMap();
        if (WeiXinPayUtils.isResultSuccess(resultData)) {
            return new DefaultTradePayApplyResult(resultData.get("prepay_id"),
                    resultData.get("code_url"), "", TradePayApplyResultCallType.WEB_QRCODE);
        }
        throw this.createTradeChannelException(WeiXinPayUtils.getErrorCodeDes(resultData));
    }

    @Override
    protected String getApiUrl() {
        return "https://api.mch.weixin.qq.com/pay/unifiedorder";
    }
}