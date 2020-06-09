package com.autumn.zero.authorization.services.impl;

import com.autumn.domain.services.AbstractDomainService;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.runtime.cache.ProxyCache;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.CollectionUtils;
import com.autumn.util.StringUtils;
import com.autumn.web.vo.UrlRequestMappingGroup;
import com.autumn.web.vo.UrlRequestMethodMappingInfo;
import com.autumn.zero.authorization.entities.common.PermissionEntity;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModulePermission;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModulePermissionRepository;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModuleRepository;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.values.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 资源服务
 *
 * @author 老码农 2018-12-05 18:49:47
 */
public class ResourcesServiceImpl extends AbstractDomainService implements ResourcesService {

    /**
     * 资源模块缓存名称
     */
    private static final String CACHE_NAME_MODULE = "cache_resources_module";

    /**
     * 资源权限缓存名称
     */
    private static final String CACHE_NAME_PERMISSION = "cache_resources_permission";

    /**
     * 模块树前缀
     */
    private static final String CACHE_KEY_MODULE_TREE_RESOURCES_TYPE_PREFIX = "module_tree_resources_type_";

    /**
     * 模块菜单树所有键
     */
    private static final String CACHE_KEY_MODULE_TREE_ALL_KEY = "module_tree_all";

    /**
     * 菜单树前缀
     */
    private static final String CACHE_KEY_MENU_TREE_RESOURCES_TYPE_PREFIX = "menu_tree_resources_type_";

    /**
     * 资源模块权限树缓存名称
     */
    private static final String CACHE_NAME_PERMISSION_RESOURCES_TYPE_PREFIX = "permission_tree_resources_type_";

    /**
     * 资源模块权限树缓存名称键
     */
    private static final String CACHE_NAME_PERMISSION_ALL_KEY = "permission_tree_all";

    @Override
    public void clearAllCache() {
        this.getCacheManager().clearCache(CACHE_NAME_MODULE);
        this.getCacheManager().clearCache(CACHE_NAME_PERMISSION);
        this.getLogger().info("清除资源所有缓存(模块与权限)");
    }

    @Override
    public void clearModuleAllCache() {
        this.getCacheManager().clearCache(CACHE_NAME_MODULE);
        this.getLogger().info("清除资源模块所有缓存");
    }

    @Override
    public void clearModuleCache(int resourcesType) {
        ProxyCache cache = this.getCacheManager().getCache(CACHE_NAME_MODULE);
        cache.evict(CACHE_KEY_MODULE_TREE_RESOURCES_TYPE_PREFIX + resourcesType,
                CACHE_KEY_MODULE_TREE_ALL_KEY, CACHE_KEY_MENU_TREE_RESOURCES_TYPE_PREFIX + resourcesType);
        this.getLogger().info("清除资源模块[" + resourcesType + "]的所有缓存");
        this.clearModulePermissionCache(resourcesType);
    }

    @Override
    public void clearModuleAllPermissionCache() {
        this.getCacheManager().clearCache(CACHE_NAME_PERMISSION);
        this.getLogger().info("清除资源模块的所有权限缓存");
    }

    @Override
    public void clearModulePermissionCache(int resourcesType) {
        ProxyCache cache = this.getCacheManager().getCache(CACHE_NAME_PERMISSION);
        cache.evict(CACHE_NAME_PERMISSION_RESOURCES_TYPE_PREFIX + resourcesType
                , CACHE_NAME_PERMISSION_ALL_KEY);
        this.getLogger().info("清除资源模块[" + resourcesType + "]权限的缓存");
    }

    @Autowired
    private ResourcesModuleRepository resourcesModuleRepository;

    @Autowired
    private ResourcesModulePermissionRepository resourcesModulePermissionRepository;

    @Override
    public Map<String, Set<String>> urlPermissionMap() {
        List<ResourcesModule> modules = this.queryAllResourcesModule();
        List<ResourcesModulePermission> permissions = this.queryAllModulePermission();
        Map<String, Set<String>> filterMap = new HashMap<>(modules.size() + permissions.size());
        this.addPermissionMap(filterMap, modules, item -> item.getPermissionUrl(), item -> item.getId());
        this.addPermissionMap(filterMap, permissions, item -> item.getPermissionUrl(), item -> item.getResourcesId() + ":" + item.getName());
        return filterMap;
    }

    private <E> void addPermissionMap(Map<String, Set<String>> filterMap,
                                      List<E> items, Function<E, String> urlFun,
                                      Function<E, String> valueFun) {
        for (E item : items) {
            String url = urlFun.apply(item);
            if (StringUtils.isNotNullOrBlank(url)) {
                String[] urlPaths = StringUtils.urlOrPackageToStringArray(url);
                for (String urlPath : urlPaths) {
                    String key = "/" + StringUtils.removeStart(urlPath.toLowerCase(), '/');
                    Set<String> resourcesSet = filterMap.computeIfAbsent(key, k -> new HashSet<>(16));
                    resourcesSet.add(valueFun.apply(item).toLowerCase());
                }
            }
        }
    }

    /**
     * 查询资源授权的所有模块
     *
     * @return
     */
    private List<ResourcesModule> queryAllResourcesModule() {
        EntityQueryWrapper<ResourcesModule> query = new EntityQueryWrapper<>(ResourcesModule.class);
        query.where().notEq(ResourcesModule.FIELD_PERMISSION_URL, "")
                .of()
                .orderBy(ResourcesModule.FIELD_ID);
        return this.resourcesModuleRepository.selectForList(query);
    }

    /**
     * 查询模块所有权限
     *
     * @return
     */
    private List<ResourcesModulePermission> queryAllModulePermission() {
        EntityQueryWrapper<ResourcesModulePermission> query = new EntityQueryWrapper<>(ResourcesModulePermission.class);
        query.where().notEq(ResourcesModulePermission.FIELD_PERMISSION_URL, "")
                .of().orderBy(ResourcesModulePermission.FIELD_ID);
        return this.resourcesModulePermissionRepository.selectForList(query);
    }

    @Override
    public List<UrlRequestMappingGroup<ResourcesModuleUrlRequestPermissionMappingInfoValue>> createUrlPermissionRequestMappingGroups(List<UrlRequestMethodMappingInfo> urlRequestMappingInfos) {
        List<ResourcesModuleUrlRequestPermissionMappingInfoValue> items = this.createUrlPermissionRequestMappingInfos(urlRequestMappingInfos);
        List<UrlRequestMappingGroup<ResourcesModuleUrlRequestPermissionMappingInfoValue>> groups = new ArrayList<>(items.size());
        Map<String, UrlRequestMappingGroup<ResourcesModuleUrlRequestPermissionMappingInfoValue>> map = new LinkedHashMap<>(groups.size());
        for (ResourcesModuleUrlRequestPermissionMappingInfoValue item : items) {
            UrlRequestMappingGroup<ResourcesModuleUrlRequestPermissionMappingInfoValue> mappingGroup = map.computeIfAbsent(item.getControllerName(), key -> {
                UrlRequestMappingGroup<ResourcesModuleUrlRequestPermissionMappingInfoValue> group = new UrlRequestMappingGroup<>();
                group.setControllerName(item.getControllerName());
                group.setControllerExplain(item.getControllerExplain());
                group.setMappings(new ArrayList<>(16));
                groups.add(group);
                return group;
            });
            mappingGroup.getMappings().add(item);
        }
        map.clear();
        groups.sort(UrlRequestMappingGroup::compareTo);
        return groups;
    }

    @Override
    public List<ResourcesModuleUrlRequestPermissionMappingInfoValue> createUrlPermissionRequestMappingInfos(List<UrlRequestMethodMappingInfo> urlRequestMappingInfos) {
        List<ResourcesModuleUrlRequestPermissionMappingInfoValue> mappingInfos;
        if (urlRequestMappingInfos != null) {
            mappingInfos = new ArrayList<>(urlRequestMappingInfos.size());
            List<ResourcesModulePermissionTreeValue> permissionTrees = this.queryByPermissionTree();
            Map<String, ResourcesModulePermissionTreeValue> permissionTreeMap = new LinkedHashMap<>(permissionTrees.size());
            this.loadPermissionTree(permissionTreeMap, permissionTrees);
            Map<String, Set<String>> urlMap = this.urlPermissionMap();
            for (UrlRequestMethodMappingInfo methodMappingInfo : urlRequestMappingInfos) {
                ResourcesModuleUrlRequestPermissionMappingInfoValue mappingInfo = AutoMapUtils.map(methodMappingInfo, ResourcesModuleUrlRequestPermissionMappingInfoValue.class);
                Set<String> ops = new HashSet<>(16);
                Set<String> urlOps = urlMap.get(methodMappingInfo.getUrl().toLowerCase());
                if (urlOps != null) {
                    ops.addAll(urlOps);
                }
                RequiresPermissions requiresPermissions = methodMappingInfo.getMethod().getAnnotation(RequiresPermissions.class);
                if (requiresPermissions != null) {
                    for (String s : requiresPermissions.value()) {
                        if (StringUtils.isNotNullOrBlank(s)) {
                            ops.add(s);
                        }
                    }
                }
                Map<String, Set<String>> handlePermissionMap = this.getHandlePermissionMap(ops);
                Map<String, Set<String>> handlePermissionExplainMap = this.getHandlePermissionExplainMap(permissionTreeMap, handlePermissionMap);
                mappingInfo.setPermissions(this.getHandlePermissionInfo(handlePermissionMap));
                mappingInfo.setPermissionExplains(this.getHandlePermissionInfo(handlePermissionExplainMap));
                Set<String> mods = new LinkedHashSet<>(handlePermissionMap.keySet().size());
                for (String s : handlePermissionMap.keySet()) {
                    ResourcesModulePermissionTreeValue permissionTreeValue = permissionTreeMap.get(s);
                    if (permissionTreeValue != null) {
                        mods.add(permissionTreeValue.getCustomName());
                    }
                }
                mappingInfo.setPermissionVisitModuleNames(String.join(";", mods));
                if (handlePermissionMap.size() > 0 || requiresPermissions != null) {
                    mappingInfo.setPermissionVisit(true);
                } else {
                    boolean iVisit = methodMappingInfo.getMethod().getAnnotation(RequiresUser.class) != null
                            || methodMappingInfo.getMethod().getAnnotation(RequiresRoles.class) != null
                            || methodMappingInfo.getMethod().getAnnotation(RequiresAuthentication.class) != null
                            || methodMappingInfo.getMethod().getDeclaringClass().getAnnotation(RequiresUser.class) != null
                            || methodMappingInfo.getMethod().getDeclaringClass().getAnnotation(RequiresRoles.class) != null
                            || methodMappingInfo.getMethod().getDeclaringClass().getAnnotation(RequiresPermissions.class) != null
                            || methodMappingInfo.getMethod().getDeclaringClass().getAnnotation(RequiresAuthentication.class) != null;
                    mappingInfo.setPermissionVisit(iVisit);
                }
                StringBuilder visitBuilder = new StringBuilder(20);
                if (mappingInfo.isPermissionVisit()) {
                    if (handlePermissionMap.size() > 0 || requiresPermissions != null) {
                        visitBuilder.append("权限访问");
                    } else {
                        if (methodMappingInfo.getMethod().getDeclaringClass().getAnnotation(RequiresPermissions.class) != null) {
                            visitBuilder.append("权限访问");
                        }
                    }
                    this.addVisitInfo(methodMappingInfo.getMethod(), visitBuilder, RequiresRoles.class, "角色访问");
                    this.addVisitInfo(methodMappingInfo.getMethod(), visitBuilder, RequiresUser.class, "登录访问");
                    this.addVisitInfo(methodMappingInfo.getMethod(), visitBuilder, RequiresAuthentication.class, "授权访问");
                }
                if (visitBuilder.length() > 0) {
                    mappingInfo.setPermissionVisitExplain("[" + visitBuilder.toString() + "]");
                } else {
                    mappingInfo.setPermissionVisitExplain("");
                }
                mappingInfos.add(mappingInfo);
            }
        } else {
            mappingInfos = new ArrayList<>(0);
        }
        mappingInfos.sort(ResourcesModuleUrlRequestPermissionMappingInfoValue::compareTo);
        return mappingInfos;
    }

    private <T extends Annotation> void addVisitInfo(Method method, StringBuilder visitBuilder, Class<T> annotation, String msg) {
        if (method.getAnnotation(annotation) != null || method.getDeclaringClass().getAnnotation(annotation) != null) {
            if (visitBuilder.length() > 0) {
                visitBuilder.append(",");
            }
            visitBuilder.append(msg);
        }
    }

    /**
     * 获取处理的权限Map
     *
     * @param permissions
     * @return
     */
    private Map<String, Set<String>> getHandlePermissionMap(Collection<String> permissions) {
        Map<String, Set<String>> map;
        if (permissions == null) {
            map = new LinkedHashMap<>(0);
        } else {
            map = new LinkedHashMap<>(permissions.size());
            for (String permission : permissions) {
                if (StringUtils.isNotNullOrBlank(permission)) {
                    String[] perms = permission.split(":");
                    String resourcesId = perms[0].toLowerCase().trim();
                    Set<String> ops = map.computeIfAbsent(resourcesId, key -> {
                        return new LinkedHashSet<>(16);
                    });
                    if (perms.length == 2) {
                        ops.add(perms[1].toLowerCase().trim());
                    } else {
                        ops.add("");
                    }
                }
            }
        }
        return map;
    }

    /**
     * 加载权限树
     *
     * @param trees
     */
    private void loadPermissionTree(Map<String, ResourcesModulePermissionTreeValue> permissionTreeMap, List<ResourcesModulePermissionTreeValue> trees) {
        if (trees != null && trees.size() > 0) {
            for (ResourcesModulePermissionTreeValue tree : trees) {
                permissionTreeMap.put(tree.getResourcesId().toLowerCase(), tree);
                this.loadPermissionTree(permissionTreeMap, tree.getChildren());
            }
        }
    }

    /**
     * 获取处理权限说明
     *
     * @param permissionMap
     * @return
     */
    private Map<String, Set<String>> getHandlePermissionExplainMap(Map<String, ResourcesModulePermissionTreeValue> permissionTreeMap, Map<String, Set<String>> permissionMap) {
        Map<String, Set<String>> permissionExplainMap = new LinkedHashMap<>(permissionMap.size());
        if (permissionMap.size() > 0) {
            for (Map.Entry<String, Set<String>> entry : permissionMap.entrySet()) {
                ResourcesModulePermissionTreeValue treeValue = permissionTreeMap.get(entry.getKey());
                if (treeValue != null) {
                    Set<String> ops = permissionExplainMap.computeIfAbsent(treeValue.getCustomName(), key -> {
                        return new LinkedHashSet<>(16);
                    });
                    if (treeValue.getOperationPermissions() != null) {
                        for (ResourcesModuleOperationPermissionValue operationPermission : treeValue.getOperationPermissions()) {
                            if (entry.getValue().contains(operationPermission.getPermissionName().toLowerCase())) {
                                ops.add(operationPermission.getFriendlyName());
                            }
                        }
                    }
                    if (entry.getValue().stream().filter(s -> StringUtils.isNullOrBlank(s)).count() > 0) {
                        ops.add("");
                    }
                }
            }
        }
        return permissionExplainMap;
    }

    /**
     * 获取处理权限信息
     *
     * @param permissionMap 权限Map
     * @return
     */
    private String getHandlePermissionInfo(Map<String, Set<String>> permissionMap) {
        if (permissionMap == null || permissionMap.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(permissionMap.size() * 100);
        int index = 0;
        for (Map.Entry<String, Set<String>> entry : permissionMap.entrySet()) {
            if (index > 0) {
                builder.append(";");
            }
            StringBuilder opBuilder = new StringBuilder(30);
            boolean addRoot = false;
            if (entry.getValue() != null && entry.getValue().size() > 0) {
                int i = 0;
                for (String s : entry.getValue()) {
                    if (StringUtils.isNotNullOrBlank(s)) {
                        if (i > 0) {
                            opBuilder.append(",");
                        }
                        opBuilder.append(s);
                        i++;
                    } else {
                        addRoot = true;
                    }
                }
            }
            builder.append(entry.getKey());
            if (addRoot && opBuilder.length() > 0) {
                builder.append(";");
                builder.append(entry.getKey());
            }
            if (opBuilder.length() > 0) {
                builder.append("[");
                builder.append(opBuilder.toString());
                builder.append("]");
            }
            index++;
        }
        return builder.toString();
    }

    @Override
    public <P extends PermissionEntity> List<ResourcesModuleTreeValue> filterModuleTree(
            List<ResourcesModuleTreeValue> moduleTree, List<P> permissions) {
        List<ResourcesModuleTreeValue> result = new ArrayList<>();
        if (moduleTree == null || moduleTree.size() == 0) {
            return result;
        }
        for (ResourcesModuleTreeValue source : moduleTree) {
            ResourcesModuleTreeValue treeValue = this.newModuleTree(source, permissions);
            if (treeValue != null) {
                result.add(treeValue);
            }
        }
        return result;
    }

    private <P extends PermissionEntity> ResourcesModuleTreeValue newModuleTree(ResourcesModuleTreeValue source,
                                                                                List<P> matchPermissions) {
        boolean isGranted;
        if (source.getIsAuthorize()) {
            if (matchPermissions == null || matchPermissions.size() == 0) {
                isGranted = false;
            } else {
                isGranted = matchPermissions.stream().anyMatch(p -> p.getIsGranted() && p.getResourcesId().equalsIgnoreCase(source.getId())
                        && StringUtils.isNullOrBlank(p.getName()));
            }
        } else {
            isGranted = true;
        }
        if (!isGranted) {
            return null;
        }
        ResourcesModuleTreeValue destination = new ResourcesModuleTreeValue();
        AutoMapUtils.mapForLoad(source, destination);
        destination.setChildren(null);
        if (source.getChildren() != null && source.getChildren().size() > 0) {
            for (ResourcesModuleTreeValue child : source.getChildren()) {
                ResourcesModuleTreeValue treeValue = this.newModuleTree(child, matchPermissions);
                if (treeValue != null) {
                    destination.childrenNullValueInit();
                    destination.getChildren().add(treeValue);
                }
            }
        }
        return destination;
    }

    @Override
    public List<ResourcesModuleTreeValue> queryByMenuTree(int resourcesType) {
        String key = CACHE_KEY_MENU_TREE_RESOURCES_TYPE_PREFIX + resourcesType;
        ProxyCache cache = this.getCacheManager().getCache(CACHE_NAME_MODULE);
        return cache.get(key, () -> {
            QueryWrapper query = new QueryWrapper(ResourcesModule.class);
            query.where().eq(ResourcesModule.FIELD_RESOURCES_TYPE, resourcesType).eq(ResourcesModule.FIELD_IS_MENU, true)
                    .of().orderBy(ResourcesModule.FIELD_PARENT_ID).orderBy(ResourcesModule.FIELD_SORT_ID)
                    .orderBy(ResourcesModule.FIELD_ID);
            return this.queryModuleByTree(query);
        });
    }

    @Override
    public List<ResourcesModuleTreeValue> queryByTree() {
        ProxyCache cache = this.getCacheManager().getCache(CACHE_NAME_MODULE);
        return cache.get(CACHE_KEY_MODULE_TREE_ALL_KEY, () -> {
            QueryWrapper query = new QueryWrapper(ResourcesModule.class);
            query.orderBy(ResourcesModule.FIELD_PARENT_ID).orderBy(ResourcesModule.FIELD_SORT_ID)
                    .orderBy(ResourcesModule.FIELD_ID);
            return this.queryModuleByTree(query);

        });
    }

    @Override
    public List<ResourcesModuleTreeValue> queryByTree(int resourcesType) {
        String key = CACHE_KEY_MODULE_TREE_RESOURCES_TYPE_PREFIX + resourcesType;
        ProxyCache cache = this.getCacheManager().getCache(CACHE_NAME_MODULE);
        return cache.get(key, () -> {
            QueryWrapper query = new QueryWrapper(ResourcesModule.class);
            query.where().eq(ResourcesModule.FIELD_RESOURCES_TYPE, resourcesType).of().orderBy(ResourcesModule.FIELD_PARENT_ID)
                    .orderBy(ResourcesModule.FIELD_SORT_ID).orderBy(ResourcesModule.FIELD_ID);
            return this.queryModuleByTree(query);
        });
    }

    @Override
    public List<ResourcesModuleValue> queryChildren(String parentId) {
        QueryWrapper query = new QueryWrapper(ResourcesModule.class);
        if (StringUtils.isNullOrBlank(parentId)) {
            parentId = "";
        }
        query.where().eq(ResourcesModule.FIELD_PARENT_ID, parentId)
                .of()
                .orderBy(ResourcesModule.FIELD_SORT_ID)
                .orderBy(ResourcesModule.FIELD_ID);
        List<ResourcesModule> modules = resourcesModuleRepository.selectForList(query);
        return AutoMapUtils.mapForList(modules, ResourcesModuleValue.class);
    }

    @Override
    public List<ResourcesModuleValue> queryChildren(int resourcesType, String parentId) {
        if (StringUtils.isNullOrBlank(parentId)) {
            parentId = "";
        }
        QueryWrapper query = new QueryWrapper(ResourcesModule.class);
        query.where().eq(ResourcesModule.FIELD_RESOURCES_TYPE, resourcesType)
                .eq(ResourcesModule.FIELD_PARENT_ID, parentId)
                .of()
                .orderBy(ResourcesModule.FIELD_SORT_ID)
                .orderBy(ResourcesModule.FIELD_ID);
        List<ResourcesModule> modules = resourcesModuleRepository.selectForList(query);
        return AutoMapUtils.mapForList(modules, ResourcesModuleValue.class);
    }

    private List<ResourcesModuleTreeValue> queryModuleByTree(QueryWrapper query) {
        List<ResourcesModule> modules = resourcesModuleRepository.selectForList(query);
        List<ResourcesModuleTreeValue> result = new ArrayList<>();
        modules.forEach(module -> {
            if (StringUtils.isNullOrBlank(module.getParentId())) {
                result.addAll(findModuleChilds(module, modules, 0, null));
            }
        });
        return result;
    }

    /**
     * 递归树
     *
     * @param item
     * @param modules
     * @param level
     * @param parent
     * @return
     */
    private List<ResourcesModuleTreeValue> findModuleChilds(ResourcesModule item, List<ResourcesModule> modules,
                                                            int level, ResourcesModuleTreeValue parent) {
        List<ResourcesModule> moduleList = CollectionUtils.findCollection(modules, (s) -> item.getId().equalsIgnoreCase(s.getParentId()));
        List<ResourcesModuleTreeValue> result = new ArrayList<>();
        ResourcesModuleTreeValue moduleValue = AutoMapUtils.map(item, ResourcesModuleTreeValue.class);
        if (moduleList.size() > 0) {
            moduleValue.childrenNullValueInit();
        }
        moduleValue.setPath(new ArrayList<>());
        if (parent != null) {
            // 先加载父级路径
            if (parent.getPath() != null) {
                moduleValue.getPath().addAll(parent.getPath());
            }
        }
        moduleValue.getPath().add(moduleValue.getId());
        for (ResourcesModule module : moduleList) {
            moduleValue.getChildren().addAll(findModuleChilds(module, modules, level + 1, moduleValue));
        }
        result.add(moduleValue);
        return result;
    }

    @Override
    public List<ResourcesModulePermissionTreeValue> queryByPermissionTree() {
        ProxyCache cache = this.getCacheManager().getCache(CACHE_NAME_PERMISSION);
        return cache.get(CACHE_NAME_PERMISSION_ALL_KEY, () -> {
            EntityQueryWrapper<ResourcesModule> query = new EntityQueryWrapper<>(ResourcesModule.class);
            query.where().eq(ResourcesModule.FIELD_IS_AUTHORIZE, true).of().orderBy(ResourcesModule.FIELD_PARENT_ID)
                    .orderBy(ResourcesModule.FIELD_SORT_ID).orderBy(ResourcesModule.FIELD_ID);
            return this.queryByPermissionTree(query);
        });

    }

    @Override
    public List<ResourcesModulePermissionTreeValue> queryByPermissionTree(int resourcesType) {
        ProxyCache cache = this.getCacheManager().getCache(CACHE_NAME_PERMISSION);
        String key = CACHE_NAME_PERMISSION_RESOURCES_TYPE_PREFIX + resourcesType;
        return cache.get(key, () -> {
            EntityQueryWrapper<ResourcesModule> query = new EntityQueryWrapper<>(ResourcesModule.class);
            query.where().eq(ResourcesModule.FIELD_RESOURCES_TYPE, resourcesType).eq(ResourcesModule.FIELD_IS_AUTHORIZE, true)
                    .of().orderBy(ResourcesModule.FIELD_PARENT_ID).orderBy(ResourcesModule.FIELD_SORT_ID)
                    .orderBy(ResourcesModule.FIELD_ID);
            return this.queryByPermissionTree(query);
        });
    }

    /**
     * @param query
     * @return
     */
    private List<ResourcesModulePermissionTreeValue> queryByPermissionTree(EntityQueryWrapper<ResourcesModule> query) {
        List<ResourcesModule> modules = this.resourcesModuleRepository.selectForList(query);
        // 查询所有模块定义的权限
        EntityQueryWrapper<ResourcesModulePermission> modulePermissionQuery = new EntityQueryWrapper<>(
                ResourcesModulePermission.class);
        modulePermissionQuery.orderBy(ResourcesModulePermission.FIELD_RESOURCES_ID)
                .orderBy(ResourcesModulePermission.FIELD_SORT_ID).orderBy(ResourcesModulePermission.FIELD_ID);
        List<ResourcesModulePermission> modulePermissions = this.resourcesModulePermissionRepository
                .selectForList(modulePermissionQuery);
        List<ResourcesModulePermissionTreeValue> result = new ArrayList<>();
        modules.forEach(module -> {
            if (StringUtils.isNullOrBlank(module.getParentId())) {
                result.addAll(findModulePermissionChilds(module, modules, modulePermissions));
            }
        });
        return result;
    }

    /**
     * 递归树
     *
     * @param item
     * @param modules
     * @param modulePermissions
     * @return
     */
    private <P extends PermissionEntity> List<ResourcesModulePermissionTreeValue> findModulePermissionChilds(
            ResourcesModule item, List<ResourcesModule> modules, List<ResourcesModulePermission> modulePermissions) {
        List<ResourcesModule> moduleList = CollectionUtils.findCollection(modules, (s) -> item.getId().equalsIgnoreCase(s.getParentId()));
        List<ResourcesModulePermissionTreeValue> result = new ArrayList<>();
        ResourcesModulePermissionTreeValue moduleValue = AutoMapUtils.map(item,
                ResourcesModulePermissionTreeValue.class);
        assert moduleValue != null;
        moduleValue.setResourcesId(item.getId());
        moduleValue.setIsGranted(false);
        moduleValue.setPermissionName("");
        modulePermissions.stream().filter(p -> p.getResourcesId().equalsIgnoreCase(item.getId())).peek(m -> {
            ResourcesModuleOperationPermissionValue opValue = new ResourcesModuleOperationPermissionValue();
            opValue.setPermissionName(m.getName());
            opValue.setIsGranted(false);
            opValue.setFriendlyName(m.getFriendlyName());
            opValue.setSummary(m.getSummary());
            if (moduleValue.getOperationPermissions() == null) {
                moduleValue.setOperationPermissions(new ArrayList<>());
            }
            moduleValue.getOperationPermissions().add(opValue);
        }).collect(Collectors.toList());
        if (moduleList.size() > 0) {
            moduleValue.childrenNullValueInit();
        }
        for (ResourcesModule module : moduleList) {
            moduleValue.getChildren().addAll(findModulePermissionChilds(module, modules, modulePermissions));
        }
        result.add(moduleValue);
        return result;
    }

    @Override
    public <P extends PermissionEntity> List<ResourcesModulePermissionTreeValue> matchByPermissionTree(
            List<P> permissions) {
        return this.loadByPermissionTree(this.queryByPermissionTree(), permissions);
    }

    @Override
    public <P extends PermissionEntity> List<ResourcesModulePermissionTreeValue> matchByPermissionTree(
            int resourcesType, List<P> permissions) {
        return this.loadByPermissionTree(this.queryByPermissionTree(resourcesType), permissions);
    }

    @Override
    public <P extends PermissionEntity> List<ResourcesModulePermissionTreeValue> loadByPermissionTree(
            List<ResourcesModulePermissionTreeValue> sources, List<P> matchPermissions) {
        List<ResourcesModulePermissionTreeValue> result = new ArrayList<>();
        if (sources == null || sources.size() == 0) {
            return result;
        }
        for (ResourcesModulePermissionTreeValue source : sources) {
            result.add(this.newModulePermissionTree(source, matchPermissions));
        }
        return result;
    }

    private <P extends PermissionEntity> ResourcesModulePermissionTreeValue newModulePermissionTree(
            ResourcesModulePermissionTreeValue source, List<P> matchPermissions) {
        boolean isGranted;
        if (matchPermissions == null || matchPermissions.size() == 0) {
            isGranted = false;
        } else {
            isGranted = matchPermissions.stream().anyMatch(p -> p.getIsGranted() && p.getResourcesId().equalsIgnoreCase(source.getResourcesId())
                    && StringUtils.isNullOrBlank(p.getName()));
        }
        ResourcesModulePermissionTreeValue destination = new ResourcesModulePermissionTreeValue();
        AutoMapUtils.mapForLoad(source, destination);
        destination.setIsGranted(isGranted);
        destination.setChildren(null);
        destination.setOperationPermissions(null);
        if (source.getOperationPermissions() != null) {
            for (ResourcesModuleOperationPermissionValue sourceOpValue : source.getOperationPermissions()) {
                if (matchPermissions == null || matchPermissions.size() == 0) {
                    isGranted = false;
                } else {
                    isGranted = matchPermissions.stream().anyMatch(p -> p.getIsGranted() && p.getResourcesId().equalsIgnoreCase(source.getResourcesId())
                            && p.getName().equalsIgnoreCase(sourceOpValue.getPermissionName()));
                }
                ResourcesModuleOperationPermissionValue opValue = new ResourcesModuleOperationPermissionValue();
                opValue.setFriendlyName(sourceOpValue.getFriendlyName());
                opValue.setPermissionName(sourceOpValue.getPermissionName());
                opValue.setSummary(sourceOpValue.getSummary());
                opValue.setIsGranted(isGranted);
                if (destination.getOperationPermissions() == null) {
                    destination.setOperationPermissions(new ArrayList<>(16));
                }
                destination.getOperationPermissions().add(opValue);
            }
        }
        if (source.getChildren() != null && source.getChildren().size() > 0) {
            destination.childrenNullValueInit();
            for (ResourcesModulePermissionTreeValue child : source.getChildren()) {
                destination.getChildren().add(this.newModulePermissionTree(child, matchPermissions));
            }
        }
        return destination;
    }


}
