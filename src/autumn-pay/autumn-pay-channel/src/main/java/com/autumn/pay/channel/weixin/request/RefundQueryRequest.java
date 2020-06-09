package com.autumn.pay.channel.weixin.request;

import com.autumn.pay.channel.OrderRefundQueryApply;
import com.autumn.pay.channel.TradeOrderRefundInfo;
import com.autumn.pay.channel.impl.DefaultTradeOrderRefundInfo;
import com.autumn.pay.channel.weixin.AbstractWeiXinTradeChannel;
import com.autumn.pay.channel.weixin.WeiXinChannelAccount;
import com.autumn.pay.channel.weixin.util.WeiXinPayUtils;
import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 退款查询
 *
 * @author ycg
 */
public class RefundQueryRequest extends AbstractWeiXinPayRequest<TradeOrderRefundInfo> {

    /**
     *
     */
    private static final long serialVersionUID = 833640728890082599L;

    private OrderRefundQueryApply apply;

    public RefundQueryRequest(AbstractWeiXinTradeChannel tradeChannel, WeiXinChannelAccount channelAccount, OrderRefundQueryApply apply) {
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
        if (StringUtils.isNullOrEmpty(apply.getRefundNo())) {
            throw createTradeChannelException("商户退款单号不能为空");
        }
        Map<String, String> reqData = new HashMap<>(16);
        reqData.put("appid", this.getChannelAccount().getAppId());
        reqData.put("mch_id", this.getChannelAccount().getMchId());
        reqData.put("nonce_str", WeiXinPayUtils.generateNonceStr());
        reqData.put("out_refund_no", apply.getRefundNo());
        this.signature(reqData);
        return reqData;
    }

    /**
     * 构建返回结果
     *
     * @param resultData
     * @return
     */
    private TradeOrderRefundInfo toResult(Map<String, String> resultData) {
        DefaultTradeOrderRefundInfo orderRefundInfo = new DefaultTradeOrderRefundInfo();
        orderRefundInfo.setRefundNo(apply.getRefundNo());
        orderRefundInfo.setTradeNo(resultData.get("transaction_id"));
        orderRefundInfo.setOrderNo(resultData.get("out_trade_no"));
        orderRefundInfo.setRefundAmount(Long.parseLong(resultData.get("refund_fee_0")));
        orderRefundInfo.setRefundTime(DateUtils.parseDate(resultData.get("refund_success_time_0")));
        return orderRefundInfo;
    }

    /**
     * 退款查询
     *
     * @return
     * @throws Exception
     */
    @Override
    public TradeOrderRefundInfo toResult() {
        Map<String, String> resultData = this.createRequestMap();
        if (WeiXinPayUtils.isResultSuccess(resultData)) {
            return toResult(resultData);
        }
        return null;
    }

    @Override
    protected String getApiUrl() {
        return "https://api.mch.weixin.qq.com/pay/refundquery";
    }

}