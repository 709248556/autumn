package com.autumn.pay.channel;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易订单信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 12:45
 */
public interface TradeOrderInfo extends Serializable {

    /**
     * 获取订单号
     *
     * @return
     */
    String getOrderNo();

    /**
     * 获取交易号
     *
     * @return
     */
    String getTradeNo();

    /**
     * 付款时间
     *
     * @return
     */
    Date getPayTime();

    /**
     * 订单金额
     *
     * @return
     */
    Long getOrderAmount();

    /**
     * 已退款金额
     *
     * @return
     */
    Long getRefundAmount();

    /**
     * 通道模式
     *
     * @return
     */
    String getChannelMode();

    /**
     * 对方账号
     *
     * @return
     */
    String getOtherPartyAccount();

    /**
     * 对方账号Id
     *
     * @return
     */
    String getOtherPartyAccountId();

    /**
     * 银行类型
     *
     * @return
     */
    String getBankType();

    /**
     * 银行名称
     *
     * @return
     */
    String getBankName();

    /**
     * 获取扩展参数
     *
     * @return
     */
    String getExtraParam();

    /**
     * 标题
     *
     * @return
     */
    String getSubject();

    /**
     * 详情(体)
     *
     * @return
     */
    String getBody();
}
