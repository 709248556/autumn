package com.autumn.application.service;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.entities.EntityDataBean;
import com.autumn.domain.entities.auditing.SoftDelete;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.util.AutoMapUtils;
import com.autumn.validation.DataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 编辑抽象
 *
 * @param <TKey>             主键类型
 * @param <TEntity>          实体类型
 * @param <TRepository>      仓储类型
 * @param <TQueryEntity>     查询实体类型
 * @param <TQueryRepository> 查询仓储类型
 * @param <TAddInput>        添加输入类型
 * @param <TUpdateInput>     更新输入类型
 * @param <TOutputItem>      输出项目类型（列表)
 * @param <TOutputDetails>   输出详情类型(单条详情)
 * @author 老码农 2018-11-19 11:49:45
 */
public abstract class AbstractEditApplicationService<TKey extends Serializable,
        TEntity extends Entity<TKey>,
        TRepository extends EntityRepository<TEntity, TKey>,
        TQueryEntity extends Entity<TKey>,
        TQueryRepository extends EntityRepository<TQueryEntity, TKey>,
        TAddInput,
        TUpdateInput extends Entity<TKey>,
        TOutputItem,
        TOutputDetails>
        extends AbstractQueryApplicationService<TKey, TQueryEntity, TQueryRepository, TOutputItem, TOutputDetails>
        implements EditApplicationService<TKey, TAddInput, TUpdateInput, TOutputItem, TOutputDetails> {


    /**
     * 日志新增操作名称
     */
    public static final String LOG_OPERATION_NAME_ADD = "新增";
    /**
     * 日志修改操作名称
     */
    public static final String LOG_OPERATION_NAME_UPDATE = "修改";
    /**
     * 日志删除操作名称
     */
    public static final String LOG_OPERATION_NAME_DELETE = "删除";

    @Autowired
    private TRepository repository;
    private final Class<TEntity> entityClass;
    private EntityTable entityTable = null;


    /**
     *
     */
    @SuppressWarnings("unchecked")
    public AbstractEditApplicationService() {
        super();
        this.entityClass = this.getGenericActualClass(GenericParameterConstant.ENTITY);
    }

    /**
     * 实例化
     *
     * @param entityClass        实体类型
     * @param queryEntityClass   查询实体类型
     * @param outputItemClass    输出项目类型
     * @param outputDetailsClass 输出详情类型
     */
    public AbstractEditApplicationService(Class<TEntity> entityClass,
                                          Class<TQueryEntity> queryEntityClass,
                                          Class<TOutputItem> outputItemClass,
                                          Class<TOutputDetails> outputDetailsClass) {
        super(queryEntityClass, outputItemClass, outputDetailsClass);
        this.entityClass = ExceptionUtils.checkNotNull(entityClass, "entityClass");
    }

    /**
     * 实例化
     *
     * @param entityArgName        实体参数名称
     * @param queryEntityArgName   查询实体参数名称
     * @param outputItemArgName    输出项目参数名称
     * @param outputDetailsArgName 输出详情参数名称
     */
    @SuppressWarnings("unchecked")
    public AbstractEditApplicationService(String entityArgName, String queryEntityArgName, String outputItemArgName, String outputDetailsArgName) {
        super(queryEntityArgName, outputItemArgName, outputDetailsArgName);
        this.entityClass = this.getGenericActualClass(entityArgName);
    }

    /**
     * 获取实体类型
     *
     * @return
     */
    public Class<TEntity> getEntityClass() {
        return this.entityClass;
    }

    /**
     * 获取实体表
     */
    public EntityTable getEntityTable() {
        if (this.entityTable == null) {
            synchronized (this) {
                if (this.entityTable == null) {
                    this.entityTable = EntityTable.getTable(this.getEntityClass());
                }
            }
        }
        return this.entityTable;
    }

    /**
     * 获取仓储
     *
     * @return
     */
    public TRepository getRepository() {
        return this.repository;
    }

    @Override
    public String getModuleId() {
        return this.getEntityClass().getSimpleName();
    }

    /**
     * 获取日志添加操作名称
     *
     * @return
     */
    protected String getLogAddOperationName() {
        return LOG_OPERATION_NAME_ADD;
    }

    /**
     * 获取日志更新操作名称
     *
     * @return
     */
    protected String getLogUpdateOperationName() {
        return LOG_OPERATION_NAME_UPDATE;
    }

    /**
     * 获取日志删除操作名称
     *
     * @return
     */
    protected String getLogDeleteOperationName() {
        return LOG_OPERATION_NAME_DELETE;
    }

    /**
     * 获取更新日志
     *
     * @param oldEntity 旧实体
     * @param newEntity 新实体
     * @return
     */
    protected String getUpdateLogDetails(TEntity oldEntity, TEntity newEntity) {
        return "旧：" + this.getAuditedLogger().getLogDetails(oldEntity)
                + "\r\n 新：" + this.getAuditedLogger().getLogDetails(newEntity);
    }

    /**
     * 是否启用操作日志
     *
     * @return 默认启用
     */
    protected boolean isEnableOperationLog() {
        return true;
    }

    /**
     * 添加操作日志
     *
     * @param entity 实体
     */
    protected void writeAddLog(TEntity entity) {
        if (this.isEnableOperationLog()) {
            this.getAuditedLogger().addLog(this, this.getLogAddOperationName(), entity);
        }
    }

    /**
     * 更新操作日志
     *
     * @param oldEntity 旧实体
     * @param newEntity 新实体
     */
    protected void writeUpdateLog(TEntity oldEntity, TEntity newEntity) {
        if (this.isEnableOperationLog()) {
            this.getAuditedLogger().addLog(this, this.getLogUpdateOperationName(), this.getUpdateLogDetails(oldEntity, newEntity));
        }
    }

    /**
     * 删除操作日志
     *
     * @param entity 实体
     */
    protected void writeDeleteLog(TEntity entity) {
        if (this.isEnableOperationLog()) {
            this.getAuditedLogger().addLog(this, this.getLogDeleteOperationName(), entity);
        }
    }

    /**
     * 添加之前
     *
     * @param input 输入
     * @param query 查询
     * @return
     */
    protected TEntity addBefore(TAddInput input, EntityQueryWrapper<TEntity> query) {
        return AutoMapUtils.map(input, this.getEntityClass());
    }

    /**
     * 添加之后
     *
     * @param input  输入
     * @param entity 实体
     * @param query  查询
     * @return
     */
    protected TOutputDetails addAfter(TAddInput input, TEntity entity, EntityQueryWrapper<TEntity> query) {
        if (this.getEntityClass().equals(this.getQueryEntityClass())) {
            return this.toOutputByEntity(entity);
        }
        return this.queryById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOutputDetails add(TAddInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        if (input instanceof DataValidation) {
            DataValidation validation = (DataValidation) input;
            validation.valid();
        }
        EntityQueryWrapper<TEntity> query = new EntityQueryWrapper<>(this.getEntityClass());
        TEntity entity = addBefore(input, query);
        this.addInitialize(entity);
        this.entityHandle(entity);
        this.getRepository().insert(entity);
        query.reset();
        TOutputDetails result = this.addAfter(input, entity, query);
        this.writeAddLog(entity);
        return result;
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
     * 实体处理
     *
     * @param entity 实体
     */
    protected final void entityHandle(TEntity entity) {
        if (entity instanceof EntityDataBean) {
            EntityDataBean entityDataBean = (EntityDataBean) entity;
            entityDataBean.forNullToDefault();
        }
        if (entity instanceof DataValidation) {
            DataValidation dataValidation = (DataValidation) entity;
            dataValidation.valid();
        }
    }

    /**
     * 更新之前
     *
     * @param input   输入
     * @param entity  已保存的实体
     * @param wrapper 包装
     */
    protected void updateBefore(TUpdateInput input, TEntity entity, EntityQueryWrapper<TEntity> wrapper) {
        AutoMapUtils.mapForLoad(input, entity);
    }

    /**
     * 更新之后
     *
     * @param input     输入
     * @param entity    已保存的实体
     * @param oldEntity 旧实体
     * @param wrapper   包装
     * @return
     */
    protected TOutputDetails updateAfter(TUpdateInput input, TEntity entity, TEntity oldEntity, EntityQueryWrapper<TEntity> wrapper) {
        if (this.getEntityClass().equals(this.getQueryEntityClass())) {
            return this.toOutputByEntity(entity);
        }
        return this.queryById(entity.getId());
    }

    /**
     * 创建基于Id的实体查询
     *
     * @param id 主键id值
     * @return
     */
    protected final EntityQueryWrapper<TEntity> createEntityWrapperById(TKey id) {
        EntityQueryWrapper<TEntity> wrapper = new EntityQueryWrapper<>(this.getEntityClass());
        wrapper.where().eq(Entity.FIELD_ID, id);
        this.systemByEntityCriteria(wrapper);
        return wrapper;
    }

    /**
     * 实体系统条件处理
     *
     * @param wrapper 包装
     */
    protected void systemByEntityCriteria(EntityQueryWrapper<TEntity> wrapper) {

    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    protected final TEntity getEntityById(TKey id) {
        return this.getEntityById(id, LockModeEnum.NONE);
    }

    /**
     * 获取实体
     *
     * @param id
     * @param mode 锁定
     * @return
     */
    protected TEntity getEntityById(TKey id, LockModeEnum mode) {
        EntityQueryWrapper<TEntity> query = this.createEntityWrapperById(id);
        if (mode != null) {
            query.lock(mode);
        } else {
            query.lock(LockModeEnum.NONE);
        }
        return this.getRepository().selectForFirst(query);
    }

    /**
     * 获取更新锁实体
     *
     * @param id
     * @return
     */
    protected final TEntity getEntityByUpdateLock(TKey id) {
        return this.getEntityById(id, LockModeEnum.UPDATE);
    }


    /**
     * 是否更新并重置子级
     *
     * @return
     */
    protected boolean isUpdateAndResetChildren() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOutputDetails update(TUpdateInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        ExceptionUtils.checkNotNull(input.getId(), "id");
        if (input instanceof DataValidation) {
            DataValidation validation = (DataValidation) input;
            validation.valid();
        }
        TEntity entity = this.getEntityById(input.getId(), LockModeEnum.UPDATE);
        if (entity == null) {
            ExceptionUtils.throwValidationException("无法修改不存在的数据。");
        }
        TEntity oldEntity;
        try {
            oldEntity = this.getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            this.getLogger().error(e.getMessage(), e);
            throw ExceptionUtils.throwValidationException("类型　" + this.getEntityClass().getName() + " 未提供默认构造函数。");
        }
        AutoMapUtils.mapForLoad(entity, oldEntity);
        EntityQueryWrapper<TEntity> query = new EntityQueryWrapper<>(this.getEntityClass());
        updateBefore(input, entity, query);
        updateInitialize(entity);
        this.entityHandle(entity);
        if (this.isUpdateAndResetChildren()) {
            this.getRepository().updateAndResetChildren(entity);
        } else {
            this.getRepository().update(entity);
        }
        query.reset();
        this.clearCacheById(entity.getId());
        TOutputDetails result = this.updateAfter(input, entity, oldEntity, query);
        this.writeUpdateLog(oldEntity, entity);
        return result;
    }

    /**
     * 实体输出
     *
     * @param entity 实体
     * @return
     */
    protected TOutputDetails toOutputByEntity(TEntity entity) {
        if (entity == null) {
            return null;
        }
        if (this.getEntityClass().equals(this.getQueryEntityClass())) {
            return this.toOutputByQuery((TQueryEntity) entity);
        }
        return AutoMapUtils.map(entity, this.getOutputDetailsClass());
    }


    /**
     * 删除之前
     *
     * @param entity 实体
     * @return 返回 true 则调用删除
     */
    protected void deleteBefore(TEntity entity) {

    }

    /**
     * 删除之后
     *
     * @param entity       实体
     * @param isSoftDelete 是否是软删除
     */
    protected void deleteAfter(TEntity entity, boolean isSoftDelete) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(TKey id) {
        ExceptionUtils.checkNotNull(id, "id");
        EntityQueryWrapper<TEntity> queryWrapper = new EntityQueryWrapper<>(this.getEntityClass());
        this.systemByEntityCriteria(queryWrapper);
        queryWrapper.where().eq(Entity.FIELD_ID, id).of().orderBy(Entity.FIELD_ID);
        TEntity entity = this.getRepository().selectForFirst(queryWrapper);
        if (entity == null) {
            ExceptionUtils.throwValidationException("无法删除不存在的数据记录。");
        }
        boolean isSoftDelete = entity instanceof SoftDelete;
        this.deleteBefore(entity);
        if (!isSoftDelete) {
            //非软删除
            this.getRepository().deleteChildrenById(id);
        }
        this.getRepository().deleteById(id);
        this.deleteAfter(entity, isSoftDelete);
        this.clearCacheById(entity.getId());
        this.writeDeleteLog(entity);
    }
}
