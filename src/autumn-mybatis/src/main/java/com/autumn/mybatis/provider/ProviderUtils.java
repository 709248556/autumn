package com.autumn.mybatis.provider;

import com.autumn.exception.ConfigureException;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.provider.annotation.ProviderDrive;
import com.autumn.util.PackageUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

/**
 * 驱动帮助
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-11-04 02:56:23
 */
public class ProviderUtils {

    @SuppressWarnings("unchecked")
    private static final Map<ProviderDriveType, Class<? extends DbProvider>> DB_PROVIDER_MAP = new CaseInsensitiveMap(16);

    static {
        registerDbProviderClassByPackage(ProviderUtils.class.getPackage().getName());
    }

    /**
     * 是否为 DbProvider 的实现类型
     *
     * @param dbProviderClass 提供者类型
     * @return
     */
    public static boolean isDbProviderImplClass(Class<?> dbProviderClass) {
        if (dbProviderClass == null) {
            return false;
        }
        return DbProvider.class.isAssignableFrom(dbProviderClass)
                && !(dbProviderClass.isInterface() || Modifier.isAbstract(dbProviderClass.getModifiers()));
    }

    /**
     * 检查 DbProvider 的实现类型
     *
     * @param dbProviderClass
     */
    public static void checkDbProviderImplClass(Class<?> dbProviderClass) throws ConfigureException {
        if (!isDbProviderImplClass(dbProviderClass)) {
            ExceptionUtils.throwConfigureException(dbProviderClass + " 不是[" + DbProvider.class.getName() + "]实现类型。");
        }
    }

    /**
     * 获取配置的提供者类型
     *
     * @param driveType             驱动类型
     * @param configDbProviderClass 配置的提供者类型
     * @return
     */
    public static Class<? extends DbProvider> getConfigDbProviderClass(ProviderDriveType driveType,
                                                                       Class<? extends DbProvider> configDbProviderClass) {
        if (isDbProviderImplClass(configDbProviderClass)) {
            return configDbProviderClass;
        }
        Class<? extends DbProvider> providerClass = getDbProviderClass(driveType);
        if (providerClass == null) {
            ExceptionUtils.throwConfigureException(driveType + " 无法自动获取[" + DbProvider.class.getName() + "]的实现类型。");
        }
        return providerClass;
    }

    /**
     * 获取驱动类型
     *
     * @param driveType 驱动类型
     * @return
     */
    public static Class<? extends DbProvider> getDbProviderClass(ProviderDriveType driveType) {
        ExceptionUtils.checkNotNull(driveType, "driveType");
        Class<? extends DbProvider> providerType = DB_PROVIDER_MAP.get(driveType);
        if (providerType == null) {
            return driveType.getProviderType();
        }
        return providerType;
    }

    /**
     * 基于驱动名称创建数据驱动
     *
     * @param driveType 驱动类型
     * @return
     */
    public static DbProvider createDbProvider(ProviderDriveType driveType) {
        Class<?> providerType = getDbProviderClass(driveType);
        if (providerType == null) {
            ExceptionUtils.throwAutumnException("驱动 " + driveType + " 无对应的实现类型。");
        }
        try {
            return (DbProvider) providerType.newInstance();
        } catch (InstantiationException e) {
            throw ExceptionUtils.throwSystemException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw ExceptionUtils.throwSystemException(e.getMessage(), e);
        }
    }

    /**
     * 基于类型名称创建数据驱动
     *
     * @param driveName 驱动名称
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException,
     * @throws IllegalAccessException
     */
    public static DbProvider createDbProvider(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ExceptionUtils.checkNotNullOrBlank(className, "className");
        Class<?> providerType = Class.forName(className);
        checkDbProviderImplClass(providerType);
        return (DbProvider) providerType.newInstance();
    }

    /**
     * 注册驱动类型
     *
     * @param driveName    驱动名称
     * @param providerType 驱动类型
     */
    public static void registerDbProviderClass(Class<? extends DbProvider> providerType) {
        ExceptionUtils.checkNotNull(providerType, "providerType");
        ProviderDrive drive = providerType.getAnnotation(ProviderDrive.class);
        if (drive == null || drive.value() == null) {
            ExceptionUtils.throwAutumnException(providerType + " 未配置 " + ProviderDrive.class + " 的注解或注解。");
        }
        checkDbProviderImplClass(providerType);
        registerDbProviderClass(drive.value(), providerType);
    }

    /**
     * 注册驱动类型
     *
     * @param driveType    提供者类型
     * @param providerType 驱动类型
     */
    public static void registerDbProviderClass(ProviderDriveType driveType, Class<? extends DbProvider> providerType) {
        ExceptionUtils.checkNotNull(driveType, "driveType");
        ExceptionUtils.checkNotNull(providerType, "providerType");
        checkDbProviderImplClass(providerType);
        DB_PROVIDER_MAP.put(driveType, providerType);
    }

    /**
     * 按包注册Db驱动类型
     *
     * @param packageName 包名称
     */
    @SuppressWarnings("unchecked")
    public static void registerDbProviderClassByPackage(String packageName) {
        try {
            Set<Class<?>> providerTypeSet = PackageUtils.getPackageTargetClass(
                    ExceptionUtils.checkNotNullOrBlank(packageName, "packageName"), DbProvider.class, false, false,
                    true);
            for (Class<?> providerType : providerTypeSet) {
                ProviderDrive drive = providerType.getAnnotation(ProviderDrive.class);
                if (drive != null && isDbProviderImplClass(providerType)) {
                    DB_PROVIDER_MAP.put(drive.value(), (Class<? extends DbProvider>) providerType);
                }
            }

        } catch (Exception e) {
            ExceptionUtils.throwSystemException(e.getMessage(), e);
        }
    }
}
