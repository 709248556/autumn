package com.autumn.pay.channel;

import lombok.Getter;
import lombok.Setter;

/**
 * 交易通道账户抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 15:35
 */
@Getter
@Setter
public class AbstractTradeChannelAccount implements TradeChannelAccount {
    private static final long serialVersionUID = -5402380967344855791L;

    /**
     * 是否启用
     */
    private boolean enable = false;

    /**
     * 账号或id
     */
    private String accountId;

    /**
     * 否支持退款
     */
    private boolean supportRefund;

}
