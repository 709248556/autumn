package com.autumn.pay.channel.alipay.response;


import com.autumn.pay.channel.alipay.AbstractAiliPayTradeChannel;
import com.autumn.pay.channel.alipay.AliPayChannelAccount;
import com.autumn.pay.channel.impl.AbstractPayResponse;

/**
 * 支付宝响应抽象
 *
 * @param <TResult> 结果
 * @Author 老码农
 * @Date 2019-09-30 13:16
 */
public abstract class AbstractAlipayPayResponse<TResult>
        extends AbstractPayResponse<AbstractAiliPayTradeChannel, AliPayChannelAccount, TResult> {

    /**
     * @param tradeChannel
     * @param channelAccount
     */
    public AbstractAlipayPayResponse(AbstractAiliPayTradeChannel tradeChannel, AliPayChannelAccount channelAccount) {
        super(tradeChannel, channelAccount);
    }
}
