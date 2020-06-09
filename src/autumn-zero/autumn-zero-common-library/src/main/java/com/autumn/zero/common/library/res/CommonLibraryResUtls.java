package com.autumn.zero.common.library.res;

import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.postgresql.PostgreSqlProvider;
import com.autumn.util.ResourceUtils;
import com.autumn.zero.authorization.plugins.data.ResourcesModuleData;

import java.util.List;

/**
 * 公共库资源工具
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 01:59
 **/
public class CommonLibraryResUtls {

    /**
     * 基础路径
     */
    public static final String BASE_PATH = ResourceUtils.convertClassNameToResourcePath(CommonLibraryResUtls.class.getPackage().getName());

    /**
     * 公共库资源模块路径
     */
    public static final String COMMON_LIBRARY_RESOURCES_MODULE_DATA_PATH = BASE_PATH + "/CommonLibraryResourcesModuleData.json";

    /**
     * 公共库资源行政区脚本
     */
    public static final String COMMON_LIBRARY_REGION_SCRIPT = BASE_PATH + "/CommonRegion.sql";

    /**
     * 公共库资源行政区(PostgreSQL)脚本
     */
    public static final String COMMON_LIBRARY_REGION_POSTGRE_SCRIPT = BASE_PATH + "/CommonRegion_Postgre.sql";

    /**
     * 读取公共库资源模块数据集合
     *
     * @return
     */
    public static List<ResourcesModuleData> readResourcesModuleDatas() {
        return ResourceUtils.readResList(COMMON_LIBRARY_RESOURCES_MODULE_DATA_PATH, ResourcesModuleData.class);
    }

    /**
     * 读取行政区初始化脚本
     *
     * @param dbProvider 提供程序
     * @return
     */
    public static String readRegionInitScript(DbProvider dbProvider) {
        if (dbProvider instanceof PostgreSqlProvider) {
            return ResourceUtils.readResString(COMMON_LIBRARY_REGION_POSTGRE_SCRIPT);
        }
        return ResourceUtils.readResString(COMMON_LIBRARY_REGION_SCRIPT);
    }

}
