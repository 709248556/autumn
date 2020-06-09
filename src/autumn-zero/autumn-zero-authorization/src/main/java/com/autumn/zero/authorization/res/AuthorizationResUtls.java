package com.autumn.zero.authorization.res;

import com.autumn.util.ResourceUtils;
import com.autumn.zero.authorization.plugins.data.ResourcesModuleData;

import java.util.List;

/**
 * 授权资源工具
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 19:44
 **/
public class AuthorizationResUtls {

    /**
     * 基础路径
     */
    public static final String BASE_PATH = ResourceUtils.convertClassNameToResourcePath(AuthorizationResUtls.class.getPackage().getName());

    /**
     * 授权资源模块路径
     */
    public static final String AUTHORIZATION_RESOURCES_MODULE_DATA_PATH = BASE_PATH + "/AuthorizationResourcesModuleData.json";

    /**
     * 读取资源权限模块数据集合
     *
     * @return
     */
    public static List<ResourcesModuleData> readResourcesModuleDatas() {
        return ResourceUtils.readResList(AUTHORIZATION_RESOURCES_MODULE_DATA_PATH, ResourcesModuleData.class);
    }


}
