package com.autumn.logistics.channel.ttk;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 天天快递物流通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:53
 **/
@Getter
@Setter
public class TTKLogisticsChannelProperties extends AbstractLogisticsChannelProperties {
    private static final long serialVersionUID = -4604614274075723946L;
    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractLogisticsChannelProperties.PREFIX + ".ttk.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "TTK" + CHANNEL_BEAN_SUFFIX;
}
