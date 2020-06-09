package com.autumn.mybatis.mapper;

import com.autumn.exception.ConfigureException;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.spring.boot.context.AutumnApplicationContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 映射帮助
 *
 * @author 老码农
 * <p>
 * 2017-10-11 18:16:32
 */
public class MapperUtils {

    private final static String DOT = ".";

    /**
     * mappedStatement 实体缓存
     */
    private final static Map<String, Class<?>> MAPPED_MAPPER_CLASS_MAP = new ConcurrentHashMap<>(EntityTable.DEFAULT_ENTITY_NUMBER);

    /**
     * mapper 缓存
     */
    private static final Map<Class<?>, WeakReference<EntityMapper>> ENTITY_MAPPER_CACHE = new ConcurrentHashMap<>(EntityTable.DEFAULT_ENTITY_NUMBER);

    /**
     * Mapper 的 DbProvider 映射
     */
    private final static Map<Class<? extends Mapper>, DbProvider> MAPPER_DB_PROVIDER_MAP = new ConcurrentHashMap<>(100);

    /**
     * 应用上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 反转 EntityMapper
     *
     * @param entityClass 实体类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <TEntity> EntityMapper<TEntity> resolveEntityMapper(Class<TEntity> entityClass) {
        return (EntityMapper<TEntity>) Optional.ofNullable(ENTITY_MAPPER_CACHE.get(entityClass))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    EntityMapper<?> mapper = createEntityMapper(entityClass);
                    ENTITY_MAPPER_CACHE.put(entityClass, new WeakReference<>(mapper));
                    return mapper;
                });
    }

    /**
     * 创建实体  Mapper
     *
     * @param entityClass 实体类型
     * @return
     */
    private static EntityMapper createEntityMapper(Class<?> entityClass) {
        ApplicationContext context = getApplicationContext();
        Class<?> mapperClass = MapperRegister.getMapperClass(entityClass);
        if (mapperClass == null) {
            ExceptionUtils.throwSystemException("类型 " + entityClass.getName() + " 无法找到对应的  EntityMapper 接口。");
        }
        try {
            return (EntityMapper) context.getBean(mapperClass);
        } catch (BeansException e) {
            throw new ConfigureException("无 " + mapperClass.getName() + " 接口或无默认实现。" + e.getMessage(), e);
        }
    }

    /**
     * 获取应用上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        if (MapperUtils.applicationContext != null) {
            return MapperUtils.applicationContext;
        }
        synchronized (MAPPED_MAPPER_CLASS_MAP) {
            if (MapperUtils.applicationContext != null) {
                return MapperUtils.applicationContext;
            }
            MapperUtils.applicationContext = AutumnApplicationContext.getContext();
            if (MapperUtils.applicationContext == null) {
                ExceptionUtils.throwSystemException(
                        "AutumnApplicationContext 的 context 为null，无法取得 EntityMapper，是否引用 autumn-spring-boot-starter 。");
            }
            return MapperUtils.applicationContext;
        }
    }

    /**
     * 设置应用上下文
     *
     * @param applicationContext 应用上下文
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        synchronized (MAPPED_MAPPER_CLASS_MAP) {
            if (MapperUtils.applicationContext == null || !MapperUtils.applicationContext.equals(applicationContext)) {
                MapperUtils.applicationContext = ExceptionUtils.checkNotNull(applicationContext, "applicationContext");
            }
        }
    }

    /**
     * 获取 MappedStatement 接口 Mapper 类
     *
     * @param msId MappedStatement is id
     * @return
     */
    public static Class<?> getMapperClass(String msId) {
        if (!msId.contains(DOT)) {
            throw new MapperException("id 为 " + msId + " 的 MappedStatement 的 id = " + msId + ",不符合规范格式。");
        }
        String mapperClassStr = msId.substring(0, msId.lastIndexOf(DOT));
        return MAPPED_MAPPER_CLASS_MAP.computeIfAbsent(mapperClassStr, (key) -> {
            try {
                return Class.forName(key);
            } catch (ClassNotFoundException e) {
                throw new MapperException("MappedStatement  id =" + msId + " 的 MapperClass 类不存在。");
            }
        });
    }

    /**
     * 根据 MappedStatement 的 Id 获取方法名
     *
     * @param msId MappedStatement is id
     * @return
     */
    public static String getMethodName(String msId) {
        return msId.substring(msId.lastIndexOf(DOT) + 1);
    }

    /**
     * 获取 MappedStatement 执行的方法名
     *
     * @param ms MappedStatement
     * @return
     */
    public static String getMethodName(MappedStatement ms) {
        return getMethodName(ms.getId());
    }


    /**
     * 获取 dbProvider
     *
     * @param mapperClass mapper类型
     * @return
     */
    public static DbProvider getDbProvider(Class<? extends Mapper> mapperClass) {
        return MAPPER_DB_PROVIDER_MAP.get(mapperClass);
    }

    /**
     * 注册 Mapper 的 DbProvider 映射
     *
     * @param mapperClass mapper类型
     * @param dbProvider  提供者
     */
    static void registerMapperMap(Class<? extends Mapper> mapperClass, DbProvider dbProvider) {
        MAPPER_DB_PROVIDER_MAP.put(mapperClass, dbProvider);
    }

}
