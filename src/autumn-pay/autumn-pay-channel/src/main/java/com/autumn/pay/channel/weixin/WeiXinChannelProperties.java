package com.autumn.pay.channel.weixin;

import com.autumn.pay.channel.AbstractPayChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信通道属性
 *
 * @author 老码农
 */
@Getter
@Setter
public class WeiXinChannelProperties extends AbstractPayChannelProperties {

    private static final long serialVersionUID = -6833182704412722083L;

    /**
     * 属性前缀
     */
    public final static String PREFIX = AbstractPayChannelProperties.PREFIX + ".weixin";

    /**
     * qrCode bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY_QR_CODE = PREFIX + ".qrCode.enable";

    /**
     * qrCode Bean 名称
     */
    public static final String BEAN_NAME_QR_CODE = CHANNEL_BEAN_PREFIX + "WeixinQRCode" + CHANNEL_BEAN_SUFFIX;

    /**
     * 微信扫码支付
     */
    private WeiXinChannelAccount qrCode = new WeiXinChannelAccount();

}
