package com.autumn.pay.channel.alipay.request;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.autumn.pay.channel.OrderQueryApply;
import com.autumn.pay.channel.PayUtils;
import com.autumn.pay.channel.TradeOrderInfo;
import com.autumn.pay.channel.alipay.AbstractAiliPayTradeChannel;
import com.autumn.pay.channel.alipay.AliPayChannelAccount;
import com.autumn.pay.channel.alipay.AlipayConstants;
import com.autumn.pay.channel.impl.DefaultTradeOrderInfo;
import com.autumn.util.StringUtils;
import com.autumn.util.json.JsonUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 交易查询
 *
 * @author ycg
 */
public class PayQueryRequest extends AbstractAlipayPayRequest<TradeOrderInfo> {


    private OrderQueryApply apply;

    public PayQueryRequest(AbstractAiliPayTradeChannel tradeChannel, AliPayChannelAccount channelAccount, OrderQueryApply apply) {
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
        // 系统内部订单号
        String orderNo = apply.getOrderNo();
        // 支付渠道交易号
        String tradeNo = apply.getTradeNo();
        if (StringUtils.isNullOrEmpty(orderNo) && StringUtils.isNullOrEmpty(tradeNo)) {
            throw createTradeChannelException("支付宝交易号和商户订单号不能同时为空");
        }
        Map<String, String> reqData = new HashMap<>(16);
        if (!StringUtils.isNullOrEmpty(orderNo)) {
            // 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no
            reqData.put("out_trade_no", orderNo);
        }
        if (!StringUtils.isNullOrEmpty(tradeNo)) {
            // 支付宝交易号，和商户订单号不能同时为空
            reqData.put("trade_no", tradeNo);
        }
        return reqData;
    }

    /**
     * 构建返回结果
     *
     * @param response
     * @return
     */

    private TradeOrderInfo toResult(AlipayTradeQueryResponse response) {
        DefaultTradeOrderInfo orderInfo = new DefaultTradeOrderInfo();
        orderInfo.setOrderNo(response.getOutTradeNo());
        orderInfo.setTradeNo(response.getTradeNo());
        orderInfo.setPayTime(response.getSendPayDate());
        orderInfo.setOrderAmount(PayUtils.getPercentMoney(new BigDecimal(response.getTotalAmount())));
        orderInfo.setExtraParam(response.getExtInfos());
        orderInfo.setSubject(response.getSubject());
        orderInfo.setBody(response.getBody());
        return orderInfo;
    }

    /**
     * 统一收单线下交易查询
     *
     * @return
     * @throws Exception
     */
    @Override
    public TradeOrderInfo toResult() {
        AlipayClient alipayClient = this.createAlipayClient();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent(JsonUtils.toJSONString(buildReqData()));
        AlipayTradeQueryResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw createTradeChannelException(e.getErrCode(), e.getErrMsg());
        }
        if (!response.isSuccess()) {
            return null;
        }
        // 查询失败，抛出异常
        String tradeState = response.getTradeStatus();
        if (!tradeState.equals(AlipayConstants.TradeState.TRADE_SUCCESS)) {
            return null;
        }
        return toResult(response);
    }
}