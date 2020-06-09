package com.autumn.pay.channel.weixin.response;


import com.autumn.pay.channel.TradeRefundApplyResult;
import com.autumn.pay.channel.enums.TradeApplyResultState;
import com.autumn.pay.channel.impl.DefaultTradeOrderRefundInfo;
import com.autumn.pay.channel.impl.DefaultTradeRefundApplyResult;
import com.autumn.pay.channel.weixin.AbstractWeiXinTradeChannel;
import com.autumn.pay.channel.weixin.WeiXinChannelAccount;
import com.autumn.pay.channel.weixin.util.WeiXinPayUtils;
import com.autumn.pay.channel.weixin.util.WeixinPayConstants;
import com.autumn.util.DateUtils;

import java.util.Map;

/**
 * 退款通知
 *
 * @author ycg
 */
public class RefundNotifyResponse extends AbstractWeiXinPayResponse<TradeRefundApplyResult> {

    private String xml;

    public RefundNotifyResponse(AbstractWeiXinTradeChannel tradeChannel, WeiXinChannelAccount channelAccount, String xml) {
        super(tradeChannel, channelAccount);
        this.xml = xml;
    }

    @Override
    public TradeRefundApplyResult toResult() {
        Map<String, String> resultData = WeiXinPayUtils.processResponseXml(xml, "", this.getChannelAccount().getKey());
        if (!WeiXinPayUtils.isResultSuccess(resultData)) {
            throw this.createTradeChannelException(WeiXinPayUtils.getErrorCodeDes(resultData));
        }
        DefaultTradeOrderRefundInfo tradeOrderRefundInfo = new DefaultTradeOrderRefundInfo();
        tradeOrderRefundInfo.setRefundNo(resultData.get("out_refund_no"));
        tradeOrderRefundInfo.setTradeNo(resultData.get("transaction_id"));
        tradeOrderRefundInfo.setOrderNo(resultData.get("out_trade_no"));
        tradeOrderRefundInfo.setRefundAmount(Long.parseLong(resultData.get("refund_fee")));
        tradeOrderRefundInfo.setRefundTime(DateUtils.parseDate(resultData.get("success_time")));

        String refundState = resultData.get("refund_status");
        String refundMsg = WeixinPayConstants.RefundState.getRefundMsg(refundState);
        TradeApplyResultState resultState = TradeApplyResultState.APPLY_ERROR;
        switch (refundState) {
            case WeixinPayConstants.RefundState.SUCCESS:
                resultState = TradeApplyResultState.COMPLETE_SUCCESS;
                break;
            case WeixinPayConstants.RefundState.CHANGE:
                resultState = TradeApplyResultState.APPLY_ERROR;
                break;
            case WeixinPayConstants.RefundState.REFUNDCLOSE:
                resultState = TradeApplyResultState.INFO_ERROR;
                break;
            default:
                break;
        }
        return new DefaultTradeRefundApplyResult(tradeOrderRefundInfo, resultState, refundMsg);
    }
}
