package com.autumn.pay.channel.enums;

import com.autumn.util.ValuedEnum;

/**
 * 交易申请结果状态
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 12:53
 */
public enum TradeApplyResultState implements ValuedEnum<Integer> {

    /**
     * 账户余额不足
     */
    BALANCE_INSUFFICIENT_ERROR(-10),

    /**
     * 申请出错
     */
    APPLY_ERROR(-1),

    /**
     * 尚未执行，未知结果
     */
    NONE(0),

    /**
     * 完成，并且已成功
     */
    COMPLETE_SUCCESS(1),

    /**
     * 申请成功，等待通知
     */
    APPLY_SUCCESS_WAIT_NOTIFY(2),

    /**
     * 信息出错(不可能再成功)
     */
    INFO_ERROR(3);

    /**
     * 获取值
     */
    private final int value;

    private TradeApplyResultState(int value) {
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
    public static TradeApplyResultState valueOf(int value) {
        for (TradeApplyResultState tradeApplyResultState : TradeApplyResultState.values()) {
            if (tradeApplyResultState.value() == value) {
                return tradeApplyResultState;
            }
        }
        return null;
    }

}
