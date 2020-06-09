package com.autumn.sms.channel.impl;

import com.autumn.sms.channel.SmsChannel;
import com.autumn.sms.channel.SmsChannelContext;
import com.autumn.sms.channel.SmsChannelListen;
import com.autumn.util.channel.AbstractChannelContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 短信通道实现
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 00:12
 **/
public class SmsChannelContextImpl extends AbstractChannelContext<SmsChannel> implements SmsChannelContext {

    private Set<SmsChannelListen> smsChannelListens;

    /**
     *
     */
    public SmsChannelContextImpl() {
        super(16);
        this.smsChannelListens = new HashSet<>(16);
    }

    @Override
    public void registerChannelListen(SmsChannelListen channelListen) {
        this.smsChannelListens.add(channelListen);
    }

    @Override
    public Collection<SmsChannelListen> getSmsChannelListens() {
        return this.smsChannelListens;
    }
}
