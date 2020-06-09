package com.autumn.zero.authorization.constants;

import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 资源模块常量
 *
 * @author 老码农 2018-12-10 01:43:44
 */
public final class ResourcesModuleConstants implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1754537345064578827L;

    /**
     * 系统管理
     */
    public static final String SYS = "sys";

    /**
     * 权限管理
     */
    public static final String SYS_PERMISSION = SYS + "_permission";

    /**
     * 角色管理
     */
    public static final String SYS_PERMISSION_ROLE = SYS_PERMISSION + "_role";

    /**
     * 用户管理
     */
    public static final String SYS_PERMISSION_USER = SYS_PERMISSION + "_user";

    /**
     * 日志管理
     */
    public static final String SYS_LOG = SYS + "_log";

    /**
     * 操作日志
     */
    public static final String SYS_LOG_OPERATION = SYS_LOG + "_operation";

    /**
     * 登录日志
     */
    public static final String SYS_LOG_LONGIN = SYS_LOG + "_login";

    /**
     * 模块管理
     */
    public static final String SYS_MODULE = SYS + "_module";

    /**
     * 模块资源
     */
    public static final String SYS_MODULE_RESOURCES = SYS_MODULE + "_resources";

    @SuppressWarnings("unchecked")
    private static final Map<String, ResourcesModule> NAME_MAP = new CaseInsensitiveMap();

    static {
        ResourcesModule module;

        module = createResourcesModule(SYS, "系统管理", "", 10000, "", "提供系统参数、配置等相关");
        NAME_MAP.put(module.getId(), module);

        module = createResourcesModule(SYS_PERMISSION, "权限管理", SYS, 1000, "", "提供权限管理依据");
        NAME_MAP.put(module.getId(), module);
        module = createResourcesModule(SYS_PERMISSION_ROLE, "角色管理", SYS_PERMISSION, 1, "", "提供角色管理、授权等操作");
        NAME_MAP.put(module.getId(), module);
        module = createResourcesModule(SYS_PERMISSION_USER, "用户管理", SYS_PERMISSION, 2, "", "提供用户管理、重置密码、授权等操作");
        NAME_MAP.put(module.getId(), module);

        module = createResourcesModule(SYS_LOG, "日志管理", SYS, 2000, "", "提供数据与日志");
        NAME_MAP.put(module.getId(), module);
        module = createResourcesModule(SYS_LOG_OPERATION, "操作日志", SYS_LOG, 1, "", "提供系统操作日志");
        NAME_MAP.put(module.getId(), module);
        module = createResourcesModule(SYS_LOG_LONGIN, "登录日志", SYS_LOG, 2, "", "提供登录的相关日志");
        NAME_MAP.put(module.getId(), module);

        module = createResourcesModule(SYS_MODULE, "模块管理", SYS, 3000, "", "提供模块配置、注册");
        NAME_MAP.put(module.getId(), module);
        module = createResourcesModule(SYS_MODULE_RESOURCES, "模块资源", SYS_MODULE, 1, "", "提供模块资源注册、配置");
        NAME_MAP.put(module.getId(), module);

    }

    private static ResourcesModule createResourcesModule(String id, String name, String parentId, int sortId,
                                                         String permissionUrl, String summary) {
        ResourcesModule module = new ResourcesModule();
        module.setId(id);
        module.setName(name);
        module.setCustomName(name);
        module.setParentId(parentId);
        module.setResourcesType(1);
        module.setSortId(sortId);
        module.setPermissionUrl(permissionUrl);
        module.setIsAuthorize(true);
        module.setIsMenu(true);
        module.setIsSysModule(true);
        module.setSummary(summary);
        return module;
    }

    /**
     * 是否存在
     *
     * @param value 值
     * @return
     */
    public static boolean exist(String value) {
        if (value == null) {
            return false;
        }
        return NAME_MAP.containsKey(value);
    }

    /**
     * 获取名称
     *
     * @param value 值
     * @return
     */
    public static String getName(String value) {
        ResourcesModule item = NAME_MAP.get(value);
        if (item == null) {
            return "";
        }
        return item.getName();
    }

    /**
     * 获取项目
     *
     * @param value 值
     * @return
     */
    public static ResourcesModule getItem(String value) {
        return NAME_MAP.get(value);
    }

    /**
     * 项目集合
     *
     * @return
     */
    public static Collection<ResourcesModule> items() {
        return NAME_MAP.values();
    }
}
