package com.autumn.pay.channel;

import com.autumn.util.channel.Channel;

/**
 * 交易通道异常
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 13:45
 */
public class TradeChannelException extends AutumnPayException implements Channel {

    private static final long serialVersionUID = -3202494007354166365L;
    private final String channelId;
    private final String channelName;

    /**
     * @param channel
     * @param message
     */
    public TradeChannelException(Channel channel, String message) {
        super(message);
        this.channelId = channel.getChannelId();
        this.channelName = channel.getChannelName();
    }

    /**
     * @param channelId
     * @param channelName
     * @param message
     */
    public TradeChannelException(String channelId,
                                 String channelName, String message) {
        super(message);
        this.channelId = channelId;
        this.channelName = channelName;
    }

    /**
     * @param channel
     * @param message
     * @param cause
     */
    public TradeChannelException(Channel channel, String message, Throwable cause) {
        super(message, cause);
        this.channelId = channel.getChannelId();
        this.channelName = channel.getChannelName();
    }

    /**
     * @param channelId
     * @param channelName
     * @param message
     * @param cause
     */
    public TradeChannelException(String channelId,
                                 String channelName, String message, Throwable cause) {
        super(message, cause);
        this.channelId = channelId;
        this.channelName = channelName;
    }

    @Override
    public String getChannelId() {
        return this.channelId;
    }

    @Override
    public String getChannelName() {
        return this.channelName;
    }
}
