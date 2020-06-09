package com.autumn.logistics.channel.zto;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 中通快递通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:53
 **/
@Getter
@Setter
public class ZTOLogisticsChannelProperties extends AbstractLogisticsChannelProperties {

    private static final long serialVersionUID = -8934143082594583425L;
    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractLogisticsChannelProperties.PREFIX + ".zto.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "ZTO" + CHANNEL_BEAN_SUFFIX;
}
