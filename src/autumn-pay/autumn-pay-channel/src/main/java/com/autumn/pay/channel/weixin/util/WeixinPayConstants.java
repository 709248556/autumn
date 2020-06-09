package com.autumn.pay.channel.weixin.util;

import org.apache.http.client.HttpClient;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 *
 * @author ycg
 */
public class WeixinPayConstants {

    /**
     *
     */
    public enum SignType {
        /**
         *
         */
        MD5,
        /**
         *
         */
        HMACSHA256
    }

    public static final String DOMAIN_API = "https://api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "https://api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "https://apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "https://apius.mch.weixin.qq.com";

    public static final String FAIL = "FAIL";
    public static final String SUCCESS = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String RETURN_CODE = "return_code";
    public static final String RETURN_MSG = "return_msg";
    public static final String RESULT_CODE = "result_code";
    public static final String ERR_CODE = "err_code";
    public static final String ERR_CODE_DES = "err_code_des";

    public static final String PAYSDK_VERSION = "AutunmPaySDK/3.0.0";
    public static final String USER_AGENT = PAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

    public static final String MICROPAY_URL_SUFFIX = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

    public static final String SANDBOX_MICROPAY_URL_SUFFIX = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";

    /**
     * 日期时间格式化(yyyyMMddHHmmss)
     */
    public final static SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 扫码交易状态
     */
    public static class TradeState {
        public static final String SUCCESS = "SUCCESS";
        public static final String REFUND = "REFUND";
        public static final String NOTPAY = "NOTPAY";
        public static final String CLOSED = "CLOSED";
        public static final String REVOKED = "REVOKED";
        public static final String USERPAYING = "USERPAYING";
        public static final String PAYERROR = "PAYERROR";

        private static Map<String, String> TRADE_MSG = new HashMap<>(16);

        static {
            TRADE_MSG.put(SUCCESS, "支付成功");
            TRADE_MSG.put(REFUND, "转入退款");
            TRADE_MSG.put(NOTPAY, "未支付");
            TRADE_MSG.put(CLOSED, "已关闭");
            TRADE_MSG.put(REVOKED, "已撤销（付款码支付）");
            TRADE_MSG.put(USERPAYING, "用户支付中（付款码支付）");
            TRADE_MSG.put(PAYERROR, "支付失败(其他原因，如银行返回失败)");
        }

        public static String getTradeMsg(String tradeState) {
            return TRADE_MSG.get(tradeState);
        }
    }

    /**
     * 退款状态
     */
    public static class RefundState {
        public static final String SUCCESS = "SUCCESS";
        public static final String REFUNDCLOSE = "REFUNDCLOSE";
        public static final String PROCESSING = "PROCESSING";
        public static final String CHANGE = "CHANGE";

        private static Map<String, String> REFUND_MSG = new HashMap<>();

        static {
            REFUND_MSG.put(SUCCESS, "退款成功");
            REFUND_MSG.put(REFUNDCLOSE, "退款关闭");
            REFUND_MSG.put(PROCESSING, "退款处理中");
            REFUND_MSG.put(CHANGE, "退款异常，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款");
        }

        public static String getRefundMsg(String tradeState) {
            return REFUND_MSG.get(tradeState);
        }
    }
}

