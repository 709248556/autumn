package com.autumn.zero.file.storage.application.services;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;

import java.io.Serializable;

/**
 * 简单的具有文件上传独立配置应用服务抽象
 *
 * @param <TEntity>     实体类型
 * @param <TRepository> 仓储类型
 * @param <TInput>      输入类型
 * @param <TOutput>     输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 1:16
 */
public abstract class AbstractSimpleZoreFileUploadIndependentConfigAppService<TEntity extends Entity<Long>,
        TRepository extends EntityRepository<TEntity, Long>, TInput extends Serializable, TOutput extends Serializable>
        extends AbstractZoreFileUploadIndependentConfigAppService<TEntity, TRepository, TEntity, TRepository, TInput, TOutput> {

    /**
     * 实例化
     */
    public AbstractSimpleZoreFileUploadIndependentConfigAppService() {
        this(GenericParameterConstant.ENTITY, GenericParameterConstant.OUTPUT);
    }

    /**
     * @param entityClass
     * @param outputClass
     */
    public AbstractSimpleZoreFileUploadIndependentConfigAppService(Class<TEntity> entityClass, Class<TOutput> outputClass) {
        super(entityClass, entityClass, outputClass);
    }

    /**
     * 实例化
     *
     * @param entityArgName
     * @param outputArgName
     */
    public AbstractSimpleZoreFileUploadIndependentConfigAppService(String entityArgName, String outputArgName) {
        super(entityArgName, entityArgName, outputArgName);
    }
}
