package com.autumn.pay.channel.weixin.response;

import com.autumn.pay.channel.impl.AbstractPayResponse;
import com.autumn.pay.channel.weixin.AbstractWeiXinTradeChannel;
import com.autumn.pay.channel.weixin.WeiXinChannelAccount;

/**
 * 微信响应抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-30 13:58
 */
public abstract class AbstractWeiXinPayResponse<TResult> extends AbstractPayResponse<AbstractWeiXinTradeChannel, WeiXinChannelAccount, TResult> {

    /**
     * @param tradeChannel
     * @param channelAccount
     */
    public AbstractWeiXinPayResponse(AbstractWeiXinTradeChannel tradeChannel, WeiXinChannelAccount channelAccount) {
        super(tradeChannel, channelAccount);
    }
}
