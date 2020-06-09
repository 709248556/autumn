package com.autumn.zero.authorization.filter;

import com.autumn.runtime.session.AutumnUser;
import com.autumn.security.context.AutumnSecurityContextHolder;
import com.autumn.util.UrlUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 权限过滤
 * <p>
 * </p>
 *
 * @description: TODO AbstractFilter
 * @author: 老码农
 * @create: 2019-12-28 19:35
 **/
public class AutumnZeroAuthorizationFilter extends AuthorizationFilter {

    private final Map<String, Set<String>> urlPermissionMap;

    public AutumnZeroAuthorizationFilter() {
        this.urlPermissionMap = new HashMap<>(100);
    }

    /**
     * 获取Url权限
     *
     * @return
     */
    public Map<String, Set<String>> getUrlPermissionMap() {
        return this.urlPermissionMap;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        boolean isPermitted = true;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String url = UrlUtils.getLowerCaseRequestUrl(httpServletRequest.getRequestURI());
            Set<String> permissions = this.getUrlPermissionMap().get(url);
            if (permissions != null && permissions.size() > 0) {
                isPermitted = false;
                AutumnUser autumnUser = AutumnSecurityContextHolder.getAutumnUser();
                if (autumnUser != null) {
                    for (String permission : permissions) {
                        if (autumnUser.getPermissions().contains(permission)) {
                            isPermitted = true;
                            break;
                        }
                    }
                }
                if (!isPermitted) {
                    return false;
                }
            }
        }
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[]) mappedValue;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else {
                if (!subject.isPermittedAll(perms)) {
                    isPermitted = false;
                }
            }
        }
        return isPermitted;
    }
}
