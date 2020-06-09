package com.autumn.logistics.channel.yunda;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 韵达快递通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 09:00
 **/
public class YunDaLogisticsChannel extends AbstractLogisticsChannel<YunDaLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "yunDa";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "韵达快递";

    /**
     * 实例化
     */
    public YunDaLogisticsChannel() {
        super(YunDaLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public YunDaLogisticsChannel(YunDaLogisticsChannelProperties channelDefaultProperties) {
        super(YunDaLogisticsChannelProperties.class, channelDefaultProperties);
    }

    @Override
    public String getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    public String getChannelName() {
        return CHANNEL_NAME;
    }

}
