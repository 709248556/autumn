package com.autumn.zero.file.storage.application.services;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;

/**
 * 简单文件上传编辑应用服务抽象
 *
 * @param <TEntity>     实体类型
 * @param <TRepository> 仓储类型
 * @param <TInput>      输入类型
 * @param <TOutput>     输出类型
 * @author 老码农 2019-07-02 16:00:00
 */
public abstract class AbstractSimpleZeroFileUploadEditAppService<TEntity extends Entity<Long>, TRepository extends EntityRepository<TEntity, Long>, TInput extends Entity<Long>, TOutput>
        extends AbstractZeroFileUploadEditAppService<TEntity, TRepository, TEntity, TRepository, TInput, TInput, TOutput, TOutput> {

    /**
     * 实例化
     */
    public AbstractSimpleZeroFileUploadEditAppService() {
        this(GenericParameterConstant.ENTITY, GenericParameterConstant.OUTPUT);
    }

    /**
     * 实例化
     *
     * @param entityClass 实体类型
     * @param qutputClass 输出类型
     */
    public AbstractSimpleZeroFileUploadEditAppService(Class<TEntity> entityClass, Class<TOutput> qutputClass) {
        super(entityClass, entityClass, qutputClass, qutputClass);
    }

    /**
     * 实例化
     *
     * @param entityArgName
     * @param qutputArgName
     */
    public AbstractSimpleZeroFileUploadEditAppService(String entityArgName, String qutputArgName) {
        super(entityArgName, entityArgName, qutputArgName, qutputArgName);
    }
}
