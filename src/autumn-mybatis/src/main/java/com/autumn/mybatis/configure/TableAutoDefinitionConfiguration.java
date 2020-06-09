package com.autumn.mybatis.configure;

import com.autumn.mybatis.event.TableAutoDefinitionListener;
import com.autumn.mybatis.event.impl.TableAutoDefinitionListenerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-05 14:24
 **/
@Configuration
public class TableAutoDefinitionConfiguration {

    /**
     * 启用表自动定义
     *
     * @return
     */
    @Bean
    @Order(TableAutoDefinitionListener.BEAN_BEGIN_ORDER)
    @ConditionalOnMissingBean({TableAutoDefinitionListener.class})
    public TableAutoDefinitionListener tableAutoDefinitionListenerImpl() {
        return new TableAutoDefinitionListenerImpl();
    }
}
