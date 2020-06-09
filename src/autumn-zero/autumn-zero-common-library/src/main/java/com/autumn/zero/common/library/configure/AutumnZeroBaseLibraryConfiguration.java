package com.autumn.zero.common.library.configure;

import com.autumn.util.json.JsonObjectDeserializerGenerator;
import com.autumn.zero.common.library.deserializer.json.CommonInputObjectDeserializerGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基本配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 19:02
 **/
@Configuration
public class AutumnZeroBaseLibraryConfiguration {

    /**
     * 公共接口默认序列化
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CommonInputObjectDeserializerGenerator.class)
    public JsonObjectDeserializerGenerator zeroCommonInputObjectDeserializerGenerator() {
        return new CommonInputObjectDeserializerGenerator();
    }

}
