package com.autumn.zero.authorization.filter;

import com.autumn.runtime.session.AutumnSession;
import com.autumn.security.filter.AutumnPermissionFilter;
import com.autumn.util.UrlUtils;
import com.autumn.zero.authorization.services.ResourcesService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 权限过滤
 * <p>
 * 使用该过滤不会抛异常，而是会产生404的错误
 * </p>
 *
 * @author 老码农 2018-12-12 12:36:59
 */
public class AutumnZeroPermissionFilter implements AutumnPermissionFilter {

    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private ServerProperties serverProperties;
    @Autowired
    private AutumnSession session;

    private final AutumnZeroAuthorizationFilter autumnAuthorizationFilter;

    public AutumnZeroPermissionFilter() {
        this.autumnAuthorizationFilter = new AutumnZeroAuthorizationFilter();
    }

    @Override
    public void initFilter(ShiroFilterFactoryBean factoryBean, SecurityManager securityManager) {
        // 权限控制map.
        // Map<String, Filter> filters = new LinkedHashMap<>(10);
        this.resetPermission();
        factoryBean.getFilters().put("perms", this.autumnAuthorizationFilter);
        Map<String, String> filterMap = new HashMap<>(10);
        filterMap.put("/**", "perms");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        factoryBean.setUnauthorizedUrl("/api/error");
    }

    @Override
    public void resetPermission() {
        synchronized (this) {
            this.autumnAuthorizationFilter.getUrlPermissionMap().clear();
            Map<String, Set<String>> map = AutumnZeroPermissionFilter.createPermissionMap(this.resourcesService, this.serverProperties);
            this.autumnAuthorizationFilter.getUrlPermissionMap().putAll(map);
        }
    }

    /**
     * 创建权限 Map
     *
     * @param resourcesService 资源服务
     * @param serverProperties 服务器属性
     * @return
     */
    static Map<String, Set<String>> createPermissionMap(ResourcesService resourcesService,
                                                        ServerProperties serverProperties) {
        Map<String, Set<String>> map = resourcesService.urlPermissionMap();
        Map<String, Set<String>> urlPermissionMap = new HashMap<>(map.size());
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            String key = UrlUtils.getLowerCaseRequestUrl(serverProperties
                            .getServlet()
                            .getContextPath(),
                    entry.getKey());
            urlPermissionMap.put(key, entry.getValue());
        }
        return urlPermissionMap;
    }

}
