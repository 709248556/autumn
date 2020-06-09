package com.autumn.logistics.channel.yto;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 圆通快递通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 09:01
 **/
public class YTOLogisticsChannel extends AbstractLogisticsChannel<YTOLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "yto";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "圆通快递";

    /**
     * 实例化
     */
    public YTOLogisticsChannel() {
        super(YTOLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public YTOLogisticsChannel(YTOLogisticsChannelProperties channelDefaultProperties) {
        super(YTOLogisticsChannelProperties.class, channelDefaultProperties);
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
