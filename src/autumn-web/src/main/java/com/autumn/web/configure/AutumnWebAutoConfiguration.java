package com.autumn.web.configure;

import com.autumn.web.handlers.RequestMappingInfoHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.*;

/**
 * Web自动配置
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-16 04:27
 **/
@Configuration
public class AutumnWebAutoConfiguration {

    /**
     * 拦截
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AutumnWebBeanPostProcessor.class)
    public BeanPostProcessor autumnWebBeanPostProcessor() {
        return new AutumnWebBeanPostProcessor();
    }

    /**
     *
     */
    private static class AutumnWebBeanPostProcessor implements BeanPostProcessor, ApplicationListener<ApplicationReadyEvent> {
        private List<RequestMappingInfoHandlerMapping> handlerMappings = new ArrayList<>(5);
        private List<RequestMappingInfoHandler> mappingInfoHandlers = new ArrayList<>(16);
        private Map<RequestMappingInfoHandlerMapping, Set<RequestMappingInfoHandler>> scanMap = new HashMap<>(5);

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            boolean isScan = false;
            if (bean instanceof RequestMappingInfoHandler) {
                this.mappingInfoHandlers.add((RequestMappingInfoHandler) bean);
                isScan = true;
            }
            if (bean instanceof RequestMappingInfoHandlerMapping) {
                this.handlerMappings.add((RequestMappingInfoHandlerMapping) bean);
                isScan = true;
            }
            if (isScan) {
                this.callMappingHandler();
            }
            return bean;
        }

        private void callMappingHandler() {
            for (RequestMappingInfoHandlerMapping handlerMapping : this.handlerMappings) {
                Set<RequestMappingInfoHandler> handlers = scanMap.computeIfAbsent(handlerMapping, key -> new HashSet<>(16));
                for (RequestMappingInfoHandler infoHandler : this.mappingInfoHandlers) {
                    if (handlers.add(infoHandler)) {
                        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
                        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
                            infoHandler.mappingHandler(entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
        }

        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            this.handlerMappings.clear();
            this.mappingInfoHandlers.clear();
            this.scanMap.clear();
            this.handlerMappings = null;
            this.mappingInfoHandlers = null;
            this.scanMap = null;
        }
    }
}
