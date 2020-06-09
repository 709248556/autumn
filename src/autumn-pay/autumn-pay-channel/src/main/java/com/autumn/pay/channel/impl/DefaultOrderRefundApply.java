package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.OrderRefundApply;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认订单退款申请
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 14:44
 */
@Getter
@Setter
public class DefaultOrderRefundApply implements OrderRefundApply {

    private static final long serialVersionUID = 4942355565195582183L;

    /**
     * 退款编号
     */
    private String refundNo;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 交易号
     */
    private String tradeNo;
    /**
     * 订单金额
     */
    private Long orderAmount;
    /**
     * 申请时间
     */
    private Date applyTime;
    /**
     * 付款时间
     */
    private Date payTime;
    /**
     * 申请金额
     */
    private Long applyAmount;
    /**
     * 退款原因
     */
    private String refundReason;
    /**
     * 扩展参数
     */
    private String extraParam;
    /**
     * 参数集合
     */
    private Map<String, String> params;

    public DefaultOrderRefundApply() {
        this.setParams(new HashMap<>());
    }

}
