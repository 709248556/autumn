package com.autumn.mybatis.mapper;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.util.tuple.TupleTwo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mapper 注册
 *
 * @author 老码农
 * <p>
 * 2017-10-17 16:59:06
 */
public class MapperRegister {

    /**
     * 全局性映射
     */
    private final static Map<String, MapperInfo> MSID_MAPPER_INFO_MAP = new ConcurrentHashMap<>(100);

    /**
     * 实体仓储映射
     */
    private final static Map<Class<?>, MapperInfo> ENTITY_MAPPER_INFO_MAP = new ConcurrentHashMap<>(100);

    /**
     * 日志
     */
    private final static Log logger = LogFactory.getLog(MapperRegister.class);

    private final DbProvider dbProvider;
    /**
     *
     */
    private final Map<String, Boolean> supportMappedStatementMap = new ConcurrentHashMap<>();

    /**
     *
     */
    private Map<Class<?>, List<MapperProvider>> registerMapperMap = new ConcurrentHashMap<>();

    /**
     *
     */
    private Map<String, MapperProvider> msIdProviderMap = new ConcurrentHashMap<>();

    /**
     *
     */
    private Set<Class<?>> mapperInterfaceMap = new HashSet<>();

    /**
     * 获取 Mapper 信息
     *
     * @param msId
     * @return 2017-12-08 14:36:09
     */
    public static MapperInfo getMapperInfo(String msId) {
        return MSID_MAPPER_INFO_MAP.get(msId);
    }

    /**
     * 获取 Mapper 信息
     *
     * @param entityClass 实体类型
     * @return
     */
    public static MapperInfo getMapperInfoByEntityClass(Class<?> entityClass) {
        return ENTITY_MAPPER_INFO_MAP.get(entityClass);
    }

    /**
     * 获取 MapperClass
     *
     * @param entityClass 实体类型
     * @return
     */
    public static Class<?> getMapperClass(Class<?> entityClass) {
        MapperInfo mapperInfo = ENTITY_MAPPER_INFO_MAP.get(entityClass);
        if (mapperInfo == null) {
            return null;
        }
        return mapperInfo.getMapperClass();
    }

    /**
     * @param dbProvider
     */
    public MapperRegister(DbProvider dbProvider) {
        this.dbProvider = ExceptionUtils.checkNotNull(dbProvider, "dbProvider");
    }

    /**
     * 获取驱动
     *
     * @return
     */
    public final DbProvider getDbProvider() {
        return dbProvider;
    }

    /**
     * 创建提供者
     *
     * @param providerClass 提供者类型
     * @param mapperClass   Map类型
     * @return
     */
    private MapperProvider createMapperProvider(Class<?> providerClass, Class<?> mapperClass) {
        try {
            Constructor<?> constructor = providerClass.getConstructor(Class.class, MapperRegister.class);
            if (constructor == null) {
                throw new MapperException(providerClass.getName() + " 的构造参数不正确。");
            }
            return (MapperProvider) constructor.newInstance(mapperClass, this);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new MapperException(providerClass.getName() + " 无法实例化:" + e.getMessage());
        }
    }

    /**
     * 注册类
     *
     * @param mapperClass map类
     * @return
     */
    private List<MapperProvider> registerMapperClass(Class<?> mapperClass) {
        logger.info("注册自动拦截接口 " + mapperClass);
        Method[] methods = mapperClass.getDeclaredMethods();
        Map<Class<?>, List<TupleTwo<Method, String>>> methodMap = new HashMap<>(16);
        for (Method method : methods) {
            TupleTwo<Class<?>, String> tuple = null;
            if (method.isAnnotationPresent(SelectProvider.class)) {
                SelectProvider provider = method.getAnnotation(SelectProvider.class);
                tuple = new TupleTwo<>(provider.type(), provider.method());
            } else if (method.isAnnotationPresent(InsertProvider.class)) {
                InsertProvider provider = method.getAnnotation(InsertProvider.class);
                tuple = new TupleTwo<>(provider.type(), provider.method());
            } else if (method.isAnnotationPresent(DeleteProvider.class)) {
                DeleteProvider provider = method.getAnnotation(DeleteProvider.class);
                tuple = new TupleTwo<>(provider.type(), provider.method());
            } else if (method.isAnnotationPresent(UpdateProvider.class)) {
                UpdateProvider provider = method.getAnnotation(UpdateProvider.class);
                tuple = new TupleTwo<>(provider.type(), provider.method());
            }
            if (tuple != null) {
                List<TupleTwo<Method, String>> items = methodMap.computeIfAbsent(tuple.getItem1(),
                        k -> new ArrayList<>());
                items.add(new TupleTwo<>(method, tuple.getItem2()));
            }
        }
        // if (methodMap.size() == 0) {
        // throw new MapperException(mapperClass.getName() + " 无任何可使用的接口。");
        // }
        List<MapperProvider> providerList = new ArrayList<>();
        for (Map.Entry<Class<?>, List<TupleTwo<Method, String>>> entry : methodMap.entrySet()) {
            MapperProvider mapperProvider = createMapperProvider(entry.getKey(), mapperClass);
            for (TupleTwo<Method, String> tuple : entry.getValue()) {
                try {
                    Method providerMethod = entry.getKey().getMethod(tuple.getItem2(), MappedStatement.class);
                    mapperProvider.registerMethod(tuple.getItem1().getName(), providerMethod);
                } catch (NoSuchMethodException e) {
                    // 若方法不存在则从配置文件中读取
                }
            }
            providerList.add(mapperProvider);
        }
        return providerList;
    }

    private boolean isRegisterWithMapper = false;

    /**
     * 注册内置自带
     */
    public void registerWithMapper() {
        synchronized (this) {
            if (!isRegisterWithMapper) {
                registerMapper(EntityMapper.class);
                registerMapper(CustomBaseMapper.class);
                isRegisterWithMapper = true;
            }
        }
    }

    /**
     * 注册 Mapper 的 DbProvider 的映射
     *
     * @param mapperClass 映射
     */
    public void registerMapperDbProviderMap(Class<? extends Mapper> mapperClass) {
        MapperUtils.registerMapperMap(mapperClass, this.getDbProvider());
    }

    /**
     * 注册通用 Mapper接口
     *
     * @param mapperClass
     */
    public void registerMapper(Class<?> mapperClass) {
        if (!registerMapperMap.containsKey(mapperClass)) {
            mapperInterfaceMap.add(mapperClass);
            registerMapperMap.put(mapperClass, registerMapperClass(mapperClass));
        }
        Class<?>[] interfaces = mapperClass.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> anInterface : interfaces) {
                registerMapper(anInterface);
            }
        }
    }

    /**
     * 注册
     *
     * @param mapperClass
     */
    public void registerMapper(String mapperClass) {
        try {
            registerMapper(Class.forName(mapperClass));
        } catch (ClassNotFoundException e) {
            throw new MapperException("注册[" + mapperClass + "]失败，没有找到类!");
        }
    }


    /**
     * 是否属于拦截方法
     *
     * @param msId
     * @return
     */
    private boolean isMapperMethod(String msId) {
        Boolean value = supportMappedStatementMap.get(msId);
        if (value != null) {
            return value;
        }
        for (Map.Entry<Class<?>, List<MapperProvider>> entry : registerMapperMap.entrySet()) {
            for (MapperProvider provider : entry.getValue()) {
                MapperInfo mapperInfo = provider.getMapperInfo(msId);
                if (mapperInfo != null) {
                    if (mapperInfo.getEntityClass() != null && !ENTITY_MAPPER_INFO_MAP.containsKey(mapperInfo.getEntityClass())) {
                        ENTITY_MAPPER_INFO_MAP.put(mapperInfo.getEntityClass(), mapperInfo);
                    }
                    supportMappedStatementMap.put(msId, true);
                    msIdProviderMap.put(msId, provider);
                    MSID_MAPPER_INFO_MAP.put(msId, mapperInfo);
                    return true;
                }
            }
        }
        supportMappedStatementMap.put(msId, false);
        return false;
    }

    /**
     * 设置
     *
     * @param ms
     */
    private void setSqlSource(DbProvider dbProvider, MappedStatement ms) {
        MapperProvider provider = msIdProviderMap.get(ms.getId());
        Class<?> msPapperClass = MapperUtils.getMapperClass(ms.getId());
        if (msPapperClass != null) {

        }
        try {
            if (provider != null) {
                provider.setSqlSource(ms);
            }
        } catch (Exception e) {
            throw new MapperException(e);
        }
    }

    /**
     * 是否已注册接口
     *
     * @param mapperInterface 类型
     * @return
     */
    public boolean isRegisterMapperInterface(Class<?> mapperInterface) {
        for (Class<?> mapperClass : mapperInterfaceMap) {
            if (mapperClass.isAssignableFrom(mapperInterface)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 配置
     *
     * @param configuration 配置
     */
    public void configure(Configuration configuration) {
        registerWithMapper();
        for (Object object : new ArrayList<Object>(configuration.getMappedStatements())) {
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                if (isMapperMethod(ms.getId())) {
                    if (ms.getSqlSource() instanceof ProviderSqlSource) {
                        setSqlSource(dbProvider, ms);
                    }
                }
            }
        }
    }

}
