package com.autumn.zero.common.library.application.services.personal.impl;

import com.autumn.application.service.AbstractEditApplicationService;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.EntityUpdateWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.zero.common.library.application.services.personal.UserPresonalDefaultService;
import com.autumn.zero.common.library.entities.personal.UserPersonalDefaultEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户个人服务抽象
 * <p>
 * </p>
 *
 * @param <TEntity>          实体类型
 * @param <TRepository>      仓储类型
 * @param <TQueryEntity>     查询实体类型
 * @param <TQueryRepository> 查询仓储类型
 * @param <TAddInput>        添加输入类型
 * @param <TUpdateInput>     更新输入类型
 * @param <TOutputItem>      输出项目类型
 * @param <TOutputDetails>   输出详情类型
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 18:25
 **/
public abstract class AbstractUserPresonalDefaultEditAppService<TEntity extends UserPersonalDefaultEntity,
        TRepository extends EntityRepository<TEntity, Long>,
        TQueryEntity extends UserPersonalDefaultEntity,
        TQueryRepository extends EntityRepository<TQueryEntity, Long>,
        TAddInput,
        TUpdateInput extends Entity<Long>,
        TOutputItem,
        TOutputDetails>
        extends AbstractEditApplicationService<Long, TEntity, TRepository, TQueryEntity, TQueryRepository, TAddInput, TUpdateInput, TOutputItem, TOutputDetails>
        implements UserPresonalDefaultService<TAddInput, TUpdateInput, TOutputItem, TOutputDetails> {

    /**
     * 默认限制个人最大记录数
     */
    public static final int DEFAULT_LIMIT_PRESONAL_MAX_RECORD = -1;

    /**
     * 缓存用户默认查询前缀
     */
    public static final String CACHE_USER_QUERY_DEFAULT_PREFIX = "personal_query_first_default_";

    @Override
    protected void queryByOrder(EntityQueryWrapper<TQueryEntity> query) {
        query.orderByDescending(UserPersonalDefaultEntity.FIELD_DEFAULTED)
                .orderBy(UserPersonalDefaultEntity.FIELD_ID);
    }

    @Override
    protected void systemByCriteria(EntityQueryWrapper<TQueryEntity> wrapper) {
        wrapper.where().eq(UserPersonalDefaultEntity.FIELD_USER_ID, this.getSession().getUserId());
    }

    @Override
    protected void systemByEntityCriteria(EntityQueryWrapper<TEntity> wrapper) {
        wrapper.where().eq(UserPersonalDefaultEntity.FIELD_USER_ID, this.getSession().getUserId());
    }

    @Override
    protected TEntity addBefore(TAddInput tAddInput, EntityQueryWrapper<TEntity> query) {
        int maxRecord = this.getLimitPresonalMaxRecord();
        if (maxRecord > 0) {
            EntityQueryWrapper<TEntity> wrapper = new EntityQueryWrapper<>(this.getEntityClass());
            systemByEntityCriteria(wrapper);
            if (this.getRepository().countByWhere(wrapper) >= maxRecord) {
                ExceptionUtils.throwValidationException(this.getModuleName() + " 不能超过[" + maxRecord + "]条记录。");
            }
        }
        TEntity result = super.addBefore(tAddInput, query);
        result.setUserId(this.getSession().getUserId());
        return result;
    }

    @Override
    protected TOutputDetails addAfter(TAddInput tAddInput, TEntity entity, EntityQueryWrapper<TEntity> wrapper) {
        TOutputDetails result = super.addAfter(tAddInput, entity, wrapper);
        if (entity.isDefaulted()) {
            this.updateNotDefault(entity.getUserId(), entity.getId());
        }
        return result;
    }

    @Override
    protected void updateBefore(TUpdateInput tUpdateInput, TEntity entity, EntityQueryWrapper<TEntity> query) {
        super.updateBefore(tUpdateInput, entity, query);
        entity.setUserId(this.getSession().getUserId());
    }

    @Override
    protected TOutputDetails updateAfter(TUpdateInput tUpdateInput, TEntity entity, TEntity oldEntity, EntityQueryWrapper<TEntity> query) {
        TOutputDetails result = super.updateAfter(tUpdateInput, entity, oldEntity, query);
        if (entity.isDefaulted() && !oldEntity.isDefaulted()) {
            this.updateNotDefault(entity.getUserId(), entity.getId());
        }
        return result;
    }

    @Override
    protected void deleteAfter(TEntity entity, boolean isSoftDelete) {
        super.deleteAfter(entity, isSoftDelete);
        if (entity.isDefaulted()) {
            this.clearUserQueryDefaultCacheByUser(entity.getUserId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void defaultUpdateById(Long id) {
        ExceptionUtils.checkNotNull(id, "id");
        long userId = this.getSession().getUserId();
        EntityUpdateWrapper<TEntity> wrapper = new EntityUpdateWrapper<>(this.getEntityClass());
        wrapper.where()
                .eq(UserPersonalDefaultEntity.FIELD_USER_ID, userId)
                .eq(UserPersonalDefaultEntity.FIELD_ID, id);
        wrapper.set(UserPersonalDefaultEntity.FIELD_DEFAULTED, true);
        if (this.getRepository().updateByWhere(wrapper) > 0) {
            this.updateNotDefault(userId, id);
        } else {
            ExceptionUtils.throwValidationException("无法更新不存在的[" + this.getModuleName() + "]。");
        }
        this.clearCacheById(id);
    }

    /**
     * 更新非默认(必须设置getDefaultFieldName)
     *
     * @param userId       用户id
     * @param notCurrentId 非当前主键
     */
    protected int updateNotDefault(Long userId, Long notCurrentId) {
        EntityUpdateWrapper<TEntity> wrapper = new EntityUpdateWrapper<>(this.getEntityClass());
        wrapper.where()
                .eq(UserPersonalDefaultEntity.FIELD_USER_ID, userId)
                .eq(UserPersonalDefaultEntity.FIELD_DEFAULTED, true);
        if (notCurrentId != null) {
            wrapper.where().notEq(UserPersonalDefaultEntity.FIELD_ID, notCurrentId);
        }
        wrapper.set(UserPersonalDefaultEntity.FIELD_DEFAULTED, false);
        int result = this.getRepository().updateByWhere(wrapper);
        this.clearUserQueryDefaultCacheByUser(userId);
        return result;
    }

    @Override
    public boolean isEnableCache() {
        return true;
    }

    @Override
    protected String createCacheKeyById(Long id) {
        return super.createCacheKeyById(id) + "_" + this.getSession().getUserId();
    }

    @Override
    public void clearCache() {
        super.clearCache();
        this.clearUserQueryDefaultCache();
    }

    /**
     * 获取用户查询认缓存名称
     *
     * @return
     */
    protected String getUserQueryDefaultCacheName() {
        return this.getCacheName() + "_user_default";
    }

    /**
     * 清除用户默认所有缓存
     */
    protected void clearUserQueryDefaultCache() {
        if (this.isEnableCache()) {
            this.clearCache(this.getUserQueryDefaultCacheName());
        }
    }

    /**
     * 创建用户默认查询缓存键
     *
     * @param userId
     * @return
     */
    protected String createUserQueryDefaultCacheKeyByUser(long userId) {
        return CACHE_USER_QUERY_DEFAULT_PREFIX + userId;
    }

    /**
     * 清除用户默认查询缓存键
     */
    protected void clearUserQueryDefaultCacheByUser(Long userId) {
        if (this.isEnableCache()) {
            this.clearCacheKey(this.getUserQueryDefaultCacheName(), this.createUserQueryDefaultCacheKeyByUser(userId));
        }
    }

    @Override
    public TOutputDetails queryDefault() {
        if (this.isEnableCache()) {
            String key = this.createUserQueryDefaultCacheKeyByUser(this.getSession().getUserId());
            return this.getOrAddCache(this.getUserQueryDefaultCacheName(), key, () -> {
                return this.queryDbDefault();
            });
        } else {
            return this.queryDbDefault();
        }
    }

    private int limitPresonalMaxRecord = DEFAULT_LIMIT_PRESONAL_MAX_RECORD;

    @Override
    public int getLimitPresonalMaxRecord() {
        return this.limitPresonalMaxRecord;
    }

    @Override
    public void setLimitPresonalMaxRecord(int limitPresonalMaxRecord) {
        this.limitPresonalMaxRecord = limitPresonalMaxRecord;
    }

    /**
     * 查询当前用户Db默认
     *
     * @return
     */
    protected TOutputDetails queryDbDefault() {
        EntityQueryWrapper<TQueryEntity> wrapper = new EntityQueryWrapper<>(this.getQueryEntityClass());
        this.systemByCriteria(wrapper);
        wrapper.where()
                .eq(UserPersonalDefaultEntity.FIELD_DEFAULTED, true)
                .of()
                .orderBy(UserPersonalDefaultEntity.FIELD_ID);
        TQueryEntity entity = this.getQueryRepository().selectForFirst(wrapper);
        return AutoMapUtils.map(entity, this.getOutputDetailsClass());
    }

}