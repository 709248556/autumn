package com.autumn.zero.file.storage.application.services;

import com.autumn.application.service.AbstractIndependentConfigApplicationService;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.util.AutoMapUtils;
import com.autumn.exception.ExceptionUtils;
import com.autumn.zero.file.storage.application.dto.FileInput;
import com.autumn.zero.file.storage.application.dto.FileUploadInput;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * 具有文件上传独立配置应用服务抽象
 *
 * @param <TEntity>          实体类型
 * @param <TRepository>      仓储类型
 * @param <TQueryEntity>     查询实体类型
 * @param <TQueryRepository> 查询仓储类型
 * @param <TInput>           输入类型
 * @param <TOutput>          输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 0:53
 */
public abstract class AbstractZoreFileUploadIndependentConfigAppService<TEntity extends Entity<Long>,
        TRepository extends EntityRepository<TEntity, Long>,
        TQueryEntity extends TEntity, TQueryRepository extends EntityRepository<TQueryEntity, Long>,
        TInput extends Serializable, TOutput extends Serializable>
        extends AbstractIndependentConfigApplicationService<Long, TEntity, TRepository, TQueryEntity, TQueryRepository, TInput, TOutput>
        implements FileUploadAppService {

    /**
     * 文件上传管理
     */
    @Autowired
    protected FileUploadManager fileUploadManager;

    /**
     * 实例化
     */
    public AbstractZoreFileUploadIndependentConfigAppService() {
        super();
    }

    /**
     * 实例化
     *
     * @param entityArgName
     * @param queryEntityArgName
     * @param outputArgName
     */
    public AbstractZoreFileUploadIndependentConfigAppService(String entityArgName,
                                                             String queryEntityArgName,
                                                             String outputArgName) {
        super(entityArgName, queryEntityArgName, outputArgName);
    }

    /**
     * @param entityClass
     * @param queryEntityClass
     * @param outputClass
     */
    public AbstractZoreFileUploadIndependentConfigAppService(Class<TEntity> entityClass,
                                                             Class<TQueryEntity> queryEntityClass,
                                                             Class<TOutput> outputClass) {
        super(entityClass, queryEntityClass, outputClass);
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
    protected void saveAfter(TInput input, TEntity entity, TEntity oldEntity) {
        super.saveAfter(input, entity, oldEntity);
        this.useUploadFiles(entity.getId(), input);
    }

    @Override
    protected TOutput queryByCreateOutput() {
        TQueryEntity queryEntity = this.queryForEntity();
        if (queryEntity != null) {
            TOutput result = AutoMapUtils.map(queryEntity, this.getOutputClass());
            this.fileUploadManager.loadUploadFileOutput(this, queryEntity.getId(), result);
            return result;
        }
        return null;
    }

    @Override
    public FileAttachmentInformationResponse saveUploadFile(FileInput input) throws Exception {
        return this.fileUploadManager.saveUploadFile(this, input, "");
    }
}
