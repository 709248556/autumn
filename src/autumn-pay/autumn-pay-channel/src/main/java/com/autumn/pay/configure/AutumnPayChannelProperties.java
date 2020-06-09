package com.autumn.pay.configure;

import com.autumn.pay.channel.AbstractPayChannelProperties;
import com.autumn.pay.channel.alipay.AliPayChannelProperties;
import com.autumn.pay.channel.weixin.WeiXinChannelProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 支付通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 20:59
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = AbstractPayChannelProperties.PREFIX)
public class AutumnPayChannelProperties implements Serializable {

    private static final long serialVersionUID = -342778778353018796L;

    /**
     * 支付宝通道属性
     */
    private AliPayChannelProperties alipay = new AliPayChannelProperties();

    /**
     * 微信通道属性
     */
    private WeiXinChannelProperties weixin = new WeiXinChannelProperties();
}
