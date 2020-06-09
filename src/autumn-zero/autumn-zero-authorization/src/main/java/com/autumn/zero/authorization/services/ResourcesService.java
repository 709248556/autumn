package com.autumn.zero.authorization.services;

import com.autumn.domain.services.DomainService;
import com.autumn.web.vo.UrlRequestMappingGroup;
import com.autumn.web.vo.UrlRequestMethodMappingInfo;
import com.autumn.zero.authorization.entities.common.PermissionEntity;
import com.autumn.zero.authorization.values.ResourcesModulePermissionTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleUrlRequestPermissionMappingInfoValue;
import com.autumn.zero.authorization.values.ResourcesModuleValue;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 资源服务抽象
 *
 * @author 老码农 2018-12-05 18:48:28
 */
public interface ResourcesService extends DomainService {

    /**
     * 基于Url权限 Map
     *
     * @return key = url ,value = 权限(资源:操作)
     */
    Map<String, Set<String>> urlPermissionMap();

    /**
     * 创建Url权限请求映射组集合
     *
     * @param urlRequestMappingInfos
     * @return
     */
    List<UrlRequestMappingGroup<ResourcesModuleUrlRequestPermissionMappingInfoValue>> createUrlPermissionRequestMappingGroups(List<UrlRequestMethodMappingInfo> urlRequestMappingInfos);

    /**
     * 创建Url权限请求映射信息集合
     *
     * @param urlRequestMappingInfos url请求集合
     * @return
     */
    List<ResourcesModuleUrlRequestPermissionMappingInfoValue> createUrlPermissionRequestMappingInfos(List<UrlRequestMethodMappingInfo> urlRequestMappingInfos);

    /**
     * 查询模块菜单树集合
     *
     * @param resourcesType 资源类型
     * @return
     */
    List<ResourcesModuleTreeValue> queryByMenuTree(int resourcesType);

    /**
     * 过滤模块树，可用于加载用户前端菜单
     *
     * @param moduleTree  模块树
     * @param permissions 权限集合
     * @return
     */
    <P extends PermissionEntity> List<ResourcesModuleTreeValue> filterModuleTree(
            List<ResourcesModuleTreeValue> moduleTree, List<P> permissions);

    /**
     * 查询资源模块树集合
     *
     * @return
     */
    List<ResourcesModuleTreeValue> queryByTree();

    /**
     * 查询资源模块树集合
     *
     * @param resourcesType 资源类型
     * @return
     */
    List<ResourcesModuleTreeValue> queryByTree(int resourcesType);

    /**
     * 查询资源模块子级
     *
     * @param parentId 父级id
     * @return
     */
    List<ResourcesModuleValue> queryChildren(String parentId);

    /**
     * 查询资源模块子级
     *
     * @param resourcesType 资源类型
     * @param parentId      父级id
     * @return
     */
    List<ResourcesModuleValue> queryChildren(int resourcesType, String parentId);

    /**
     * 查询模块权限树
     *
     * @return 均返回待授权状态
     */
    List<ResourcesModulePermissionTreeValue> queryByPermissionTree();

    /**
     * 查询模块权限树
     *
     * @param resourcesType 资源类型
     * @return 均返回待授权状态
     */
    List<ResourcesModulePermissionTreeValue> queryByPermissionTree(int resourcesType);

    /**
     * 匹配模块权限树
     *
     * @param permissions 权限集合
     * @return 如果权限与模块权限匹配则返回已授权，否则为未授权
     */
    <P extends PermissionEntity> List<ResourcesModulePermissionTreeValue> matchByPermissionTree(List<P> permissions);

    /**
     * 匹配模块权限树
     *
     * @param resourcesType 资源类型
     * @param permissions   权限集合
     * @return 如果权限与模块权限匹配则返回已授权，否则为未授权
     */
    <P extends PermissionEntity> List<ResourcesModulePermissionTreeValue> matchByPermissionTree(int resourcesType,
                                                                                                List<P> permissions);

    /**
     * 不改变原的的对象加载新的模块权限
     *
     * @param sources     源集合
     * @param permissions 权限集合
     * @return 返回新的匹配模块权限
     */
    <P extends PermissionEntity> List<ResourcesModulePermissionTreeValue> loadByPermissionTree(
            List<ResourcesModulePermissionTreeValue> sources, List<P> permissions);

    /**
     * 清除所有缓存
     */
    void clearAllCache();

    /**
     * 清除模块所有缓存
     */
    void clearModuleAllCache();

    /**
     * 清除模块缓存
     *
     * @param resourcesType 资源类型
     */
    void clearModuleCache(int resourcesType);

    /**
     * 清除模块权限所有缓存
     */
    void clearModuleAllPermissionCache();

    /**
     * 清除模块权限缓存
     *
     * @param resourcesType 资源类型
     */
    void clearModulePermissionCache(int resourcesType);
}
