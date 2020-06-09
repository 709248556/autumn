package com.autumn.logistics.configure;

import com.autumn.logistics.channel.LogisticsChannel;
import com.autumn.logistics.channel.LogisticsChannelContext;
import com.autumn.logistics.channel.best.BestLogisticsChannel;
import com.autumn.logistics.channel.best.BestLogisticsChannelProperties;
import com.autumn.logistics.channel.cainiao.CaiNiaoLogisticsChannel;
import com.autumn.logistics.channel.cainiao.CaiNiaoLogisticsChannelProperties;
import com.autumn.logistics.channel.ems.EmsLogisticsChannel;
import com.autumn.logistics.channel.ems.EmsLogisticsChannelProperties;
import com.autumn.logistics.channel.impl.LogisticsChannelContextImpl;
import com.autumn.logistics.channel.jd.JDLogisticsChannel;
import com.autumn.logistics.channel.jd.JDLogisticsChannelProperties;
import com.autumn.logistics.channel.kuaidi100.KuaiDi100LogisticsChannel;
import com.autumn.logistics.channel.kuaidi100.KuaiDi100LogisticsChannelProperties;
import com.autumn.logistics.channel.sf.SFLogisticsChannel;
import com.autumn.logistics.channel.sf.SFLogisticsChannelProperties;
import com.autumn.logistics.channel.sto.STOLogisticsChannel;
import com.autumn.logistics.channel.sto.STOLogisticsChannelProperties;
import com.autumn.logistics.channel.ttk.TTKLogisticsChannel;
import com.autumn.logistics.channel.ttk.TTKLogisticsChannelProperties;
import com.autumn.logistics.channel.yto.YTOLogisticsChannel;
import com.autumn.logistics.channel.yto.YTOLogisticsChannelProperties;
import com.autumn.logistics.channel.yunda.YunDaLogisticsChannel;
import com.autumn.logistics.channel.yunda.YunDaLogisticsChannelProperties;
import com.autumn.logistics.channel.zto.ZTOLogisticsChannel;
import com.autumn.logistics.channel.zto.ZTOLogisticsChannelProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-01 23:39
 **/
@Configuration
@EnableConfigurationProperties({AutumnLogisticsChannelProperties.class})
public class LogisticsChannelAutoConfiguration {

    /**
     * 物流通道上下文
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LogisticsChannelContext.class)
    public LogisticsChannelContext logisticsChannelContext() {
        return new LogisticsChannelContextImpl();
    }


    /**
     * 百世达物流通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(BestLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = BestLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel bestLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new BestLogisticsChannel(properties.getBest());
    }

    /**
     * 菜鸟物流通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(CaiNiaoLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = CaiNiaoLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel caiNiaoLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new CaiNiaoLogisticsChannel(properties.getCainiao());
    }

    /**
     * 邮政Ems通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(EmsLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = EmsLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel emsLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new EmsLogisticsChannel(properties.getEms());
    }

    /**
     * 京东物流通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(JDLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = JDLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel jdLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new JDLogisticsChannel(properties.getJd());
    }

    /**
     * 快递100通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(KuaiDi100LogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = KuaiDi100LogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel kuaiDi100LogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new KuaiDi100LogisticsChannel(properties.getKuaidi100());
    }

    /**
     * 顺丰快递通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(SFLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = SFLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel sfLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new SFLogisticsChannel(properties.getSf());
    }

    /**
     * 申通快递通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(STOLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = STOLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel stoLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new STOLogisticsChannel(properties.getSto());
    }

    /**
     * 天天快递物流通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(TTKLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = TTKLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel ttkLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new TTKLogisticsChannel(properties.getTtk());
    }

    /**
     * 圆通快递通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(YTOLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = YTOLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel ytoLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new YTOLogisticsChannel(properties.getYto());
    }

    /**
     * 韵达快递通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(YunDaLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = YunDaLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel yunDaLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new YunDaLogisticsChannel(properties.getYunda());
    }

    /**
     * 中通快递通道
     *
     * @param properties 属性
     * @return
     */
    @Bean(ZTOLogisticsChannelProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = ZTOLogisticsChannelProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(BestLogisticsChannel.class)
    public LogisticsChannel ztoLogisticsChannel(AutumnLogisticsChannelProperties properties) {
        return new ZTOLogisticsChannel(properties.getZto());
    }


    /**
     * 拦截
     *
     * @param channelContext 通道上下文
     * @return
     */
    @Bean
    public BeanPostProcessor autumnLogisticsChannelBeanPostProcessor(LogisticsChannelContext channelContext) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof LogisticsChannel) {
                    channelContext.register((LogisticsChannel) bean);
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
