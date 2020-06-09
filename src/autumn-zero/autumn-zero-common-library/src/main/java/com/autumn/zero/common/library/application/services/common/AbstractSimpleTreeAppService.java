package com.autumn.zero.common.library.application.services.common;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.zero.common.library.application.dto.tree.input.TreeInput;
import com.autumn.zero.common.library.application.dto.tree.output.TreeOutput;
import com.autumn.zero.common.library.entities.AbstractTreeEntity;

/**
 * 简单的树形应用服务抽象
 *
 * @param <TEntity>     实体类型
 * @param <TRepository> 实体仓储类型
 * @param <TInput>      输入类型
 * @param <TOutput>     输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 20:18
 */
public abstract class AbstractSimpleTreeAppService<TEntity extends AbstractTreeEntity,
        TRepository extends EntityRepository<TEntity, Long>, TInput extends TreeInput, TOutput extends TreeOutput>
        extends AbstractTreeAppService<TEntity, TRepository, TEntity, TRepository, TInput, TOutput, TOutput> {

    /**
     *
     */
    public AbstractSimpleTreeAppService() {
        this(GenericParameterConstant.ENTITY, GenericParameterConstant.OUTPUT);
    }

    /**
     * @param entityArgName
     * @param outputArgName
     */
    public AbstractSimpleTreeAppService(String entityArgName, String outputArgName) {
        super(entityArgName, entityArgName, outputArgName, outputArgName);
    }

    /**
     * 实例化
     *
     * @param entityClass
     * @param outputClass
     */
    public AbstractSimpleTreeAppService(Class<TEntity> entityClass,
                                            Class<TOutput> outputClass) {
        super(entityClass, entityClass, outputClass, outputClass);
    }

}
