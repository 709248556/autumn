package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.TradeOrderRefundInfo;
import com.autumn.pay.channel.TradeRefundApplyResult;
import com.autumn.pay.channel.enums.TradeApplyResultState;

/**
 * 默认交易退款申请结果
 * @author ycg
 */
public class DefaultTradeRefundApplyResult implements TradeRefundApplyResult {

    /**
     *
     */
    private static final long serialVersionUID = 7275662837772871700L;

    private TradeOrderRefundInfo tradeOrderRefundInfo;
    private TradeApplyResultState resultState;
    private String resultStateMessage;

    public DefaultTradeRefundApplyResult(TradeOrderRefundInfo tradeOrderRefundInfo, TradeApplyResultState resultState, String resultStateMessage) {
        this.tradeOrderRefundInfo = tradeOrderRefundInfo;
        this.resultState = resultState;
        this.resultStateMessage = resultStateMessage;
    }

    @Override
    public TradeOrderRefundInfo getTradeOrderRefundInfo() {
        return tradeOrderRefundInfo;
    }

    public void setTradeOrderRefundInfo(TradeOrderRefundInfo tradeOrderRefundInfo) {
        this.tradeOrderRefundInfo = tradeOrderRefundInfo;
    }

    @Override
    public TradeApplyResultState getResultState() {
        return resultState;
    }

    public void setResultState(TradeApplyResultState resultState) {
        this.resultState = resultState;
    }

    @Override
    public String getResultStateMessage() {
        return resultStateMessage;
    }

    public void setResultStateMessage(String resultStateMessage) {
        this.resultStateMessage = resultStateMessage;
    }
}
