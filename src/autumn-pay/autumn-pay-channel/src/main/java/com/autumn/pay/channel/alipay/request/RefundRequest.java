package com.autumn.pay.channel.alipay.request;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.autumn.pay.channel.OrderRefundApply;
import com.autumn.pay.channel.PayUtils;
import com.autumn.pay.channel.TradeRefundApplyResult;
import com.autumn.pay.channel.alipay.AbstractAiliPayTradeChannel;
import com.autumn.pay.channel.alipay.AliPayChannelAccount;
import com.autumn.pay.channel.enums.TradeApplyResultState;
import com.autumn.pay.channel.impl.DefaultTradeOrderRefundInfo;
import com.autumn.pay.channel.impl.DefaultTradeRefundApplyResult;
import com.autumn.util.StringUtils;
import com.autumn.util.json.JsonUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 退款请求
 *
 * @author ycg
 */
public class RefundRequest extends AbstractAlipayPayRequest<TradeRefundApplyResult> {

    /**
     *
     */
    private static final long serialVersionUID = 2073335224193239920L;

    private OrderRefundApply apply;

    public RefundRequest(AbstractAiliPayTradeChannel tradeChannel, AliPayChannelAccount channelAccount, OrderRefundApply apply) {
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
        if (StringUtils.isNotNullOrBlank(orderNo) && StringUtils.isNotNullOrBlank(tradeNo)) {
            throw createTradeChannelException("支付宝交易号和商户订单号不能同时为空");
        }
        if (StringUtils.isNotNullOrBlank(apply.getRefundNo())) {
            throw createTradeChannelException("退款批次号不能为空。");
        }
        Map<String, String> reqData = new HashMap<>(16);
        if (!StringUtils.isNullOrEmpty(orderNo)) {
            // 订单支付时传入的商户订单号,不能和 trade_no同时为空
            reqData.put("out_trade_no", orderNo);
        }
        // 支付宝交易号，和商户订单号不能同时为空
        if (!StringUtils.isNullOrEmpty(tradeNo)) {
            reqData.put("trade_no", tradeNo);
        }
        // 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
        reqData.put("refund_amount", PayUtils.getPercentActualMoney(apply.getApplyAmount()).toString());
        // 退款的原因说明
        reqData.put("refund_reason", apply.getRefundReason());
        // 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
        reqData.put("out_request_no", apply.getRefundNo());
        return reqData;
    }

    /**
     * 构建返回结果
     *
     * @param response
     * @return
     */
    private TradeRefundApplyResult toResult(AlipayTradeRefundResponse response) {
        // 退款接口返回code=10000，msg=success，fund_change=Y代表钱已经从支付宝账号上退款出去了
        // 支付宝退款接口为同步接口，电脑网站支付和当面付接口支付的订单退款默认不发送异步通知，没有通知回调
        DefaultTradeOrderRefundInfo refundInfo = new DefaultTradeOrderRefundInfo();
        if (response.isSuccess()) {
            // 支付宝交易号
            refundInfo.setTradeNo(response.getTradeNo());
            // 商户订单号
            refundInfo.setOrderNo(response.getOutTradeNo());
            //退款时间
            refundInfo.setRefundTime(response.getGmtRefundPay());
            // 退款金额
            refundInfo.setRefundAmount(PayUtils.getPercentMoney(new BigDecimal(response.getPresentRefundBuyerAmount())));
            return new DefaultTradeRefundApplyResult(refundInfo, TradeApplyResultState.COMPLETE_SUCCESS, "退款成功");
        }
        if ("ACQ.SYSTEM_ERROR".equalsIgnoreCase(response.getSubCode())) {
            return new DefaultTradeRefundApplyResult(refundInfo, TradeApplyResultState.APPLY_ERROR, "系统错误,等待再次请求[" + response.getSubMsg() + "]");
        }
        if ("ACQ.SELLER_BALANCE_NOT_ENOUGH".equalsIgnoreCase(response.getSubCode())) {
            return new DefaultTradeRefundApplyResult(refundInfo, TradeApplyResultState.BALANCE_INSUFFICIENT_ERROR, "余额不足[" + response.getSubMsg() + "]");
        }
        return new DefaultTradeRefundApplyResult(refundInfo, TradeApplyResultState.INFO_ERROR, response.getSubMsg());
    }

    /**
     * 统一收单交易退款
     *
     * @return
     * @throws Exception
     */
    @Override
    public TradeRefundApplyResult toResult() {
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = this.createAlipayClient();
        // 创建API对应的request类
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JsonUtils.toJSONString(buildReqData()));
        // 通过alipayClient调用API，获得对应的response类
        AlipayTradeRefundResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw createTradeChannelException(e.getErrCode(), e.getErrMsg());
        }
        TradeRefundApplyResult result = toResult(response);
        if (result.getTradeOrderRefundInfo() != null) {
            DefaultTradeOrderRefundInfo refundInfo = (DefaultTradeOrderRefundInfo) result.getTradeOrderRefundInfo();
            refundInfo.setRefundReason(this.apply.getRefundReason());
            refundInfo.setRefundNo(this.apply.getRefundNo());
            if (!response.isSuccess()) {
                refundInfo.setTradeNo(this.apply.getTradeNo());
                refundInfo.setOrderNo(this.apply.getOrderNo());
                refundInfo.setRefundTime(null);
                refundInfo.setRefundAmount(this.apply.getApplyAmount());
            }
        }
        return result;
    }
}