package com.autumn.pay.channel.weixin.request;


import com.autumn.pay.channel.OrderRefundApply;
import com.autumn.pay.channel.TradeRefundApplyResult;
import com.autumn.pay.channel.enums.TradeApplyResultState;
import com.autumn.pay.channel.impl.DefaultTradeOrderRefundInfo;
import com.autumn.pay.channel.impl.DefaultTradeRefundApplyResult;
import com.autumn.pay.channel.weixin.AbstractWeiXinTradeChannel;
import com.autumn.pay.channel.weixin.WeiXinChannelAccount;
import com.autumn.pay.channel.weixin.util.WeiXinPayUtils;
import com.autumn.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ycg
 */
public class RefundRequest extends AbstractWeiXinPayRequest<TradeRefundApplyResult> {

    /**
     *
     */
    private static final long serialVersionUID = 3862098929127329458L;


    private OrderRefundApply apply;

    public RefundRequest(AbstractWeiXinTradeChannel tradeChannel, WeiXinChannelAccount channelAccount, OrderRefundApply apply) {
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
        Map<String, String> reqData = new HashMap<>(20);
        reqData.put("appid", this.getChannelAccount().getAppId());
        reqData.put("mch_id", this.getChannelAccount().getMchId());
        reqData.put("nonce_str", WeiXinPayUtils.generateNonceStr());
        if (!StringUtils.isNullOrEmpty(apply.getOrderNo())) {
            reqData.put("out_trade_no", apply.getOrderNo());
        } else {
            reqData.put("transaction_id", apply.getTradeNo());
        }
        reqData.put("out_refund_no", apply.getRefundNo());
        reqData.put("total_fee", apply.getOrderAmount().toString());
        reqData.put("refund_fee", apply.getApplyAmount().toString());
        if (!StringUtils.isNullOrEmpty(apply.getRefundReason())) {
            reqData.put("refund_desc", apply.getRefundReason());
        }
        if (StringUtils.isNotNullOrBlank(this.getTradeChannel().getChannelConfigure().getRefundNotifyUrl())) {
            reqData.put("notify_url", this.getTradeChannel().getChannelConfigure().getRefundNotifyUrl());
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
    private TradeRefundApplyResult toResult(Map<String, String> resultData) {
        DefaultTradeOrderRefundInfo tradeOrderRefundInfo = new DefaultTradeOrderRefundInfo();
        tradeOrderRefundInfo.setRefundNo(resultData.get("out_refund_no"));
        tradeOrderRefundInfo.setTradeNo(resultData.get("transaction_id"));
        tradeOrderRefundInfo.setOrderNo(resultData.get("out_trade_no"));
        tradeOrderRefundInfo.setRefundAmount(Long.parseLong(resultData.get("refund_fee")));
        tradeOrderRefundInfo.setRefundReason(apply.getRefundReason());
        tradeOrderRefundInfo.setRefundTime(new Date());
        return new DefaultTradeRefundApplyResult(tradeOrderRefundInfo,
                TradeApplyResultState.COMPLETE_SUCCESS,
                "退款成功");
    }

    /**
     * 申请退款
     *
     * @return
     * @throws Exception
     */
    @Override
    public TradeRefundApplyResult toResult() {
        Map<String, String> resultData = this.createRequestWithCertMap();
        if (WeiXinPayUtils.isResultSuccess(resultData)) {
            return toResult(resultData);
        }
        if ("INVALID_REQ_TOO_MUCH".equalsIgnoreCase(WeiXinPayUtils.getErrorCode(resultData))) {
            return new DefaultTradeRefundApplyResult(null,
                    TradeApplyResultState.APPLY_ERROR,
                    "无效请求过多");
        }
        if ("NOTENOUGH".equalsIgnoreCase(WeiXinPayUtils.getErrorCode(resultData))) {
            return new DefaultTradeRefundApplyResult(null,
                    TradeApplyResultState.BALANCE_INSUFFICIENT_ERROR,
                    "余额不足");
        }
        return new DefaultTradeRefundApplyResult(null,
                TradeApplyResultState.INFO_ERROR,
                WeiXinPayUtils.getErrorCodeDes(resultData));
    }

    @Override
    protected String getApiUrl() {
        return "https://api.mch.weixin.qq.com/secapi/pay/refund";
    }
}