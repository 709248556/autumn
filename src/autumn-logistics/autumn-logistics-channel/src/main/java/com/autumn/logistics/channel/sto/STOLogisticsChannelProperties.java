package com.autumn.logistics.channel.sto;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 申通快递通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:53
 **/
@Getter
@Setter
public class STOLogisticsChannelProperties extends AbstractLogisticsChannelProperties {
    private static final long serialVersionUID = -7542658643357312440L;

    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractLogisticsChannelProperties.PREFIX + ".sto.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "STO" + CHANNEL_BEAN_SUFFIX;
}
