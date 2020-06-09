package com.autumn.logistics.channel;

import com.autumn.util.channel.Channel;

/**
 * 物流通道
 * <p>
 * 物流查询、下单、推送通道
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-01 22:53
 **/
public interface LogisticsChannel extends Channel {

    /**
     * 获取通道属性类型
     *
     * @return
     */
    Class<? extends AbstractLogisticsChannelProperties> getChannelPropertiesType();


}
