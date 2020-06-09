package com.autumn.pay.channel.weixin.request;

import com.autumn.pay.channel.OrderQueryApply;
import com.autumn.pay.channel.TradeOrderInfo;
import com.autumn.pay.channel.impl.DefaultTradeOrderInfo;
import com.autumn.pay.channel.weixin.AbstractWeiXinTradeChannel;
import com.autumn.pay.channel.weixin.WeiXinChannelAccount;
import com.autumn.pay.channel.weixin.util.WeiXinPayUtils;
import com.autumn.pay.channel.weixin.util.WeixinPayConstants;
import com.autumn.util.StringUtils;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 交易查询
 *
 * @author ycg
 */
public class PayQueryRequest extends AbstractWeiXinPayRequest<TradeOrderInfo> {

    /**
     *
     */
    private static final long serialVersionUID = 1506180902381017378L;

    private OrderQueryApply apply;

    public PayQueryRequest(AbstractWeiXinTradeChannel tradeChannel,
                           WeiXinChannelAccount channelAccount, OrderQueryApply apply) {
        super(tradeChannel, channelAccount);
        this.apply = apply;
    }

    /**
     * 构建请求参数
     *
     * @return
     */
    @Override
    public Map<String, String> buildReqData() {
        if (StringUtils.isNullOrEmpty(apply.getOrderNo())
                && StringUtils.isNullOrEmpty(apply.getTradeNo())) {
            throw createTradeChannelException("微信的订单号和商户系统内部订单号不能同时为空");
        }
        Map<String, String> reqData = new HashMap<>(16);
        reqData.put("appid", this.getChannelAccount().getAppId());
        reqData.put("mch_id", this.getChannelAccount().getMchId());
        reqData.put("nonce_str", WeiXinPayUtils.generateNonceStr());
        if (!StringUtils.isNullOrEmpty(apply.getOrderNo())) {
            reqData.put("out_trade_no", apply.getOrderNo());
        } else {
            reqData.put("transaction_id", apply.getTradeNo());
        }
        this.signature(reqData);
        return reqData;
    }

    /**
     * 构建返回结果
     *
     * @param resultData
     * @return
     */
    private TradeOrderInfo toResult(Map<String, String> resultData) {
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

    /**
     * 查询订单
     *
     * @return
     * @throws Exception
     */
    @Override
    public TradeOrderInfo toResult() {
        Map<String, String> resultData = this.createRequestMap();
        if (WeiXinPayUtils.isResultSuccess(resultData)
                && WeixinPayConstants.SUCCESS.equalsIgnoreCase(resultData.get("trade_state"))) {
            return toResult(resultData);
        }
        return null;
    }

    @Override
    protected String getApiUrl() {
        return "https://api.mch.weixin.qq.com/pay/orderquery";
    }
}