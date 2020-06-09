package com.autumn.zero.ueditor.configure;

import com.autumn.zero.file.storage.application.services.FileUploadManager;
import com.autumn.zero.ueditor.action.ConfigAction;
import com.autumn.zero.ueditor.action.UploadAction;
import com.autumn.zero.ueditor.action.impl.DefaultConfigAction;
import com.autumn.zero.ueditor.action.impl.DefaultUploadAction;
import com.autumn.zero.ueditor.web.controllers.UeditorController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AutumnUeditorWeb 配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 12:12
 */
@Configuration
public class AutumnUeditorWebConfiguration {

    /**
     * 配置执行器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ConfigAction.class)
    public ConfigAction autumnZeroConfigAction() {
        DefaultConfigAction action = new DefaultConfigAction();
        action.setCheckLogin(true);
        return action;
    }

    /**
     * 上传执行器
     *
     * @param fileUploadManager 文件上传管理
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UploadAction.class)
    public UploadAction autumnZeroUploadAction(FileUploadManager fileUploadManager) {
        DefaultUploadAction action = new DefaultUploadAction(fileUploadManager);
        action.setCheckLogin(true);
        return action;
    }

    /**
     * Ueditor 控制器
     *
     * @param uploadAction 上传执行器
     * @param configAction 配置执行器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UeditorController.class)
    public UeditorController autumnZeroUeditorController(UploadAction uploadAction, ConfigAction configAction) {
        return new UeditorController(uploadAction, configAction);
    }

}