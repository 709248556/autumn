package com.autumn.pay.channel.alipay.request;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.autumn.pay.channel.alipay.AbstractAiliPayTradeChannel;
import com.autumn.pay.channel.alipay.AliPayChannelAccount;
import com.autumn.pay.channel.alipay.AlipayConstants;
import com.autumn.pay.channel.impl.AbstractPayRequest;

/**
 * 支付宝请求抽象
 *
 * @param <TResult> 结果
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-30 13:22
 */
public abstract class AbstractAlipayPayRequest<TResult>
        extends AbstractPayRequest<AbstractAiliPayTradeChannel, AliPayChannelAccount, TResult> {

    /**
     * @param tradeChannel
     * @param channelAccount
     */
    public AbstractAlipayPayRequest(AbstractAiliPayTradeChannel tradeChannel, AliPayChannelAccount channelAccount) {
        super(tradeChannel, channelAccount);
    }

    /**
     * 创建客户端
     *
     * @return
     */
    protected AlipayClient createAlipayClient() {
        return new DefaultAlipayClient(AlipayConstants.SERVER_URL,
                this.getChannelAccount().getAppId(),
                this.getChannelAccount().getAppPrivateKey(),
                AlipayConstants.FORMAT,
                AlipayConstants.CHARSET, this.getChannelAccount().getAlipayPublicKey(),
                AlipayConstants.SIGN_TYPE);
    }

}
