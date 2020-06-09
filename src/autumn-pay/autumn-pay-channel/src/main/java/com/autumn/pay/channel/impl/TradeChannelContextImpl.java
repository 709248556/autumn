package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.TradeChannel;
import com.autumn.pay.channel.TradeChannelContext;
import com.autumn.util.channel.AbstractChannelContext;

/**
 * 通道上下文实现
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-01 23:59
 **/
public class TradeChannelContextImpl extends AbstractChannelContext<TradeChannel> implements TradeChannelContext {

    public TradeChannelContextImpl() {
        super(50);
    }

}
