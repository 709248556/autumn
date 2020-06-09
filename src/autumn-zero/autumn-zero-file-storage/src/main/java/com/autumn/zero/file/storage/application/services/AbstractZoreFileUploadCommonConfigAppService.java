package com.autumn.zero.file.storage.application.services;

import com.autumn.application.service.AbstractCommonConfigApplicationService;
import com.autumn.domain.entities.AbstractSystemCommonConfig;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.zero.file.storage.application.dto.FileInput;
import com.autumn.zero.file.storage.application.dto.FileUploadInput;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * 具有文件上传公共配置抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 1:28
 */
public abstract class AbstractZoreFileUploadCommonConfigAppService<TEntity extends AbstractSystemCommonConfig,
        TRepository extends EntityRepository<TEntity, Long>, TInput extends FileUploadInput, TOutput extends Serializable>
        extends AbstractCommonConfigApplicationService<TEntity, TRepository, TInput, TOutput>
        implements FileUploadAppService {

    /**
     * 文件上传管理
     */
    @Autowired
    protected FileUploadManager fileUploadManager;

    /**
     * 实例化
     */
    public AbstractZoreFileUploadCommonConfigAppService() {
        super();
    }

    /**
     * 实例化
     *
     * @param entityClass   实体类型
     * @param outputArgName 输出参数名称
     */
    public AbstractZoreFileUploadCommonConfigAppService(Class<TEntity> entityClass, String outputArgName) {
        super(entityClass, outputArgName);
    }

    /**
     * 实例化
     *
     * @param entitArgName
     * @param outputArgName
     */
    public AbstractZoreFileUploadCommonConfigAppService(String entitArgName, String outputArgName) {
        super(entitArgName, outputArgName);
    }

    /**
     * 获取上传目标id
     *
     * @return
     */
    public abstract long getUploadTargetId();

    @Override
    protected void saveAfter(TInput input, List<TEntity> systemCommonConfigs) {
        super.saveAfter(input, systemCommonConfigs);
        this.fileUploadManager.useUploadFiles(this, this.getUploadTargetId(), input);
    }

    @Override
    protected TOutput queryByCreateOutput() {
        TOutput result = super.queryByCreateOutput();
        if (result != null) {
            this.fileUploadManager.loadUploadFileOutput(this, this.getUploadTargetId(), result);
        }
        return result;
    }

    @Override
    public FileAttachmentInformationResponse saveUploadFile(FileInput input) throws Exception {
        return this.fileUploadManager.saveUploadFile(this, input, "");
    }

}
