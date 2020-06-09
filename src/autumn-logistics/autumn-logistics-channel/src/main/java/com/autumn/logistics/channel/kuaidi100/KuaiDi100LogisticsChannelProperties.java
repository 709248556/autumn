package com.autumn.logistics.channel.kuaidi100;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 快递100通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:52
 **/
@Getter
@Setter
public class KuaiDi100LogisticsChannelProperties extends AbstractLogisticsChannelProperties {
    private static final long serialVersionUID = -1384180229695173418L;

    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractLogisticsChannelProperties.PREFIX + ".kuaidi100.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "KuaiDi100" + CHANNEL_BEAN_SUFFIX;
}
