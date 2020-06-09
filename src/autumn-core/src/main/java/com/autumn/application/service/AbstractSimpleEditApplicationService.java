package com.autumn.application.service;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;

import java.io.Serializable;

/**
 * 简单实体抽象编辑应用服务
 *
 * @param <TKey>        主键类型
 * @param <TEntity>     实体类型
 * @param <TRepository> 仓储类型
 * @param <TInput>      输入类型
 * @param <TOutput>     输出类型
 * @author 老码农  2018-12-08 23:30:19
 */
public abstract class AbstractSimpleEditApplicationService<TKey extends Serializable,
        TEntity extends Entity<TKey>,
        TRepository extends EntityRepository<TEntity, TKey>,
        TInput extends Entity<TKey>,
        TOutput>
        extends AbstractEditApplicationService<TKey, TEntity, TRepository, TEntity, TRepository, TInput, TInput, TOutput, TOutput> {

    /**
     *
     */
    public AbstractSimpleEditApplicationService() {
        super(GenericParameterConstant.ENTITY, GenericParameterConstant.ENTITY, GenericParameterConstant.OUTPUT, GenericParameterConstant.OUTPUT);
    }

    /**
     * @param entityClass
     * @param outputClass
     */
    public AbstractSimpleEditApplicationService(Class<TEntity> entityClass, Class<TOutput> outputClass) {
        super(entityClass, entityClass, outputClass, outputClass);
    }

    /**
     * @param entityArgName
     * @param outputArgName
     */
    public AbstractSimpleEditApplicationService(String entityArgName, String outputArgName) {
        super(entityArgName, entityArgName, outputArgName, outputArgName);
    }
}
