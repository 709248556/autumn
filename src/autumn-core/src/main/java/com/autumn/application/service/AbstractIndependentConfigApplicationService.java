package com.autumn.application.service;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.entities.EntityDataBean;
import com.autumn.domain.entities.auditing.SoftDelete;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.StringUtils;
import com.autumn.validation.DataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 独立配置应用服务抽象
 *
 * @param <TKey>             键类型
 * @param <TEntity>          实体类型
 * @param <TRepository>      仓储类型
 * @param <TQueryEntity>     查询实体类型
 * @param <TQueryRepository> 查询仓储类型
 * @param <TInput>           输入类型
 * @param <TOutput>          输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-30 19:08
 */
public abstract class AbstractIndependentConfigApplicationService<TKey extends Serializable, TEntity extends Entity<TKey>,
        TRepository extends EntityRepository<TEntity, TKey>,
        TQueryEntity extends TEntity, TQueryRepository extends EntityRepository<TQueryEntity, TKey>,
        TInput extends Serializable, TOutput extends Serializable>
        extends AbstractConfigApplicationService<TInput, TOutput> implements IndependentConfigApplicationService<TQueryEntity, TInput, TOutput> {

    private final Class<TEntity> entityClass;
    private final Class<TQueryEntity> queryEntityClass;

    @Autowired
    private TRepository repository;

    @Autowired
    private TQueryRepository queryRepository;


    /**
     * 实例化
     */
    public AbstractIndependentConfigApplicationService() {
        this(GenericParameterConstant.ENTITY, GenericParameterConstant.QUERY_ENTITY, GenericParameterConstant.OUTPUT);
    }

    /**
     * 实例化
     *
     * @param entityArgName
     * @param queryEntityArgName
     * @param outputArgName
     */
    public AbstractIndependentConfigApplicationService(String entityArgName, String queryEntityArgName,
                                                       String outputArgName) {
        super(outputArgName);
        this.entityClass = this.getGenericActualClass(entityArgName);
        this.queryEntityClass = this.getGenericActualClass(queryEntityArgName);
    }

    /**
     * 实例化
     *
     * @param entityClass
     * @param queryEntityClass
     * @param outputClass
     */
    public AbstractIndependentConfigApplicationService(Class<TEntity> entityClass,
                                                       Class<TQueryEntity> queryEntityClass,
                                                       Class<TOutput> outputClass) {
        super(outputClass);
        this.entityClass = ExceptionUtils.checkNotNull(entityClass, "entityClass");
        this.queryEntityClass = ExceptionUtils.checkNotNull(queryEntityClass, "queryEntityClass");
    }

    /**
     * 获取查询实体类型
     *
     * @return
     */
    public final Class<TQueryEntity> getQueryEntityClass() {
        return this.queryEntityClass;
    }

    /**
     * 获取实体类型
     *
     * @return
     */
    public final Class<TEntity> getEntityClass() {
        return this.entityClass;
    }


    /**
     * 获取仓储
     *
     * @return
     */
    public final TRepository getRepository() {
        return this.repository;
    }

    /**
     * 获取查询仓储
     *
     * @return
     */
    public final TQueryRepository getQueryRepository() {
        return this.queryRepository;
    }

    /**
     * 保存之前
     *
     * @param input     输入
     * @param entity    实体
     * @param oldEntity 旧实体
     * @return
     */
    protected void saveBefore(TInput input, TEntity entity, TEntity oldEntity) {
        AutoMapUtils.mapForLoad(input, entity);
    }

    /**
     * 保存之后
     *
     * @param input     输入
     * @param entity    实体
     * @param oldEntity 旧实体
     * @return
     */
    protected void saveAfter(TInput input, TEntity entity, TEntity oldEntity) {

    }

    /**
     * 添加初始化
     *
     * @param entity 实体
     */
    protected void addInitialize(TEntity entity) {

    }

    /**
     * 更新初始化
     *
     * @param entity 实体
     */
    protected void updateInitialize(TEntity entity) {

    }

    /**
     * 是否是软删除实体
     *
     * @return
     */
    public final boolean isSoftDeleteEntity() {
        return SoftDelete.class.isAssignableFrom(this.getEntityClass());
    }

    /**
     * 实体系统条件
     *
     * @param query 查询
     */
    protected void systemByEntityCriteria(EntityQueryWrapper<TEntity> query) {

    }

    /**
     * 查询系统条件
     *
     * @param query 查询
     */
    protected void systemByQueryCriteria(EntityQueryWrapper<TQueryEntity> query) {

    }

    /**
     * 创建实体 Wrapper
     * *
     *
     * @return
     */
    protected final EntityQueryWrapper<TEntity> createEntityWrapper() {
        EntityQueryWrapper<TEntity> query = new EntityQueryWrapper<>(this.getEntityClass());
        query.orderBy(Entity.FIELD_ID);
        this.systemByEntityCriteria(query);
        return query;
    }

    /**
     * 创建查询 Wrapper
     * *
     *
     * @return
     */
    protected final EntityQueryWrapper<TQueryEntity> createQueryWrapper() {
        EntityQueryWrapper<TQueryEntity> query = new EntityQueryWrapper<>(this.getQueryEntityClass());
        query.orderBy(Entity.FIELD_ID);
        this.systemByQueryCriteria(query);
        return query;
    }

    /**
     * 创建实体
     *
     * @return
     */
    protected TEntity createEntity() {
        try {
            return this.getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            this.getLogger().error(e.getMessage(), e);
            throw ExceptionUtils.throwValidationException("类型　" + this.getEntityClass().getName() + " 未提供默认构造函数。");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOutput save(TInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        if (input instanceof DataValidation) {
            DataValidation validation = (DataValidation) input;
            validation.valid();
        }
        EntityQueryWrapper<TEntity> query = this.createEntityWrapper();
        query.lockByUpdate();
        TEntity entity = this.getRepository().selectForFirst(query);
        TEntity oldEntity = null;
        if (entity != null) {
            oldEntity = this.createEntity();
            AutoMapUtils.mapForLoad(entity, oldEntity);
        } else {
            entity = this.createEntity();
        }
        this.saveBefore(input, entity, oldEntity);
        if (entity instanceof EntityDataBean) {
            EntityDataBean entityDataBean = (EntityDataBean) entity;
            entityDataBean.forNullToDefault();
        }
        if (oldEntity == null) {
            this.addInitialize(entity);
            this.getRepository().insert(entity);
        } else {
            this.updateInitialize(entity);
            this.getRepository().updateAndResetChildren(entity);
        }
        this.saveAfter(input, entity, oldEntity);
        this.clearCache();
        TOutput result = this.queryForOutput();
        this.writeSaveLog(entity);
        return result;
    }

    /**
     * 查询并创建实体
     *
     * @return
     */
    protected TQueryEntity queryByCreateEntity() {
        EntityQueryWrapper<TQueryEntity> query = this.createQueryWrapper();
        return this.getQueryRepository().selectForFirstAndLoad(query);
    }

    private String[] otherCacheKeys = null;

    @Override
    protected String[] getOtherCacheKeys() {
        if (this.otherCacheKeys == null) {
            this.otherCacheKeys = new String[]{this.getEntityKey()};
        }
        return this.otherCacheKeys;
    }

    /**
     * 获取实体键
     *
     * @return
     */
    protected String getEntityKey() {
        return this.getCacheKey() + "_@entity@";
    }

    @Override
    public TQueryEntity queryForEntity() {
        String cacheKey = this.getCacheKey();
        if (StringUtils.isNullOrBlank(cacheKey)) {
            return this.queryByCreateEntity();
        }
        return this.getOrAddCache(this.getCacheName(), this.getEntityKey(), this::queryByCreateEntity);
    }

    @Override
    protected TOutput queryByCreateOutput() {
        TQueryEntity queryEntity = this.queryForEntity();
        if (queryEntity != null) {
            return AutoMapUtils.map(queryEntity, this.getOutputClass());
        }
        return null;
    }

}
