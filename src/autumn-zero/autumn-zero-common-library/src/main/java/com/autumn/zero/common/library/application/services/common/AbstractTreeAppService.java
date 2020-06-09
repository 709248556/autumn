package com.autumn.zero.common.library.application.services.common;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.dto.input.StatusInput;
import com.autumn.application.service.AbstractEditApplicationService;
import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.EntityUpdateWrapper;
import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.EqualsUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.zero.common.library.application.dto.tree.input.ChildrenQueryInput;
import com.autumn.zero.common.library.application.dto.tree.input.TreeInput;
import com.autumn.zero.common.library.application.dto.tree.output.TreeOutput;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import com.autumn.zero.common.library.entities.AbstractTreeEntity;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 树形应用服务抽象
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
 * @Date 2019-08-04 17:09
 */
public abstract class AbstractTreeAppService<TEntity extends AbstractTreeEntity,
        TRepository extends EntityRepository<TEntity, Long>,
        TQueryEntity extends AbstractTreeEntity,
        TQueryRepository extends EntityRepository<TQueryEntity, Long>,
        TInput extends TreeInput,
        TOutputItem extends TreeOutput,
        TOutputDetails extends TreeOutput>
        extends AbstractEditApplicationService<Long, TEntity, TRepository, TQueryEntity, TQueryRepository, TInput, TInput, TOutputItem, TOutputDetails>
        implements TreeAppService<TInput, TOutputItem, TOutputDetails> {

    /**
     * 缓存名称前缀
     */
    private static final String CACHE_NAME_PREFIX = "cache_tree_";

    /**
     * 文件上传管理
     */
    @Autowired
    protected FileUploadManager fileUploadManager;

    /**
     *
     */
    public AbstractTreeAppService() {
        this(GenericParameterConstant.ENTITY, GenericParameterConstant.QUERY_ENTITY, GenericParameterConstant.OUTPUT_ITEM, GenericParameterConstant.OUTPUT_DETAILS);
    }

    /**
     * @param entityArgName
     * @param queryEntityArgName
     * @param outputItemArgName
     * @param outputDetailsArgName
     */
    public AbstractTreeAppService(String entityArgName,
                                  String queryEntityArgName,
                                  String outputItemArgName,
                                  String outputDetailsArgName) {
        super(entityArgName, queryEntityArgName, outputItemArgName, outputDetailsArgName);
        this.getSearchMembers().add(AbstractTreeEntity.FIELD_FULL_NAME);
    }

    /**
     * 实例化
     *
     * @param entityClass        实体类型
     * @param queryEntityClass   查询实体类型
     * @param outputItemClass    输出项目类型
     * @param outputDetailsClass 输出详情类型
     */
    public AbstractTreeAppService(Class<TEntity> entityClass,
                                  Class<TQueryEntity> queryEntityClass,
                                  Class<TOutputItem> outputItemClass,
                                  Class<TOutputDetails> outputDetailsClass) {
        super(entityClass, queryEntityClass, outputItemClass, outputDetailsClass);
        this.getSearchMembers().add(AbstractTreeEntity.FIELD_FULL_NAME);
    }

    /**
     * 获取路径分隔符
     *
     * @return
     */
    protected String getPathSeparate() {
        return "/";
    }

    /**
     * 获取最大级别
     *
     * @return
     */
    protected int getMaxLevel() {
        return -1;
    }

    /**
     * 获取缓存名称
     *
     * @return
     */
    @Override
    protected String getCacheName() {
        return CACHE_NAME_PREFIX + this.getModuleId();
    }

    /**
     * 是否启用缓存
     *
     * @return
     */
    @Override
    public boolean isEnableCache() {
        return true;
    }

    @Override
    protected void queryByOrder(EntityQueryWrapper<TQueryEntity> query) {
        query.orderBy(AbstractTreeEntity.FIELD_FULL_ID)
                .orderBy(AbstractTreeEntity.FIELD_ID);
    }

    /**
     * 创建实体公共查询
     *
     * @return
     */
    protected EntityQueryWrapper<TEntity> createEntityCommonWrapper() {
        EntityQueryWrapper<TEntity> query = new EntityQueryWrapper<>(this.getEntityClass());
        this.systemByEntityCriteria(query);
        return query;
    }

    /**
     * 创建查询公共查询
     *
     * @return
     */
    protected EntityQueryWrapper<TQueryEntity> createQueryCommonWrapper() {
        EntityQueryWrapper<TQueryEntity> query = new EntityQueryWrapper<>(this.getQueryEntityClass());
        this.systemByCriteria(query);
        return query;
    }

    /**
     * 是否检查名称是否重复
     *
     * @param input
     * @param entity
     * @param isAddNew
     * @return
     */
    private boolean isCheckNameRepeat(TInput input, TEntity entity, boolean isAddNew) {
        if (isAddNew) {
            return true;
        } else {
            if (EqualsUtils.equalsObject(input.getParentId(), entity.getParentId())) {
                return !entity.getName().equalsIgnoreCase(input.getName().trim());
            }
            return true;
        }
    }

    /**
     * 编辑检查并返回父级
     *
     * @param input
     * @param entity
     * @param isAddNew
     */
    protected TEntity editCheckToPrarentEntity(TInput input, TEntity entity, boolean isAddNew) {
        if (input.getName().contains(this.getPathSeparate())) {
            ExceptionUtils.throwValidationException("名称不能包含[" + this.getPathSeparate() + "]符号。");
        }
        EntityQueryWrapper<TEntity> query;
        TEntity prarentEntity = null;
        if (input.getParentId() == null) {
            entity.setLevel(1);
            entity.setFullName(input.getName().trim());
            if (this.isCheckNameRepeat(input, entity, isAddNew)) {
                query = this.createEntityCommonWrapper();
                query.where(s -> s.eq(AbstractTreeEntity.FIELD_NAME, input.getName().trim())
                        .eq(AbstractTreeEntity.FIELD_LEVEL, entity.getLevel())).orderBy(AbstractTreeEntity.FIELD_ID);
                if (query.exist(this.getRepository())) {
                    ExceptionUtils.throwValidationException("指定的" + this.getModuleName() + "，根级名称[" + input.getName() + "]已重复。");
                }
            }
            if (isAddNew) {
                entity.setFullId("");
            } else {
                entity.setFullId(Long.toString(entity.getId()));
            }
        } else {
            if (!isAddNew) {
                if (input.getParentId().equals(entity.getId())) {
                    ExceptionUtils.throwValidationException("不引用自身作为父级。");
                }
                if (entity.getChildrenCount() > 0) {
                    //查找子级，是否将子级为作父级引用
                    query = new EntityQueryWrapper<>(this.getEntityClass());
                    query.where(s -> s.eq(AbstractTreeEntity.FIELD_ID, input.getParentId()).leftLike(AbstractTreeEntity.FIELD_FULL_ID, entity.getFullId() + this.getPathSeparate()));
                    if (query.exist(this.getRepository())) {
                        ExceptionUtils.throwValidationException("不引用子级作为父级。");
                    }
                }
            }
            query = this.createEntityCommonWrapper();
            query.where(s -> s.eq(AbstractTreeEntity.FIELD_ID, input.getParentId())).orderBy(AbstractTreeEntity.FIELD_ID);
            prarentEntity = this.getRepository().selectForFirst(query);
            if (prarentEntity == null) {
                ExceptionUtils.throwValidationException("指定的" + this.getModuleName() + "的父级不存在。");
            }
            entity.setLevel(prarentEntity.getLevel() + 1);
            entity.setFullName(prarentEntity.getFullName() + this.getPathSeparate() + input.getName().trim());
            if (this.getMaxLevel() > 0 && entity.getLevel() > this.getMaxLevel()) {
                ExceptionUtils.throwValidationException("最大级别不能超过[" + this.getMaxLevel() + "]级。");
            }
            if (this.isCheckNameRepeat(input, entity, isAddNew)) {
                query = this.createEntityCommonWrapper();
                query.where(s -> s.eq(AbstractTreeEntity.FIELD_NAME, input.getName().trim())
                        .eq(AbstractTreeEntity.FIELD_PARENT_ID, input.getParentId())).orderBy(AbstractTreeEntity.FIELD_ID);
                if (query.exist(this.getRepository())) {
                    ExceptionUtils.throwValidationException("指定的" + this.getModuleName() + "，名称[" + input.getName() + "]在同一父级[" + prarentEntity.getName() + "]下已重复。");
                }
            }
            if (isAddNew) {
                entity.setFullId(prarentEntity.getFullId() + this.getPathSeparate());
            } else {
                entity.setFullId(prarentEntity.getFullId() + this.getPathSeparate() + entity.getId());
            }
        }
        this.editCheck(input, entity, prarentEntity, isAddNew);
        if (isAddNew) {
            entity.setChildrenCount(0);
        }
        return prarentEntity;
    }

    /**
     * 编辑检查
     *
     * @param input
     * @param entity
     * @param prarentEntity
     * @param isAddNew
     */
    protected void editCheck(TInput input, TEntity entity, TEntity prarentEntity, boolean isAddNew) {

    }


    @Override
    protected TEntity addBefore(TInput input, EntityQueryWrapper<TEntity> query) {
        TEntity entity = super.addBefore(input, query);
        this.editCheckToPrarentEntity(input, entity, true);
        return entity;
    }

    /**
     * 更新完整id
     *
     * @param entity 实体
     */
    protected void updateFullId(TEntity entity) {
        EntityUpdateWrapper<TEntity> wrapper = new EntityUpdateWrapper<>(this.getEntityClass());
        wrapper.where(s -> s.eq(AbstractTreeEntity.FIELD_ID, entity.getId()))
                .set(AbstractTreeEntity.FIELD_FULL_ID, entity.getFullId());
        this.getRepository().updateByWhere(wrapper);
    }

    @Override
    protected void clearCacheById(Long id) {
        this.clearCache();
    }

    @Override
    protected TOutputDetails addAfter(TInput input, TEntity entity, EntityQueryWrapper<TEntity> query) {
        TOutputDetails result = super.addAfter(input, entity, query);
        String fullId;
        if (entity.getParentId() != null) {
            TEntity parent = this.updateChildrenCount(entity.getParentId(), 1);
            fullId = parent.getFullId() + this.getPathSeparate() + entity.getId();
        } else {
            fullId = Long.toString(entity.getId());
        }
        entity.setFullId(fullId);
        this.updateFullId(entity);
        this.clearCache();
        return this.queryById(entity.getId());
    }

    @Override
    protected void updateBefore(TInput input, TEntity entity, EntityQueryWrapper<TEntity> query) {
        this.editCheckToPrarentEntity(input, entity, false);
        super.updateBefore(input, entity, query);
    }

    @Override
    protected TOutputDetails updateAfter(TInput input, TEntity entity, TEntity oldEntity, EntityQueryWrapper<TEntity> query) {
        TOutputDetails result = super.updateAfter(input, entity, oldEntity, query);
        if (!EqualsUtils.equalsObject(entity.getParentId(), oldEntity.getParentId())) {
            if (oldEntity.getParentId() != null) {
                this.updateChildrenCount(oldEntity.getParentId(), -1);
            }
            if (entity.getParentId() != null) {
                this.updateChildrenCount(entity.getParentId(), 1);
            }
        }
        if (entity.getChildrenCount() > 0 && this.isUpdateChildrenRelation(entity, oldEntity)) {
            this.updateChildrenRelation(entity);
        }
        return this.queryById(entity.getId());
    }

    /**
     * 更新子级数量
     *
     * @param id          主键
     * @param changeCount 变更的数量
     */
    protected TEntity updateChildrenCount(Long id, int changeCount) {
        if (id != null) {
            TEntity entity = this.getRepository().getByLock(id, LockModeEnum.UPDATE);
            if (entity == null) {
                ExceptionUtils.throwValidationException("指定的" + this.getModuleName() + "的父级不存在。");
            }
            EntityUpdateWrapper<TEntity> wrapper = new EntityUpdateWrapper<>(this.getEntityClass());
            wrapper.where(s -> s.eq(AbstractTreeEntity.FIELD_ID, entity.getId()))
                    .set(AbstractTreeEntity.FIELD_CHILDREN_COUNT, entity.getChildrenCount() + changeCount);
            this.getRepository().updateByWhere(wrapper);
            return entity;
        }
        return null;
    }

    /**
     * 是否更新子级关联
     *
     * @param entity
     * @param oldEntity
     * @return
     */
    protected boolean isUpdateChildrenRelation(TEntity entity, TEntity oldEntity) {
        return !EqualsUtils.equalsObject(entity.getParentId(), oldEntity.getParentId())
                || !EqualsUtils.equalsObject(entity.getName(), oldEntity.getName());
    }

    /**
     * 更新子级关联
     *
     * @param entity 实体
     */
    protected void updateChildrenRelation(TEntity entity) {
        EntityQueryWrapper<TEntity> query = new EntityQueryWrapper<>(this.getEntityClass());
        query.where(s -> s.eq(AbstractTreeEntity.FIELD_PARENT_ID, entity.getId())).orderBy(AbstractTreeEntity.FIELD_ID);
        List<TEntity> entitys = this.getRepository().selectForList(query);
        for (TEntity children : entitys) {
            //防止数据库出错，导至无限递归
            if (children.getId().equals(entity.getId())) {
                continue;
            }
            setUpdateChildrenRelation(entity, children);
            children.setLevel(entity.getLevel() + 1);
            if (this.getMaxLevel() > 0 && children.getLevel() > this.getMaxLevel()) {
                ExceptionUtils.throwValidationException("最大级别不能超过[" + this.getMaxLevel() + "]级。");
            }
            children.setFullId(entity.getFullId() + this.getPathSeparate() + children.getId());
            children.setFullName(entity.getFullName() + this.getPathSeparate() + children.getName());
            this.getRepository().update(children);
            if (children.getChildrenCount() > 0) {
                this.updateChildrenRelation(children);
            }
        }
    }

    /**
     * 设置更新子级关联
     *
     * @param entity   实体
     * @param children 子级
     */
    protected void setUpdateChildrenRelation(TEntity entity, TEntity children) {

    }

    @Override
    protected void deleteBefore(TEntity entity) {
        if (entity.getChildrenCount() > 0) {
            ExceptionUtils.throwValidationException("指定的" + this.getModuleName() + "的[" + entity.getName() + "]存在子级，无法删除。");
        }
        super.deleteBefore(entity);
    }

    @Override
    protected void deleteAfter(TEntity entity, boolean isSoftDelete) {
        super.deleteAfter(entity, isSoftDelete);
        if (entity.getParentId() != null) {
            this.updateChildrenCount(entity.getParentId(), -1);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(StatusInput<Long> input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        if (!CommonStatusConstant.exist(input.getStatus())) {
            ExceptionUtils.throwValidationException("无效的状态或不支持。");
        }
        TEntity entity = this.getEntityById(input.getId(), LockModeEnum.UPDATE);
        if (entity == null) {
            ExceptionUtils.throwValidationException("指定" + this.getModuleName() + "不存在，无法更新状态。");
        }
        if (entity.getStatus().equals(input.getStatus())) {
            return;
        }
        this.updateInitialize(entity);
        entity.setStatus(input.getStatus());
        this.getRepository().update(entity);
        this.clearCache();
        this.getAuditedLogger().addLog(this.getModuleName(), "更新状态", CommonStatusConstant.getName(entity.getStatus()));
    }

    @Override
    public TemporaryFileInformationDto exportFileByExcel(AdvancedQueryInput input) {
        Workbook workbook = this.exportByExcel(input);
        try {
            return fileUploadManager.saveTemporaryFileByWorkbook(this.getModuleName() + ExcelUtils.EXCEL_JOIN_EXTENSION_NAME, workbook);
        } catch (Exception e) {
            throw ExceptionUtils.throwApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 查询子列表
     *
     * @param input 输入
     * @return
     */
    protected List<TOutputItem> queryChildList(ChildrenQueryInput input) {
        EntityQueryWrapper<TQueryEntity> wrapper = this.createQueryCommonWrapper();
        if (input.getParentId() != null) {
            wrapper.where().eq(AbstractTreeEntity.FIELD_PARENT_ID, input.getParentId());
        } else {
            wrapper.where().isNull(AbstractTreeEntity.FIELD_PARENT_ID);
        }
        if (input.getStatus() != null) {
            wrapper.where().eq(AbstractTreeEntity.FIELD_STATUS, input.getStatus());
        }
        this.sortQueryChildren(input, wrapper);
        List<TQueryEntity> items = this.getQueryRepository().selectForList(wrapper);
        return AutoMapUtils.mapForList(items, this.getOutputItemClass());
    }

    /**
     * 排序查询子级
     *
     * @param input
     * @param wrapper
     */
    protected void sortQueryChildren(ChildrenQueryInput input, EntityQueryWrapper<TQueryEntity> wrapper) {
        wrapper.orderBy(AbstractTreeEntity.FIELD_SORT_ID).orderBy(AbstractTreeEntity.FIELD_ID);
    }

    /**
     * 查询子级
     *
     * @param input 输入
     * @return
     */
    @Override
    public List<TOutputItem> queryChildren(ChildrenQueryInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        String key = "query_children_" + input.toCacheKey();
        return this.getOrAddCache(key, () -> this.queryChildList(input));
    }
}
