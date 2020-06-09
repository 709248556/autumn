package com.autumn.zero.common.library.application.services.common;

import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.EqualsUtils;
import com.autumn.zero.common.library.application.dto.tree.input.TreeCodeInput;
import com.autumn.zero.common.library.application.dto.tree.output.TreeCodeOutput;
import com.autumn.zero.common.library.entities.AbstractTreeCodeEntity;

/**
 * 具有代码树形应用服务抽象
 *
 * @param <TEntity>          实体类型
 * @param <TRepository>      实体仓储类型
 * @param <TQueryEntity>     查询类型
 * @param <TQueryRepository> 查询仓储类型
 * @param <TInput>           输入类型
 * @param <TOutputItem>      输出类型
 * @param <TOutputDetails>   输出详情类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 20:01
 */
public abstract class AbstractTreeCodeAppService<TEntity extends AbstractTreeCodeEntity,
        TRepository extends EntityRepository<TEntity, Long>,
        TQueryEntity extends AbstractTreeCodeEntity,
        TQueryRepository extends EntityRepository<TQueryEntity, Long>,
        TInput extends TreeCodeInput,
        TOutputItem extends TreeCodeOutput,
        TOutputDetails extends TreeCodeOutput>
        extends AbstractTreeAppService<TEntity, TRepository, TQueryEntity, TQueryRepository, TInput, TOutputItem, TOutputDetails>
        implements TreeCodeAppService<TInput, TOutputItem, TOutputDetails> {

    /**
     *
     */
    public AbstractTreeCodeAppService() {
        super();
        this.getSearchMembers().add(AbstractTreeCodeEntity.FIELD_FULL_CODE);
    }

    /**
     * @param entityArgName
     * @param queryEntityArgName
     * @param outputItemArgName
     * @param outputDetailsArgName
     */
    public AbstractTreeCodeAppService(String entityArgName,
                                      String queryEntityArgName,
                                      String outputItemArgName,
                                      String outputDetailsArgName) {
        super(entityArgName, queryEntityArgName, outputItemArgName, outputDetailsArgName);
        this.getSearchMembers().add(AbstractTreeCodeEntity.FIELD_FULL_CODE);
    }

    /**
     * 实例化
     *
     * @param entityClass        实体类型
     * @param queryEntityClass   查询实体类型
     * @param outputItemClass    输出项目类型
     * @param outputDetailsClass 输出详情类型
     */
    public AbstractTreeCodeAppService(Class<TEntity> entityClass,
                                      Class<TQueryEntity> queryEntityClass,
                                      Class<TOutputItem> outputItemClass,
                                      Class<TOutputDetails> outputDetailsClass) {
        super(entityClass, queryEntityClass, outputItemClass, outputDetailsClass);
        this.getSearchMembers().add(AbstractTreeCodeEntity.FIELD_FULL_CODE);
    }

    private boolean isCheckCodeRepeat(TInput input, TEntity entity, boolean isAddNew) {
        if (isAddNew) {
            return true;
        } else {
            return !entity.getCode().equalsIgnoreCase(input.getCode().trim());
        }
    }

    @Override
    protected TEntity editCheckToPrarentEntity(TInput input, TEntity entity, boolean isAddNew) {
        if (input.getCode().contains(this.getPathSeparate())) {
            ExceptionUtils.throwValidationException("代码不能包含[" + this.getPathSeparate() + "]符号。");
        }
        return super.editCheckToPrarentEntity(input, entity, isAddNew);
    }


    @Override
    protected void editCheck(TInput input, TEntity entity, TEntity prarentEntity, boolean isAddNew) {
        if (this.isCheckCodeRepeat(input, entity, isAddNew)) {
            EntityQueryWrapper<TEntity> query = this.createEntityCommonWrapper();
            query.where(s -> s.eq(AbstractTreeCodeEntity.FIELD_CODE, input.getCode().trim()));
            if (query.exist(this.getRepository())) {
                ExceptionUtils.throwValidationException("指定的" + this.getModuleName() + "，代码[" + input.getCode() + "]已重复。");
            }
        }
        if (prarentEntity == null) {
            entity.setFullCode(input.getCode().trim());

        } else {
            entity.setFullCode(prarentEntity.getFullCode() + this.getPathSeparate() + input.getCode().trim());
        }
    }

    /**
     * 是否更新子级关联
     *
     * @param entity
     * @param oldEntity
     * @return
     */
    @Override
    protected boolean isUpdateChildrenRelation(TEntity entity, TEntity oldEntity) {
        return super.isUpdateChildrenRelation(entity, oldEntity)
                || !EqualsUtils.equalsObject(entity.getCode(), oldEntity.getCode());
    }

    /**
     * 设置更新子级关联
     *
     * @param entity   实体
     * @param children 子级
     */
    @Override
    protected void setUpdateChildrenRelation(TEntity entity, TEntity children) {
        children.setFullCode(entity.getFullCode() + this.getPathSeparate() + children.getCode());
    }
}
