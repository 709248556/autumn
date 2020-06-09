package com.autumn.web.configure;

import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.web.handlers.NoRepeatSubmitFilter;
import com.autumn.web.handlers.NoRepeatSubmitHandler;
import com.autumn.web.handlers.NoRepeatSubmitRequestMappingInfoHandler;
import com.autumn.web.handlers.impl.NoRepeatSubmitRequestMappingInfoHandlerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 防重复提交配置
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-14 21:28
 **/
@Configuration
public class NoRepeatSubmitConfiguration {

    /**
     * 重复提交处理器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(NoRepeatSubmitRequestMappingInfoHandler.class)
    public NoRepeatSubmitRequestMappingInfoHandler noRepeatSubmitRequestMappingInfoHandler() {
        return new NoRepeatSubmitRequestMappingInfoHandlerImpl();
    }

    /**
     * 配置重复提交处理器
     *
     * @param noRepeatSubmitRequestMappingInfoHandler 处理器
     * @param autumnRedisTemplate                     redis模板
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(NoRepeatSubmitHandler.class)
    public NoRepeatSubmitHandler onRepeatSubmitHandler(NoRepeatSubmitRequestMappingInfoHandler noRepeatSubmitRequestMappingInfoHandler, AutumnRedisTemplate autumnRedisTemplate) {
        return new NoRepeatSubmitHandler(noRepeatSubmitRequestMappingInfoHandler, autumnRedisTemplate);
    }

    /**
     * 配置重复提交过滤器
     *
     * @param noRepeatSubmitRequestMappingInfoHandler 处理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(NoRepeatSubmitFilter.class)
    public NoRepeatSubmitFilter noRepeatSubmitFilter(NoRepeatSubmitRequestMappingInfoHandler noRepeatSubmitRequestMappingInfoHandler) {
        return new NoRepeatSubmitFilter(noRepeatSubmitRequestMappingInfoHandler);
    }

}
