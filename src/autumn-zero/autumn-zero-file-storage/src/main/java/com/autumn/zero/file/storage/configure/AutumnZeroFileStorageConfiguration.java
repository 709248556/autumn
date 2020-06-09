package com.autumn.zero.file.storage.configure;

import com.autumn.util.json.JsonObjectDeserializerGenerator;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import com.autumn.zero.file.storage.application.services.impl.FileUploadManagerImpl;
import com.autumn.zero.file.storage.deserializer.json.FileInputObjectDeserializerGenerator;
import com.autumn.zero.file.storage.services.FileAttachmentInformationService;
import com.autumn.zero.file.storage.services.impl.FileAttachmentInformationServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储配置
 *
 * @author 老码农 2019-03-18 18:04:33
 */
@Configuration
public class AutumnZeroFileStorageConfiguration {

    /**
     * 文件信息服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(FileAttachmentInformationService.class)
    public FileAttachmentInformationService autumnZeroFileAttachmentInformationService() {
        return new FileAttachmentInformationServiceImpl();
    }

    /**
     * 文件上传管理
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(FileUploadManager.class)
    public FileUploadManager autumnZeroFileUploadManager() {
        return new FileUploadManagerImpl();
    }

    /**
     * 文件上传接口默认序列化
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(FileInputObjectDeserializerGenerator.class)
    public JsonObjectDeserializerGenerator fileInputObjectDeserializerGenerator() {
        return new FileInputObjectDeserializerGenerator();
    }
}
