package com.autumn.logistics.channel.sf;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 顺丰快递通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:52
 **/
@Getter
@Setter
public class SFLogisticsChannelProperties extends AbstractLogisticsChannelProperties {
    private static final long serialVersionUID = 527376055773769252L;
    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractLogisticsChannelProperties.PREFIX + ".sf.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "SF" + CHANNEL_BEAN_SUFFIX;
}
