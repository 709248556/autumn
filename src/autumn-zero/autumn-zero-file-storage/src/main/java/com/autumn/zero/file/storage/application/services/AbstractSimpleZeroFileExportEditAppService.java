package com.autumn.zero.file.storage.application.services;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;

/**
 * 简单文件导出编辑应用服务
 *
 * @param <TEntity>     实体类型
 * @param <TRepository> 仓储类型
 * @param <TInput>      输入类型
 * @param <TOutput>     输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-24 18:27
 */
public abstract class AbstractSimpleZeroFileExportEditAppService<TEntity extends Entity<Long>, TRepository extends EntityRepository<TEntity, Long>, TInput extends Entity<Long>, TOutput>
        extends AbstractZeroFileExportEditAppService<TEntity, TRepository, TEntity, TRepository, TInput, TInput, TOutput, TOutput> {

    /**
     *
     */
    public AbstractSimpleZeroFileExportEditAppService() {
        this(GenericParameterConstant.ENTITY, GenericParameterConstant.OUTPUT);
    }

    /**
     * @param entityClass
     * @param qutputClass
     */
    public AbstractSimpleZeroFileExportEditAppService(String entityArgName, String outputArgName) {
        super(entityArgName, entityArgName, outputArgName, outputArgName);
    }

    /**
     * @param entityClass
     * @param outputClass
     */
    public AbstractSimpleZeroFileExportEditAppService(Class<TEntity> entityClass, Class<TOutput> outputClass) {
        super(entityClass, entityClass, outputClass, outputClass);
    }
}
