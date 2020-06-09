package com.autumn.pay.channel.alipay;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 */
public class AlipayConstants {

    /**
     * 支付网关
     */
    public static final String SERVER_URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 报文格式
     */
    public static final String FORMAT = "json";

    /**
     * 字符编码
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 加密方式
     */
    public static final String SIGN_TYPE = "RSA2";

    /**
     * 扫码交易状态
     */
    public static class TradeState {
        public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        public static final String TRADE_CLOSED = "TRADE_CLOSED";
        public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
        public static final String TRADE_FINISHED = "TRADE_FINISHED";

        private static Map<String, String> tradeMsg = new HashMap<>();

        static {
            tradeMsg.put(WAIT_BUYER_PAY, "交易创建，等待买家付款");
            tradeMsg.put(TRADE_CLOSED, "未付款交易超时关闭，或支付完成后全额退款");
            tradeMsg.put(TRADE_SUCCESS, "交易支付成功");
            tradeMsg.put(TRADE_FINISHED, "交易结束，不可退款");
        }

        public static String getTradeMsg(String tradeState) {
            return tradeMsg.get(tradeState);
        }
    }
}

