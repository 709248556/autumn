package com.autumn.pay.channel.enums;

import com.autumn.util.ValuedEnum;

/**
 * 交易付款申请结果调用类型
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 12:42
 */
public enum TradePayApplyResultCallType implements ValuedEnum<Integer> {

    /**
     * App Url 参数
     */
    APP_URL(1),

    /**
     * APP JSON参数
     */
    APP_JSON(2),

    /**
     * Web 脚本
     */
    WEB_SCRIPT(11),

    /**
     * Web 二维码
     */
    WEB_QRCODE(12),

    /**
     * Web Post
     */
    WEB_POST(13),

    /**
     * Web Get
     */
    WEB_GET(14),

    /**
     * 等待短信验证
     */
    WAIT_SMS_CHECK(90),

    /**
     * 直接支付成功(如部份协议支付)
     */
    PAY_SUCCESS(99);

    /**
     * 获取值
     */
    private final int value;

    private TradePayApplyResultCallType(int value) {
        this.value = value;
    }

    /**
     * 获取值
     *
     * @return
     */
    @Override
    public Integer value() {
        return this.value;
    }

    /**
     * 值解析
     *
     * @param value 值
     * @return
     */
    public static TradePayApplyResultCallType valueOf(int value) {
        for (TradePayApplyResultCallType tradePayApplyResultCallType : TradePayApplyResultCallType.values()) {
            if (tradePayApplyResultCallType.value() == value) {
                return tradePayApplyResultCallType;
            }
        }
        return null;
    }
}
