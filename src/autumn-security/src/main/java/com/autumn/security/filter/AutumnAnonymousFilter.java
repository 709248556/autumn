package com.autumn.security.filter;

import com.autumn.security.credential.AutumnUserCredentialsService;
import com.autumn.security.token.DeviceAuthenticationToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 匿名 访问
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 22:56
 **/
public class AutumnAnonymousFilter extends AnonymousFilter {

    private static final Logger log = LoggerFactory.getLogger(AutumnAnonymousFilter.class);

    protected final AutumnUserCredentialsService credentialsService;

    /**
     * 过滤上下文
     */
    protected final AutumnFilterContext filterContext;

    /**
     * @param credentialsService
     * @param filterContext
     */
    public AutumnAnonymousFilter(AutumnUserCredentialsService credentialsService,
                                 AutumnFilterContext filterContext) {
        this.credentialsService = credentialsService;
        this.filterContext = filterContext;
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        DeviceAuthenticationToken authToken = FilterUtils.createDeviceToken(httpServletRequest);
        if (authToken == null) {
            return super.preHandle(request, response);
        }
        if (this.filterContext.getExcludeTokenLoginPaths().size() > 0) {
            for (String path : this.filterContext.getExcludeTokenLoginPaths()) {
                if (this.pathsMatch(path, request)) {
                    return super.preHandle(request, response);
                }
            }
        }
        if (FilterUtils.matchesDevice(this.credentialsService, authToken)) {
            return true;
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(authToken);
            return true;
        } catch (Exception e) {
            log.error("Token 自动登录出错:" + e.getMessage(), e);
            //返回 false 会导致无法捕获异常
            return true;
        }
    }

    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        super.doFilterInternal(request, response, chain);
    }
}
