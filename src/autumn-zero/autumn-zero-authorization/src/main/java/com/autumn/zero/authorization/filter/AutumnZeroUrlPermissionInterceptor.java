package com.autumn.zero.authorization.filter;

import com.autumn.runtime.session.AutumnSession;
import com.autumn.security.AutumnAuthenticationException;
import com.autumn.security.AutumnNotLoginException;
import com.autumn.util.UrlUtils;
import com.autumn.zero.authorization.services.ResourcesService;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * Web权限拦载
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-29 09:58
 **/
public class AutumnZeroUrlPermissionInterceptor implements WebMvcConfigurer, HandlerInterceptor {

    private final AutumnSession session;
    private final ResourcesService resourcesService;
    private final ServerProperties serverProperties;
    private Map<String, Set<String>> urlPermissionMap;

    /**
     * 实例化
     *
     * @param session          会话
     * @param resourcesService
     * @param serverProperties
     */
    public AutumnZeroUrlPermissionInterceptor(AutumnSession session,
                                              ResourcesService resourcesService,
                                              ServerProperties serverProperties) {
        this.session = session;
        this.resourcesService = resourcesService;
        this.serverProperties = serverProperties;
        this.urlPermissionMap = null;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }

    /**
     * 获取Url权限
     *
     * @return
     */
    public Map<String, Set<String>> getUrlPermissionMap() {
        if (this.urlPermissionMap == null) {
            synchronized (this) {
                if (this.urlPermissionMap == null) {
                    this.urlPermissionMap = AutumnZeroPermissionFilter.createPermissionMap(this.resourcesService, this.serverProperties);
                }
            }
        }
        return this.urlPermissionMap;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (this.getUrlPermissionMap().size() > 0) {
            String url = UrlUtils.getLowerCaseRequestUrl(request.getRequestURI());
            Set<String> permissions = this.getUrlPermissionMap().get(url);
            if (permissions != null && permissions.size() > 0) {
                boolean isPermitted = this.session.isOrPermittedAll(permissions);
                if (!isPermitted) {
                    if (!this.session.isAuthenticated()) {
                        throw new AutumnNotLoginException();
                    }
                    throw new AutumnAuthenticationException("无权操作。");
                }
            }
        }
        return true;
    }

    /**
     * 重置权限
     */
    public void resetPermission() {
        synchronized (this) {
            this.urlPermissionMap = this.resourcesService.urlPermissionMap();
        }
    }
}
