package com.autumn.pay.channel.impl;


import com.autumn.pay.channel.TradeChannel;
import com.autumn.pay.channel.TradeChannelAccount;
import com.autumn.pay.channel.TradeChannelException;

/**
 * 响应抽象
 *
 * @param <TTradeChannel>   交易通道
 * @param <TChannelAccount>
 * @param <TResult>
 * @author ycg
 */
public abstract class AbstractPayResponse<TTradeChannel extends TradeChannel, TChannelAccount extends TradeChannelAccount, TResult> {

    private final TTradeChannel tradeChannel;
    private final TChannelAccount channelAccount;

    public AbstractPayResponse(TTradeChannel tradeChannel, TChannelAccount channelAccount) {
        this.tradeChannel = tradeChannel;
        this.channelAccount = channelAccount;
    }

    /**
     * 获取交易通道
     *
     * @return
     */
    public final TTradeChannel getTradeChannel() {
        return this.tradeChannel;
    }

    /**
     * 获取通道账户
     *
     * @return
     */
    public final TChannelAccount getChannelAccount() {
        return this.channelAccount;
    }

    /**
     * 创建交易通道异常
     *
     * @param message 消息
     * @return
     */
    protected TradeChannelException createTradeChannelException(String message) {
        return new TradeChannelException(this.getTradeChannel(), message);
    }

    /**
     * 创建交易通道异常
     *
     * @param message 消息
     * @return
     */
    protected TradeChannelException createTradeChannelException(String code, String message) {
        return new TradeChannelException(this.getTradeChannel(), message + ",code=" + code);
    }

    /**
     * 创建交易通道异常
     *
     * @param message 消息
     * @param cause   原异常
     * @return
     */
    protected TradeChannelException createTradeChannelException(String message, Throwable cause) {
        return new TradeChannelException(this.getTradeChannel(), message, cause);
    }

    /**
     * 构建返回结果
     *
     * @return
     */
    public abstract TResult toResult();
}
