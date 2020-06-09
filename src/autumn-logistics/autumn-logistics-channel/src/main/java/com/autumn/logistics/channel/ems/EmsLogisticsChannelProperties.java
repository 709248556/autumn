package com.autumn.logistics.channel.ems;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 邮政Ems通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:52
 **/
@Getter
@Setter
public class EmsLogisticsChannelProperties extends AbstractLogisticsChannelProperties {
    private static final long serialVersionUID = -603577791008745428L;

    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractLogisticsChannelProperties.PREFIX + ".ems.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "Ems" + CHANNEL_BEAN_SUFFIX;
}
