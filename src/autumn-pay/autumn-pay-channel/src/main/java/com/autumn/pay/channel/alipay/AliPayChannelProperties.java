package com.autumn.pay.channel.alipay;

import com.autumn.pay.channel.AbstractPayChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付宝通道属性
 *
 * @author ycg
 */
@Getter
@Setter
public class AliPayChannelProperties extends AbstractPayChannelProperties {
    private static final long serialVersionUID = -2619326324576310337L;

    /**
     * 属性前缀
     */
    public final static String PREFIX = AbstractPayChannelProperties.PREFIX + ".alipay";

    /**
     * qrCode bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY_QR_CODE = PREFIX + ".qrCode.enable";

    /**
     * qrCode Bean 名称
     */
    public static final String BEAN_NAME_QR_CODE = CHANNEL_BEAN_PREFIX + "AlipayQRCode" + CHANNEL_BEAN_SUFFIX;

    /**
     * 扫码支付
     */
    private AliPayChannelAccount qrCode = new AliPayChannelAccount();

}
