package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.TradeOrderInfo;
import com.autumn.pay.channel.TradePayApplyResult;
import com.autumn.pay.channel.enums.TradePayApplyResultCallType;

/**
 * 默认交易支付申请结果
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 15:01
 */
public class DefaultTradePayApplyResult implements TradePayApplyResult {
    private static final long serialVersionUID = -1741640082305319579L;

    private String payId;
    private String payUrl;
    private String payParam;
    private TradePayApplyResultCallType callType;
    private TradeOrderInfo tradeOrderInfo;

    public DefaultTradePayApplyResult(String payId, String payUrl, String payParam, TradePayApplyResultCallType callType) {
        this.payId = payId;
        this.payUrl = payUrl;
        this.payParam = payParam;
        this.callType = callType;
        this.tradeOrderInfo = null;
    }

    @Override
    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    @Override
    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    @Override
    public String getPayParam() {
        return payParam;
    }

    public void setPayParam(String payParam) {
        this.payParam = payParam;
    }

    @Override
    public TradePayApplyResultCallType getCallType() {
        return callType;
    }

    public void setCallType(TradePayApplyResultCallType callType) {
        this.callType = callType;
    }

    @Override
    public TradeOrderInfo getTradeOrderInfo() {
        return tradeOrderInfo;
    }

    public void setTradeOrderInfo(TradeOrderInfo tradeOrderInfo) {
        this.tradeOrderInfo = tradeOrderInfo;
    }
}
