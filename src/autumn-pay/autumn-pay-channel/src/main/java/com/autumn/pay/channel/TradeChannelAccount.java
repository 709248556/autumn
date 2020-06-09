package com.autumn.pay.channel;

import java.io.Serializable;

/**
 * 交易通道账户
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 12:10
 */
public interface TradeChannelAccount extends Serializable {

    /**
     * 获取账号或id
     *
     * @return
     */
    String getAccountId();

    /**
     * 获取是否支持退款
     *
     * @return
     */
    boolean isSupportRefund();
}
