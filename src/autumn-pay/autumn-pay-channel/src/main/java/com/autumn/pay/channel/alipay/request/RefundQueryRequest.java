package com.autumn.pay.channel.alipay.request;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.autumn.pay.channel.OrderRefundQueryApply;
import com.autumn.pay.channel.PayUtils;
import com.autumn.pay.channel.TradeOrderRefundInfo;
import com.autumn.pay.channel.alipay.AbstractAiliPayTradeChannel;
import com.autumn.pay.channel.alipay.AliPayChannelAccount;
import com.autumn.pay.channel.impl.DefaultTradeOrderRefundInfo;
import com.autumn.util.StringUtils;
import com.autumn.util.json.JsonUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 退款查询
 *
 * @author ycg
 */
public class RefundQueryRequest extends AbstractAlipayPayRequest<TradeOrderRefundInfo> {

    /**
     *
     */
    private static final long serialVersionUID = -6235227035289683201L;

    private OrderRefundQueryApply apply;

    public RefundQueryRequest(AbstractAiliPayTradeChannel tradeChannel, AliPayChannelAccount channelAccount, OrderRefundQueryApply apply) {
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
        String orderNo = apply.getOrderNo();
        String tradeNo = apply.getTradeNo();
        String refundNo = apply.getRefundNo();
        if (StringUtils.isNotNullOrBlank(orderNo) && StringUtils.isNotNullOrBlank(tradeNo)) {
            throw createTradeChannelException("支付宝交易号和商户订单号不能同时为空");
        }
        if (StringUtils.isNotNullOrBlank(refundNo)) {
            throw createTradeChannelException("退款批次号不能为空");
        }
        Map<String, String> reqData = new HashMap<>(16);
        if (!StringUtils.isNotNullOrBlank(tradeNo)) {
            // 支付宝交易号
            reqData.put("trade_no", tradeNo);
        }
        if (!StringUtils.isNotNullOrBlank(orderNo)) {
            // 订单号
            reqData.put("out_trade_no", orderNo);
        }
        // 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
        reqData.put("out_request_no", refundNo);
        return reqData;
    }

    /**
     * 构建返回结果
     *
     * @param response
     * @return
     */

    protected TradeOrderRefundInfo toResult(AlipayTradeFastpayRefundQueryResponse response) {
        DefaultTradeOrderRefundInfo orderRefundInfo = new DefaultTradeOrderRefundInfo();
        orderRefundInfo.setRefundNo(response.getOutRequestNo());
        orderRefundInfo.setTradeNo(response.getTradeNo());
        orderRefundInfo.setOrderNo(response.getOutTradeNo());
        orderRefundInfo.setRefundAmount(PayUtils.getPercentMoney(new BigDecimal(response.getRefundAmount())));
        orderRefundInfo.setRefundTime(response.getGmtRefundPay());
        orderRefundInfo.setRefundReason(response.getRefundReason());
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
        AlipayClient alipayClient = this.createAlipayClient();
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizContent(JsonUtils.toJSONString(buildReqData()));
        AlipayTradeFastpayRefundQueryResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw createTradeChannelException(e.getErrCode(), e.getErrMsg());
        }
        if (!response.isSuccess()) {
            return null;
        }
        return toResult(response);
    }
}