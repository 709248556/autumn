package com.autumn.logistics.channel.impl;

import com.autumn.logistics.channel.LogisticsChannel;
import com.autumn.logistics.channel.LogisticsChannelContext;
import com.autumn.util.channel.AbstractChannelContext;

/**
 * 物流通道实现
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-01 23:48
 **/
public class LogisticsChannelContextImpl extends AbstractChannelContext<LogisticsChannel> implements LogisticsChannelContext {

    /**
     *
     */
    public LogisticsChannelContextImpl() {
        super(100);
    }
}
