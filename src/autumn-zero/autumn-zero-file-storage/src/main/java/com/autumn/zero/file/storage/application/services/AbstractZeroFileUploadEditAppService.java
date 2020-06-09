package com.autumn.zero.file.storage.application.services;

import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.exception.ExceptionUtils;
import com.autumn.zero.file.storage.application.dto.FileInput;
import com.autumn.zero.file.storage.application.dto.FileUploadInput;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;

import java.util.List;

/**
 * 具有文件上传编辑应用服务抽象
 *
 * @param <TEntity>          实体类型
 * @param <TRepository>      仓储类型
 * @param <TQueryEntity>     查询实体类型
 * @param <TQueryRepository> 查询仓储类型
 * @param <TAddInput>        添加输入类型
 * @param <TUpdateInput>     更新输入类型
 * @param <TOutputItem>      输出项目类型
 * @param <TOutputDetails>   输出类型
 * @author 老码农 2019-07-02 16:00:00
 */
public abstract class AbstractZeroFileUploadEditAppService<TEntity extends Entity<Long>,
        TRepository extends EntityRepository<TEntity, Long>,
        TQueryEntity extends Entity<Long>,
        TQueryRepository extends EntityRepository<TQueryEntity, Long>,
        TAddInput,
        TUpdateInput extends Entity<Long>,
        TOutputItem,
        TOutputDetails>
        extends AbstractZeroFileExportEditAppService<TEntity, TRepository, TQueryEntity, TQueryRepository, TAddInput, TUpdateInput, TOutputItem, TOutputDetails>
        implements FileUploadAppService, FileExportAppService {

    /**
     *
     */
    public AbstractZeroFileUploadEditAppService() {
        super();
    }

    /**
     * @param entityArgName
     * @param queryEntityArgName
     * @param outputItemArgName
     * @param outputDetailsArgName
     */
    public AbstractZeroFileUploadEditAppService(String entityArgName,
                                                   String queryEntityArgName,
                                                   String outputItemArgName,
                                                   String outputDetailsArgName) {
        super(entityArgName, queryEntityArgName, outputItemArgName, outputDetailsArgName);
    }

    /**
     * @param entityClass
     * @param queryEntityClass
     * @param outputItemClass
     * @param outputDetailsClass
     */
    public AbstractZeroFileUploadEditAppService(Class<TEntity> entityClass,
                                                   Class<TQueryEntity> queryEntityClass,
                                                   Class<TOutputItem> outputItemClass,
                                                   Class<TOutputDetails> outputDetailsClass) {
        super(entityClass, queryEntityClass, outputItemClass, outputDetailsClass);
    }

    /**
     * 使用上传文件
     *
     * @param id
     * @param input 输入
     * @return
     */
    protected List<FileAttachmentInformationResponse> useUploadFiles(Long id, Object input) {
        if (input instanceof FileUploadInput) {
            return this.fileUploadManager.useUploadFiles(this, id, (FileUploadInput) input);
        } else {
            throw ExceptionUtils.throwSystemException("类型 " + input.getClass() + " 未实现 FileUploadInput 接口。");
        }
    }

    @Override
    protected TOutputDetails addAfter(TAddInput input, TEntity entity, EntityQueryWrapper<TEntity> query) {
        List<FileAttachmentInformationResponse> uploadFiles = this.useUploadFiles(entity.getId(), input);
        TOutputDetails result = super.addAfter(input, entity, query);
        this.fileUploadManager.loadUploadFileOutput(uploadFiles, result);
        return result;
    }

    @Override
    protected TOutputDetails updateAfter(TUpdateInput input, TEntity entity, TEntity oldEntity, EntityQueryWrapper<TEntity> query) {
        List<FileAttachmentInformationResponse> uploadFiles = this.useUploadFiles(entity.getId(), input);
        TOutputDetails result = super.updateAfter(input, entity, oldEntity, query);
        this.fileUploadManager.loadUploadFileOutput(uploadFiles, result);
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
