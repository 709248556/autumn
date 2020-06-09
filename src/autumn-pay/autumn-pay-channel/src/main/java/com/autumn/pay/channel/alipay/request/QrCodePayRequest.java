package com.autumn.pay.channel.alipay.request;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.autumn.pay.channel.OrderPayApply;
import com.autumn.pay.channel.PayUtils;
import com.autumn.pay.channel.TradePayApplyResult;
import com.autumn.pay.channel.alipay.AbstractAiliPayTradeChannel;
import com.autumn.pay.channel.alipay.AliPayChannelAccount;
import com.autumn.pay.channel.enums.TradePayApplyResultCallType;
import com.autumn.pay.channel.impl.DefaultTradePayApplyResult;
import com.autumn.util.json.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ycg
 */
public class QrCodePayRequest extends AbstractAlipayPayRequest<TradePayApplyResult> {

    /**
     *
     */
    private static final long serialVersionUID = 8774756902874062641L;

    private OrderPayApply order;

    public QrCodePayRequest(AbstractAiliPayTradeChannel tradeChannel, AliPayChannelAccount channelAccount, OrderPayApply order) {
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
        // 商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复
        reqData.put("out_trade_no", order.getOrderNo());
        // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        reqData.put("total_amount", PayUtils.getPercentActualMoney(order.getOrderAmount()).toString());
        // 订单标题
        reqData.put("subject", this.getLeft(order.getSubject(), 256));
        reqData.put("body", this.getLeft(order.getBody(), 128));
        return reqData;
    }

    /**
     * 统一收单线下交易预创建（扫码支付）
     *
     * @return
     * @throws Exception
     */
    @Override
    public TradePayApplyResult toResult() {
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = this.createAlipayClient();
        // 创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(this.getTradeChannel().getChannelConfigure().getPayNotifyUrl());
        request.setBizContent(JsonUtils.toJSONString(buildReqData()));
        // 通过alipayClient调用API，获得对应的response类
        AlipayTradePrecreateResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw createTradeChannelException(e.getErrCode(), e.getErrMsg());
        }
        if (!response.isSuccess()) {
            throw createTradeChannelException(response.getSubCode(), response.getSubMsg());
        }
        return new DefaultTradePayApplyResult("", response.getQrCode(), "", TradePayApplyResultCallType.WEB_QRCODE);
    }
}