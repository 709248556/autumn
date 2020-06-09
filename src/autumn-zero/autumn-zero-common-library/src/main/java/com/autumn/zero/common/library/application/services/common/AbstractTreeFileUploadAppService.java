package com.autumn.zero.common.library.application.services.common;

import com.autumn.domain.repositories.EntityRepository;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.zero.common.library.application.dto.tree.input.TreeInput;
import com.autumn.zero.common.library.application.dto.tree.output.TreeOutput;
import com.autumn.zero.common.library.entities.AbstractTreeEntity;
import com.autumn.zero.file.storage.application.dto.FileInput;
import com.autumn.zero.file.storage.application.dto.FileUploadInput;
import com.autumn.zero.file.storage.application.services.FileUploadAppService;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;

import java.util.List;

/**
 * 具有文件上传树形应用服务抽象
 *
 * @param <TEntity>          实体类型
 * @param <TRepository>      实体仓储类型
 * @param <TQueryEntity>     查询类型
 * @param <TQueryRepository> 查询仓储类型
 * @param <TInput>           输入类型
 * @param <TOutputItem>      输出类型
 * @param <TOutputDetails>   输出详情类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 20:45
 */
public abstract class AbstractTreeFileUploadAppService<TEntity extends AbstractTreeEntity,
        TRepository extends EntityRepository<TEntity, Long>,
        TQueryEntity extends AbstractTreeEntity,
        TQueryRepository extends EntityRepository<TQueryEntity, Long>,
        TInput extends TreeInput,
        TOutputItem extends TreeOutput,
        TOutputDetails extends TreeOutput>
        extends AbstractTreeAppService<TEntity, TRepository, TQueryEntity, TQueryRepository, TInput, TOutputItem, TOutputDetails>
        implements FileUploadAppService {

    /**
     *
     */
    public AbstractTreeFileUploadAppService() {
        super();
    }

    /**
     * @param entityArgName
     * @param queryEntityArgName
     * @param outputItemArgName
     * @param outputDetailsArgName
     */
    public AbstractTreeFileUploadAppService(String entityArgName,
                                            String queryEntityArgName,
                                            String outputItemArgName,
                                            String outputDetailsArgName) {
        super(entityArgName, queryEntityArgName, outputItemArgName, outputDetailsArgName);
    }

    /**
     * 实例化
     *
     * @param entityClass        实体类型
     * @param queryEntityClass   查询实体类型
     * @param outputItemClass    输出项目类型
     * @param outputDetailsClass 输出详情类型
     */
    public AbstractTreeFileUploadAppService(Class<TEntity> entityClass,
                                            Class<TQueryEntity> queryEntityClass,
                                            Class<TOutputItem> outputItemClass,
                                            Class<TOutputDetails> outputDetailsClass) {
        super(entityClass, queryEntityClass, outputItemClass, outputDetailsClass);
    }

    @Override
    protected TOutputDetails addAfter(TInput input, TEntity entity, EntityQueryWrapper<TEntity> query) {
        TOutputDetails result = super.addAfter(input, entity, query);
        List<FileAttachmentInformationResponse> uploadFiles = this.fileUploadManager.useUploadFiles(this, entity.getId(), (FileUploadInput) input);
        this.fileUploadManager.loadUploadFileOutput(uploadFiles, result);
        this.clearCache();
        return result;
    }

    @Override
    protected TOutputDetails updateAfter(TInput input, TEntity entity, TEntity oldEntity, EntityQueryWrapper<TEntity> query) {
        TOutputDetails result = super.updateAfter(input, entity, oldEntity, query);
        List<FileAttachmentInformationResponse> uploadFiles = this.fileUploadManager.useUploadFiles(this, entity.getId(), (FileUploadInput) input);
        this.fileUploadManager.loadUploadFileOutput(uploadFiles, result);
        this.clearCache();
        return result;
    }

    @Override
    protected TOutputDetails toOutputByQuery(TQueryEntity queryEntity) {
        TOutputDetails result = super.toOutputByQuery(queryEntity);
        if (result != null && queryEntity != null) {
            this.fileUploadManager.loadUploadFileOutput(this, queryEntity.getId(), result);
        }
        return result;
    }

    @Override
    protected void deleteAfter(TEntity entity, boolean isSoftDelete) {
        super.deleteAfter(entity, isSoftDelete);
        if (!isSoftDelete) {
            this.fileUploadManager.deleteUploadFiles(this, entity.getId());
        }
    }

    @Override
    public FileAttachmentInformationResponse saveUploadFile(FileInput input) throws Exception {
        return this.fileUploadManager.saveUploadFile(this, input, "");
    }
}
