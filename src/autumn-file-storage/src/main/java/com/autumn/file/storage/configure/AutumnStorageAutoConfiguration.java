package com.autumn.file.storage.configure;

import com.autumn.exception.ExceptionUtils;
import com.autumn.file.storage.StorageClient;
import com.autumn.file.storage.StorageClientContext;
import com.autumn.file.storage.clients.aliyun.AliyunStorageClient;
import com.autumn.file.storage.clients.aliyun.AliyunStorageClientProperties;
import com.autumn.file.storage.clients.fastdfs.FastDFSStorageClient;
import com.autumn.file.storage.clients.fastdfs.FastDFSStorageClientProperties;
import com.autumn.file.storage.clients.local.LocalStorageClient;
import com.autumn.file.storage.clients.local.LocalStorageClientProperties;
import com.autumn.file.storage.impl.StorageClientContextImpl;
import com.autumn.file.storage.properties.AutumnStorageProperties;
import com.autumn.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 存储配置
 *
 * @author 老码农 2019-03-11 00:38:30
 */
@Configuration
@EnableConfigurationProperties({AutumnStorageProperties.class})
public class AutumnStorageAutoConfiguration {

    private final Log logger = LogFactory.getLog(AutumnStorageAutoConfiguration.class);

    /**
     * 存储客户端上下文
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(StorageClientContext.class)
    public StorageClientContext storageClientContext() {
        return new StorageClientContextImpl();
    }

    /**
     * 阿里云 oos
     *
     * @param properties 属性
     * @return
     */
    @Bean(AliyunStorageClientProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = AliyunStorageClientProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(AliyunStorageClient.class)
    public StorageClient aliyunStorageClient(AutumnStorageProperties properties) {
        AliyunStorageClientProperties clientProperties = properties.getAliyun();
        AliyunStorageClient client = new AliyunStorageClient(clientProperties.getEndpoint(),
                clientProperties.getDefaultBucketName(), clientProperties.getAccessKeyId(),
                clientProperties.getAccessKeySecret(), clientProperties.toCannedAccessControlList());
        client.setReadBlockSize(clientProperties.getReadBlockSize());
        client.setWriteBlockSize(clientProperties.getWriteBlockSize());
        logger.info(client.toString());
        return client;
    }

    /**
     * FastDFS
     *
     * @param properties 属性
     * @return
     */
    @Bean(FastDFSStorageClientProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = FastDFSStorageClientProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(FastDFSStorageClient.class)
    public StorageClient fastDFSStorageClient(AutumnStorageProperties properties) {
        FastDFSStorageClientProperties clientProperties = properties.getFastDFS();
        try {
            ClientGlobal.initByProperties(clientProperties.createFastDFSProperties());
        } catch (Exception e) {
            ExceptionUtils.throwConfigureException("初始化FastDFS出错:" + e.getMessage(), e);
        }
        FastDFSStorageClient client = new FastDFSStorageClient(clientProperties.getEndpoint(), clientProperties.getDefaultBucketName());
        client.setReadBlockSize(clientProperties.getReadBlockSize());
        client.setWriteBlockSize(clientProperties.getWriteBlockSize());
        logger.info(client.toString());
        return client;
    }

    /**
     * 本地文件存储
     *
     * @param properties 属性
     * @return
     */
    @Bean(LocalStorageClientProperties.CHANNEL_BEAN_NAME)
    @ConditionalOnProperty(name = LocalStorageClientProperties.BEAN_CONDITIONAL_PROPERTY, havingValue = "true")
    @ConditionalOnMissingBean(LocalStorageClient.class)
    public StorageClient localStorageClient(AutumnStorageProperties properties) {
        LocalStorageClientProperties clientProperties = properties.getLocal();
        String endpoint = clientProperties.getEndpoint();
        if (StringUtils.isNullOrBlank(endpoint)) {
            endpoint = LocalStorageClientProperties.DEFAULT_ENDPOINT;
        }
        String defaultBucketName = clientProperties.getDefaultBucketName();
        if (StringUtils.isNullOrBlank(defaultBucketName)) {
            defaultBucketName = LocalStorageClientProperties.DEFAULT_BUCKET_NAME;
        }
        String rootFilePath = clientProperties.getRootFilePath();
        if (StringUtils.isNullOrBlank(rootFilePath)) {
            rootFilePath = LocalStorageClient.getDefaultLocalPath();
        }
        if (StringUtils.isNullOrBlank(rootFilePath)) {
            ExceptionUtils.throwConfigureException("本地文件存储无默认保存路径 rootFilePath。");
        }
        LocalStorageClient client = new LocalStorageClient(endpoint, defaultBucketName, rootFilePath);
        client.setReadBlockSize(clientProperties.getReadBlockSize());
        client.setWriteBlockSize(clientProperties.getWriteBlockSize());
        logger.info(client.toString());
        return client;
    }

    /**
     * 拦截
     *
     * @param clientContext 客户端上下文
     * @return
     */
    @Bean
    public BeanPostProcessor autumnLogisticsChannelBeanPostProcessor(StorageClientContext clientContext) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof StorageClient) {
                    clientContext.register((StorageClient) bean);
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
