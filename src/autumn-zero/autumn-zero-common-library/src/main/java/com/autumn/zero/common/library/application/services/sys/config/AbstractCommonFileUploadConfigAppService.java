package com.autumn.zero.common.library.application.services.sys.config;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.zero.common.library.application.callback.CommonFileUploadConfigCallback;
import com.autumn.zero.common.library.entities.sys.SystemCommonConfig;
import com.autumn.zero.common.library.repositories.sys.SystemCommonConfigRepository;
import com.autumn.zero.file.storage.application.dto.FileUploadInput;
import com.autumn.zero.file.storage.application.services.AbstractZoreFileUploadCommonConfigAppService;
import com.autumn.zero.file.storage.application.services.FileUploadAppService;

import java.io.Serializable;
import java.util.Set;

/**
 * 公共文件配置文件上传抽象
 *
 * @param <TInput>  输入类型
 * @param <TOutput> 输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 19:09
 */
public abstract class AbstractCommonFileUploadConfigAppService<TInput extends FileUploadInput, TOutput extends Serializable>
        extends AbstractZoreFileUploadCommonConfigAppService<SystemCommonConfig, SystemCommonConfigRepository, TInput, TOutput>
        implements FileUploadAppService {

    /**
     * 实例化
     */
    public AbstractCommonFileUploadConfigAppService() {
        super(SystemCommonConfig.class, GenericParameterConstant.OUTPUT);
    }

    /**
     * 获取配置回调
     *
     * @return
     */
    protected abstract CommonFileUploadConfigCallback getConfigCallback();

    @Override
    public String getModuleId() {
        return this.getConfigCallback().getModuleId();
    }

    @Override
    protected String getConfigType() {
        return this.getConfigCallback().getConfigType();
    }

    @Override
    public String getModuleName() {
        return this.getConfigCallback().getModuleName();
    }

    @Override
    public int getFileUploadAttachmentType() {
        return this.getConfigCallback().getFileUploadAttachmentType();
    }

    @Override
    public String getFileUploadStartPath() {
        return this.getConfigCallback().getFileUploadStartPath();
    }

    @Override
    public Set<String> getFileUploadLimitExtensions() {
        return this.getConfigCallback().getFileUploadLimitExtensions();
    }

    @Override
    public Long getFileUploadLimitSize() {
        return this.getConfigCallback().getFileUploadLimitSize();
    }
}
