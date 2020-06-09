package com.autumn.application.service;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;

import java.io.Serializable;

/**
 * 简单实体抽象编辑并支持数据导入应用服务
 * <p>
 * </p>
 *
 * @param <TKey>            主键类型
 * @param <TEntity>         实体类型
 * @param <TRepository>     仓储类型
 * @param <TInput>          输入类型
 * @param <TOutput>         输出类型
 * @param <TImportTemplate> 导入模板类型
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-05 19:44
 **/
public abstract class AbstractSimpleEditDataImportApplicationService<TKey extends Serializable,
        TEntity extends Entity<TKey>,
        TRepository extends EntityRepository<TEntity, TKey>,
        TInput extends Entity<TKey>,
        TOutput,
        TImportTemplate>
        extends AbstractEditDataImportApplicationService<TKey, TEntity, TRepository, TEntity, TRepository, TInput, TInput, TOutput, TOutput, TImportTemplate> {

    /**
     * 实例化
     */
    public AbstractSimpleEditDataImportApplicationService() {
        this(GenericParameterConstant.ENTITY, GenericParameterConstant.OUTPUT, GenericParameterConstant.IMPORT_TEMPLATE);
    }

    /**
     * 实例化
     *
     * @param entityClass
     * @param outputClass
     * @param importTemplateClass
     */
    public AbstractSimpleEditDataImportApplicationService(Class<TEntity> entityClass,
                                                          Class<TOutput> outputClass,
                                                          Class<TImportTemplate> importTemplateClass) {
        super(entityClass, entityClass, outputClass, outputClass, importTemplateClass);
    }

    /**
     * 实例化
     *
     * @param entityClassArgumentsName
     * @param outputClassArgumentsName
     * @param importTemplateClassArgumentsName
     */
    public AbstractSimpleEditDataImportApplicationService(String entityArgName,
                                                          String outputArgName,
                                                          String importTemplateArgName) {
        super(entityArgName, entityArgName, outputArgName, outputArgName, importTemplateArgName);
    }

}
