package com.autumn.pay.channel;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 订单退款申请
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 11:31
 */
public interface OrderRefundApply extends Serializable {

    /**
     * 获取退款批号
     *
     * @return
     */
    String getRefundNo();

    /**
     * 获取订单号(与交易号至少一项不能为空)
     *
     * @return
     */
    String getOrderNo();

    /**
     * 获取交易号(与订单号至少一项不能为空)
     *
     * @return
     */
    String getTradeNo();

    /**
     * 获取订单金额(单位：分)
     *
     * @return
     */
    Long getOrderAmount();

    /**
     * 获取申请时间
     *
     * @return
     */
    Date getApplyTime();

    /**
     * 实际付款时间
     *
     * @return
     */
    Date getPayTime();

    /**
     * 获取申请金额(单位：分)
     *
     * @return
     */
    Long getApplyAmount();

    /**
     * 获取退款原因
     *
     * @return
     */
    String getRefundReason();

    /**
     * 获取扩展参数
     *
     * @return
     */
    String getExtraParam();

    /**
     * 获取参数集合
     *
     * @return
     */
    Map<String, String> getParams();
}
