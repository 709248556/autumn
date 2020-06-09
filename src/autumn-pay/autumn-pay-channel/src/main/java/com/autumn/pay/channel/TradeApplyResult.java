package com.autumn.pay.channel;

import com.autumn.pay.channel.enums.TradeApplyResultState;

import java.io.Serializable;

/**
 * 交易申请结果
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 12:52
 */
public interface TradeApplyResult extends Serializable {

    /**
     * 获取结果状态
     *
     * @return
     */
    TradeApplyResultState getResultState();

    /**
     * 获取结果状态消息
     *
     * @return
     */
    String getResultStateMessage();
}
