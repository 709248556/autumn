package com.autumn.zero.common.library.application.services.common;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.zero.common.library.application.dto.tree.input.TreeCodeInput;
import com.autumn.zero.common.library.application.dto.tree.output.TreeCodeOutput;
import com.autumn.zero.common.library.entities.AbstractTreeCodeEntity;

/**
 * 简单具有代码的树形应用服务抽象
 *
 * @param <TEntity>     实体类型
 * @param <TRepository> 实体仓储类型
 * @param <TInput>      输入类型
 * @param <TOutput>     输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 20:27
 */
public abstract class AbstractSimpleTreeCodeAppService<TEntity extends AbstractTreeCodeEntity,
        TRepository extends EntityRepository<TEntity, Long>, TInput extends TreeCodeInput, TOutput extends TreeCodeOutput>
        extends AbstractTreeCodeAppService<TEntity, TRepository, TEntity, TRepository, TInput, TOutput, TOutput> {

    /**
     *
     */
    public AbstractSimpleTreeCodeAppService() {
        this(GenericParameterConstant.ENTITY, GenericParameterConstant.OUTPUT);
    }

    /**
     * @param entityArgName
     * @param outputArgName
     */
    public AbstractSimpleTreeCodeAppService(String entityArgName,
                                            String outputArgName) {
        super(entityArgName, entityArgName, outputArgName, outputArgName);
    }

    /**
     * 实例化
     *
     * @param entityClass
     * @param outputClass
     */
    public AbstractSimpleTreeCodeAppService(Class<TEntity> entityClass,
                                            Class<TOutput> outputClass) {
        super(entityClass, entityClass, outputClass, outputClass);
    }
}
