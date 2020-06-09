package com.autumn.pay.channel.weixin.request;


import com.autumn.pay.channel.impl.AbstractPayRequest;
import com.autumn.pay.channel.weixin.AbstractWeiXinTradeChannel;
import com.autumn.pay.channel.weixin.WeiXinChannelAccount;
import com.autumn.pay.channel.weixin.util.WeiXinPayHttpUtils;
import com.autumn.pay.channel.weixin.util.WeiXinPayUtils;
import com.autumn.pay.channel.weixin.util.WeiXinXmlUtils;
import com.autumn.pay.channel.weixin.util.WeixinPayConstants;

import java.util.Map;


/**
 * 微信支付请求抽象
 *
 * @param <TResult> 返回结果
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-30 9:44
 */
public abstract class AbstractWeiXinPayRequest<TResult>
        extends AbstractPayRequest<AbstractWeiXinTradeChannel, WeiXinChannelAccount, TResult> {

    /**
     * 实例化
     *
     * @param tradeChannel
     * @param channelAccount
     */
    public AbstractWeiXinPayRequest(AbstractWeiXinTradeChannel tradeChannel, WeiXinChannelAccount channelAccount) {
        super(tradeChannel, channelAccount);
    }

    /**
     * 获取签名类型
     *
     * @return
     */
    protected String getSignType() {
        return WeixinPayConstants.MD5;
    }

    /**
     * 获取apiUrl
     *
     * @return
     */
    protected abstract String getApiUrl();

    /**
     * 创建非证书请求的Map
     *
     * @return
     */
    protected Map<String, String> createRequestMap() {
        try {
            String xml = WeiXinPayHttpUtils.requestWithoutCert(this.getApiUrl(),
                    WeiXinXmlUtils.mapToXml(this.buildReqData()),
                    this.getTradeChannel().getChannelConfigure().getHttpConnectTimeoutMs(),
                    this.getTradeChannel().getChannelConfigure().getHttpReadTimeoutMs());
            return WeiXinPayUtils.processResponseXml(xml, this.getSignType(), this.getChannelAccount().getKey());
        } catch (Exception e) {
            throw this.createTradeChannelException(e.getMessage(), e);
        }
    }

    /**
     * 创建证书请求的Map
     *
     * @return
     */
    protected Map<String, String> createRequestWithCertMap() {
        try {
            String xml = WeiXinPayHttpUtils.requestWithCert(this.getApiUrl(),
                    WeiXinXmlUtils.mapToXml(this.buildReqData()),
                    this.getChannelAccount().getMchId(),
                    this.getChannelAccount().getCert(),
                    this.getTradeChannel().getChannelConfigure().getHttpConnectTimeoutMs(),
                    this.getTradeChannel().getChannelConfigure().getHttpReadTimeoutMs());
            return WeiXinPayUtils.processResponseXml(xml, this.getSignType(), this.getChannelAccount().getKey());
        } catch (Exception e) {
            throw this.createTradeChannelException(e.getMessage(), e);
        }
    }

    /**
     * 签名
     *
     * @param reqData 请求数据
     */
    protected void signature(Map<String, String> reqData) {
        reqData.put(WeixinPayConstants.FIELD_SIGN_TYPE, this.getSignType());
        String sign = WeiXinPayUtils.generateSignature(reqData, this.getSignType(), this.getChannelAccount().getKey());
        reqData.put(WeixinPayConstants.FIELD_SIGN, sign);
    }

}
