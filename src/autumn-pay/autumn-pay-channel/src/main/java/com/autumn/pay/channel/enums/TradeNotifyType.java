package com.autumn.pay.channel.enums;

import com.autumn.util.ValuedEnum;

/**
 * 交易通知类型
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 12:00
 */
public enum TradeNotifyType implements ValuedEnum<Integer> {

    /**
     * 支付
     */
    PAY(1),

    /**
     * 退款
     */
    REFUND(2),

    /**
     * 转账
     */
    TRANSFERS(3);

    /**
     * 获取值
     */
    private final int value;

    private TradeNotifyType(int value) {
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
    public static TradeNotifyType valueOf(int value) {
        for (TradeNotifyType tradeNotifyType : TradeNotifyType.values()) {
            if (tradeNotifyType.value() == value) {
                return tradeNotifyType;
            }
        }
        return null;
    }

}
