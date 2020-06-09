package com.autumn.security.filter;


import com.autumn.security.credential.AutumnUserCredentialsService;
import com.autumn.security.token.DeviceAuthenticationToken;
import com.autumn.security.token.UserNamePasswordAuthenticationToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 设备过滤
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 21:16
 **/
public class AutumnFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(AutumnFormAuthenticationFilter.class);

    protected final AutumnUserCredentialsService credentialsService;

    /**
     * 过滤上下文
     */
    protected final AutumnFilterContext filterContext;

    /**
     * @param credentialsService
     * @param filterContext
     */
    public AutumnFormAuthenticationFilter(AutumnUserCredentialsService credentialsService,
                                          AutumnFilterContext filterContext) {
        this.credentialsService = credentialsService;
        this.filterContext = filterContext;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        DeviceAuthenticationToken authToken = FilterUtils.createDeviceToken(httpServletRequest);
        if (authToken != null) {
            return authToken;
        }
        return super.createToken(request, response);
    }

    @Override
    protected AuthenticationToken createToken(String username, String password, boolean rememberMe, String host) {
        return new UserNamePasswordAuthenticationToken(username, password, rememberMe, host);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        DeviceAuthenticationToken authToken = FilterUtils.createDeviceToken(httpServletRequest);
        if (authToken == null) {
            return super.onAccessDenied(request, response);
        }
        if (this.filterContext.getExcludeTokenLoginPaths().size() > 0) {
            for (String path : this.filterContext.getExcludeTokenLoginPaths()) {
                if (this.pathsMatch(path, request)) {
                    return super.preHandle(request, response);
                }
            }
        }
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (FilterUtils.matchesDevice(this.credentialsService, authToken)) {
                    return true;
                }
                try {
                    Subject subject = SecurityUtils.getSubject();
                    subject.login(authToken);
                    return onLoginSuccess(authToken, subject, request, response);
                } catch (AuthenticationException e) {
                    return onLoginFailure(authToken, e, request, response);
                }
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                return true;
            }
        }
        return super.onAccessDenied(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return super.onLoginFailure(token, e, request, response);
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        super.postHandle(request, response);
    }

    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        super.doFilterInternal(request, response, chain);
    }
}
