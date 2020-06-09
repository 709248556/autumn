package com.autumn.pay.configure;

import com.autumn.pay.channel.TradeChannel;
import com.autumn.pay.channel.TradeChannelContext;
import com.autumn.pay.channel.alipay.AliPayChannelProperties;
import com.autumn.pay.channel.alipay.AliPayQRCodeTradeChannel;
import com.autumn.pay.channel.impl.TradeChannelContextImpl;
import com.autumn.pay.channel.weixin.WeiXinChannelProperties;
import com.autumn.pay.channel.weixin.WeiXinQRCodeTradeChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-16 1:03
 */
@Configuration
@EnableConfigurationProperties({AutumnPayChannelProperties.class})
public class PayChannelAutoConfiguration {

    /**
     * 交易通道上下文
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(TradeChannelContext.class)
    public TradeChannelContext tradeChannelContext() {
        return new TradeChannelContextImpl();
    }

    /**
     * 支付宝扫码支付
     *
     * @param properties 属性
     * @return
     */
    @Bean(AliPayChannelProperties.BEAN_NAME_QR_CODE)
    @ConditionalOnProperty(name = AliPayChannelProperties.BEAN_CONDITIONAL_PROPERTY_QR_CODE, havingValue = "true")
    @ConditionalOnMissingBean(AliPayQRCodeTradeChannel.class)
    public TradeChannel aliPayQRCodeTradeChannel(AutumnPayChannelProperties properties) {
        AliPayQRCodeTradeChannel channel = new AliPayQRCodeTradeChannel();
        channel.setChannelConfigure(properties.getAlipay());
        if (properties.getAlipay() != null && properties.getAlipay().getQrCode() != null) {
            channel.checkTradeChannelAccount(properties.getAlipay().getQrCode());
            channel.setDefaultChannelAccount(properties.getAlipay().getQrCode());
        } else {
            channel.setDefaultChannelAccount(null);
        }
        return channel;
    }

    /**
     * 微信扫码支付
     *
     * @param properties 属性
     * @return
     */
    @Bean(WeiXinChannelProperties.BEAN_NAME_QR_CODE)
    @ConditionalOnProperty(name = WeiXinChannelProperties.BEAN_CONDITIONAL_PROPERTY_QR_CODE, havingValue = "true")
    @ConditionalOnMissingBean(WeiXinQRCodeTradeChannel.class)
    public TradeChannel weiXinQRCodeTradeChannel(AutumnPayChannelProperties properties) {
        WeiXinQRCodeTradeChannel channel = new WeiXinQRCodeTradeChannel();
        channel.setChannelConfigure(properties.getWeixin());
        if (properties.getWeixin() != null && properties.getWeixin().getQrCode() != null) {
            channel.checkTradeChannelAccount(properties.getWeixin().getQrCode());
            channel.setDefaultChannelAccount(properties.getWeixin().getQrCode());
        } else {
            channel.setDefaultChannelAccount(null);
        }
        return channel;
    }

    /**
     * 拦截
     *
     * @param channelContext 通道上下文
     * @return
     */
    @Bean
    public BeanPostProcessor autumnPayChannelBeanPostProcessor(TradeChannelContext channelContext) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof TradeChannel) {
                    channelContext.register((TradeChannel) bean);
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }

}
