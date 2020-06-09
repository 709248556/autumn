package com.autumn.application.service;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;

import java.io.Serializable;

/**
 * 简单的独立应用配置抽象
 *
 * @param <TKey>        键类型
 * @param <TEntity>     实体类型
 * @param <TRepository> 仓储类型
 * @param <TInput>      输入类型
 * @param <TOutput>     输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-30 20:38
 */
public abstract class AbstractSimpleIndependentConfigureApplicationService<TKey extends Serializable, TEntity extends Entity<TKey>,
        TRepository extends EntityRepository<TEntity, TKey>, TInput extends Serializable, TOutput extends Serializable>
        extends AbstractIndependentConfigApplicationService<TKey, TEntity, TRepository, TEntity, TRepository, TInput, TOutput> {

    /**
     * 实例化
     */
    public AbstractSimpleIndependentConfigureApplicationService() {
        this(GenericParameterConstant.ENTITY, GenericParameterConstant.OUTPUT);
    }

    /**
     * @param entityClass
     * @param outputClass
     */
    public AbstractSimpleIndependentConfigureApplicationService(Class<TEntity> entityClass, Class<TOutput> outputClass) {
        super(entityClass, entityClass, outputClass);
    }

    /**
     * 实例化
     *
     * @param entityArgName
     * @param outputArgName
     */
    public AbstractSimpleIndependentConfigureApplicationService(String entityArgName, String outputArgName) {
        super(entityArgName, entityArgName, outputArgName);
    }

}
