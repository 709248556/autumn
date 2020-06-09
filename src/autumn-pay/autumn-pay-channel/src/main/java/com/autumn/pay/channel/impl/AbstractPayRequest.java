package com.autumn.pay.channel.impl;

import com.autumn.pay.channel.TradeChannel;
import com.autumn.pay.channel.TradeChannelAccount;
import com.autumn.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 支付请求抽象
 *
 * @param <TTradeChannel>   交易通道
 * @param <TChannelAccount>
 * @param <TResult>
 * @author 老码农
 */
public abstract class AbstractPayRequest<TTradeChannel extends TradeChannel, TChannelAccount extends TradeChannelAccount, TResult>
        extends AbstractPayResponse<TTradeChannel, TChannelAccount, TResult> {

    /**
     * 日期时间格式化(yyyyMMddHHmmss)
     */
    public final static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 实例化
     *
     * @param tradeChannel
     * @param channelAccount
     */
    public AbstractPayRequest(TTradeChannel tradeChannel, TChannelAccount channelAccount) {
        super(tradeChannel, channelAccount);
    }

    /**
     * 构建请求参数
     *
     * @return
     */
    protected abstract Map<String, String> buildReqData();

    /**
     * 获取长度
     *
     * @param value  值
     * @param length 长度
     * @return
     */
    protected String getLeft(String value, int length) {
        if (value == null) {
            return "";
        }
        return StringUtils.getLeft(value, length);
    }

}
