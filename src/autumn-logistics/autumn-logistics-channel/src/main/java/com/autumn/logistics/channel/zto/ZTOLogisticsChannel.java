package com.autumn.logistics.channel.zto;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 中通快递通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 08:59
 **/
public class ZTOLogisticsChannel extends AbstractLogisticsChannel<ZTOLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "zto";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "中通快递";

    /**
     * 实例化
     */
    public ZTOLogisticsChannel() {
        super(ZTOLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public ZTOLogisticsChannel(ZTOLogisticsChannelProperties channelDefaultProperties) {
        super(ZTOLogisticsChannelProperties.class, channelDefaultProperties);
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
