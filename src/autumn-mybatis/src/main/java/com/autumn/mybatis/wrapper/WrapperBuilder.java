package com.autumn.mybatis.wrapper;

/**
 * Wrapper 生成器
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-26 0:48
 */
public abstract class WrapperBuilder {

    /**
     * 创建条件 Wrapper
     *
     * @param entityClass 实体
     * @return
     */
    public static CriteriaWrapper CriteriaWrapper(Class<?> entityClass) {
        return new CriteriaWrapper(entityClass);
    }

    /**
     * 创建实体条件 Wrapper
     *
     * @param entityClass 实体
     * @param <TEntity>
     * @return
     */
    public static <TEntity> EntityCriteriaWrapper<TEntity> createEntityCriteriaWrapper(Class<TEntity> entityClass) {
        return new EntityCriteriaWrapper<>(entityClass);
    }

    /**
     * 创建查询 Wrapper
     *
     * @param entityClass 实体
     * @return
     */
    public static QueryWrapper createQueryWrapper(Class<?> entityClass) {
        return new QueryWrapper(entityClass);
    }


    /**
     * 创建实体查询 Wrapper
     *
     * @param entityClass 实体
     * @param <TEntity>
     * @return
     */
    public static <TEntity> EntityQueryWrapper<TEntity> createEntityQueryWrapper(Class<TEntity> entityClass) {
        return new EntityQueryWrapper<>(entityClass);
    }

    /**
     * 创建更新 Wrapper
     *
     * @param entityClass 实体
     * @return
     */
    public static UpdateWrapper createUpdateWrapper(Class<?> entityClass) {
        return new UpdateWrapper(entityClass);
    }

    /**
     * 创建实体更新 Wrapper
     *
     * @param entityClass 实体
     * @param <TEntity>
     * @return
     */
    public static <TEntity> EntityUpdateWrapper<TEntity> createEntityUpdateWrapper(Class<TEntity> entityClass) {
        return new EntityUpdateWrapper<>(entityClass);
    }

}
