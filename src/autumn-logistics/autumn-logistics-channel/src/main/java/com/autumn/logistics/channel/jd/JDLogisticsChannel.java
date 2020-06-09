package com.autumn.logistics.channel.jd;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 京东物流通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 09:35
 **/
public class JDLogisticsChannel extends AbstractLogisticsChannel<JDLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "jd";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "京东物流";

    /**
     * 实例化
     */
    public JDLogisticsChannel() {
        super(JDLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public JDLogisticsChannel(JDLogisticsChannelProperties channelDefaultProperties) {
        super(JDLogisticsChannelProperties.class, channelDefaultProperties);
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
