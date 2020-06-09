package com.autumn.pay.channel.alipay;

import com.autumn.pay.channel.AbstractTradeChannelAccount;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付宝交易通道账户
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 15:33
 */
@Getter
@Setter
public class AliPayChannelAccount extends AbstractTradeChannelAccount {

    private static final long serialVersionUID = 4087904059168798302L;

    /**
     * 应用Id
     */
    private String appId;

    /**
     * 加密私钥
     */
    private String appPrivateKey;

    /**
     * 解密公钥
     */
    private String alipayPublicKey;

}
